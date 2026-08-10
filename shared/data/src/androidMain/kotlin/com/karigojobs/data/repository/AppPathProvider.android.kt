package com.karigojobs.data.repository

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import java.io.File
import kotlin.system.exitProcess

actual class AppPathProvider(private val context: Context) {
    actual fun getDatabasePath(): String {
        val dbFile = context.getDatabasePath("KarigojobsDatabase.db")
        // Force WAL checkpoint before backup
        try {
            val db = SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READWRITE)
            db.rawQuery("PRAGMA wal_checkpoint(FULL);", null).use { it.moveToFirst() }
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dbFile.absolutePath
    }

    actual fun getDatastorePath(fileName: String): String {
        return File(context.filesDir, fileName).absolutePath
    }

    actual fun restartApp() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            exitProcess(0)
        }
    }

    actual fun extractLegacyBackup(bytes: ByteArray): Boolean {
        val tempDir = File(context.cacheDir, "legacy_restore_${System.currentTimeMillis()}")
        return try {
            tempDir.mkdirs()
            val byteStream = java.io.ByteArrayInputStream(bytes)
            val zis = java.util.zip.ZipInputStream(byteStream)
            var entry = zis.nextEntry
            var isZip = false
            var extractedDbFile: File? = null

            while (entry != null) {
                isZip = true
                val name = entry.name
                val targetFile = File(tempDir, name.substringAfterLast("/"))
                
                if (!entry.isDirectory) {
                    targetFile.parentFile?.mkdirs()
                    targetFile.outputStream().use { zis.copyTo(it) }

                    val fileName = targetFile.name
                    if ((fileName.endsWith(".db", ignoreCase = true) || fileName.contains("KarigojobsDatabase", ignoreCase = true)) 
                        && !fileName.endsWith("-wal", ignoreCase = true) 
                        && !fileName.endsWith("-shm", ignoreCase = true)) {
                        extractedDbFile = targetFile
                    }
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
            zis.close()

            if (isZip && extractedDbFile != null && extractedDbFile.exists()) {
                // Checkpoint WAL if -wal file exists in extracted temp folder
                val walFile = File(extractedDbFile.absolutePath + "-wal")
                if (walFile.exists()) {
                    try {
                        val db = SQLiteDatabase.openDatabase(extractedDbFile.absolutePath, null, SQLiteDatabase.OPEN_READWRITE)
                        db.rawQuery("PRAGMA wal_checkpoint(FULL);", null).use { it.moveToFirst() }
                        db.close()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                mergeDatabaseFile(extractedDbFile.absolutePath)
                true
            } else {
                // Fallback: If not zip or no .db found in zip, try merging bytes directly as DB
                mergeDatabaseBackup(bytes, null, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mergeDatabaseBackup(bytes, null, null)
        } finally {
            if (tempDir.exists()) {
                tempDir.deleteRecursively()
            }
        }
    }

    actual fun mergeDatabaseBackup(dbBytes: ByteArray, walBytes: ByteArray?, shmBytes: ByteArray?): Boolean {
        val time = System.currentTimeMillis()
        val tempDbFile = File(context.cacheDir, "temp_restore_$time.db")
        val tempWalFile = File(context.cacheDir, "temp_restore_$time.db-wal")
        val tempShmFile = File(context.cacheDir, "temp_restore_$time.db-shm")

        return try {
            tempDbFile.writeBytes(dbBytes)
            if (walBytes != null && walBytes.isNotEmpty()) {
                tempWalFile.writeBytes(walBytes)
            }
            if (shmBytes != null && shmBytes.isNotEmpty()) {
                tempShmFile.writeBytes(shmBytes)
            }

            // Force WAL checkpoint on backup file before merging so all WAL pages commit to main DB file
            try {
                val tempDb = SQLiteDatabase.openDatabase(tempDbFile.absolutePath, null, SQLiteDatabase.OPEN_READWRITE)
                tempDb.rawQuery("PRAGMA wal_checkpoint(FULL);", null).use { it.moveToFirst() }
                tempDb.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mergeDatabaseFile(tempDbFile.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            if (tempDbFile.exists()) tempDbFile.delete()
            if (tempWalFile.exists()) tempWalFile.delete()
            if (tempShmFile.exists()) tempShmFile.delete()
        }
    }

    private fun mergeDatabaseFile(backupDbPath: String): Boolean {
        val activeDbFile = context.getDatabasePath("KarigojobsDatabase.db")
        if (!activeDbFile.exists()) {
            activeDbFile.parentFile?.mkdirs()
            File(backupDbPath).copyTo(activeDbFile, overwrite = true)
            return true
        }

        return try {
            val db = SQLiteDatabase.openDatabase(activeDbFile.absolutePath, null, SQLiteDatabase.OPEN_READWRITE)
            try {
                // Disable foreign keys BEFORE starting transaction
                db.execSQL("PRAGMA foreign_keys = OFF;")
                db.execSQL("ATTACH DATABASE '$backupDbPath' AS backup_db;")

                db.beginTransaction()

                // Pre-merge: remove local materials/categories with matching name and trade_type to prevent restore duplicates
                try {
                    db.execSQL("""
                        DELETE FROM MaterialCategory WHERE EXISTS (
                            SELECT 1 FROM backup_db.MaterialCategory b 
                            WHERE LOWER(TRIM(b.name)) = LOWER(TRIM(MaterialCategory.name)) 
                            AND LOWER(TRIM(b.trade_type)) = LOWER(TRIM(MaterialCategory.trade_type))
                        );
                    """.trimIndent())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                try {
                    db.execSQL("""
                        DELETE FROM materials WHERE EXISTS (
                            SELECT 1 FROM backup_db.materials b 
                            WHERE LOWER(TRIM(b.name)) = LOWER(TRIM(materials.name)) 
                            AND LOWER(TRIM(b.trade_type)) = LOWER(TRIM(materials.trade_type))
                        );
                    """.trimIndent())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val mergeTables = listOf(
                    "client",
                    "MaterialCategory",
                    "materials",
                    "worker_profile",
                    "job",
                    "site_estimate",
                    "job_labour_item",
                    "job_labour_log",
                    "job_material",
                    "job_payment",
                    "estimate_materials",
                    "monthly_quota"
                )

                for (table in mergeTables) {
                    mergeTableFlexibly(db, table)
                }

                // Post-merge safety deduplication pass
                try {
                    db.execSQL("""
                        DELETE FROM materials 
                        WHERE rowid NOT IN (
                            SELECT MAX(rowid) 
                            FROM materials 
                            GROUP BY LOWER(TRIM(name)), LOWER(TRIM(trade_type))
                        );
                    """.trimIndent())
                    
                    db.execSQL("""
                        DELETE FROM MaterialCategory 
                        WHERE rowid NOT IN (
                            SELECT MAX(rowid) 
                            FROM MaterialCategory 
                            GROUP BY LOWER(TRIM(name)), LOWER(TRIM(trade_type))
                        );
                    """.trimIndent())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                db.execSQL("DROP VIEW IF EXISTS monthly_earning;")
                db.execSQL("""
                    CREATE VIEW monthly_earning AS
                    SELECT
                        strftime('%Y-%m', datetime(created_at / 1000, 'unixepoch')) AS month,
                        COUNT(*) AS job_count,
                        TOTAL(CASE WHEN UPPER(status) LIKE '%PAID%' THEN total ELSE 0.0 END) AS revenue,
                        TOTAL(total) AS total_billed,
                        TOTAL(CASE WHEN UPPER(status) NOT LIKE '%PAID%' THEN total ELSE 0.0 END) AS outstanding
                    FROM job
                    WHERE created_at IS NOT NULL AND created_at > 0
                    GROUP BY strftime('%Y-%m', datetime(created_at / 1000, 'unixepoch'))
                    ORDER BY month DESC;
                """.trimIndent())

                db.setTransactionSuccessful()
            } finally {
                if (db.inTransaction()) {
                    db.endTransaction()
                }
                try {
                    db.execSQL("DETACH DATABASE backup_db;")
                    db.execSQL("PRAGMA foreign_keys = ON;")
                    db.rawQuery("PRAGMA wal_checkpoint(FULL);", null).use { it.moveToFirst() }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                db.close()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun mergeTableFlexibly(db: SQLiteDatabase, tableName: String) {
        try {
            // Get active database column names for tableName
            val activeCols = mutableSetOf<String>()
            db.rawQuery("PRAGMA table_info($tableName);", null).use { cursor ->
                val nameIdx = cursor.getColumnIndex("name")
                if (nameIdx != -1) {
                    while (cursor.moveToNext()) {
                        activeCols.add(cursor.getString(nameIdx))
                    }
                }
            }

            // Get backup database column names for tableName
            val backupCols = mutableSetOf<String>()
            db.rawQuery("PRAGMA backup_db.table_info($tableName);", null).use { cursor ->
                val nameIdx = cursor.getColumnIndex("name")
                if (nameIdx != -1) {
                    while (cursor.moveToNext()) {
                        backupCols.add(cursor.getString(nameIdx))
                    }
                }
            }

            val commonCols = activeCols.intersect(backupCols)
            if (commonCols.isNotEmpty()) {
                val colListStr = commonCols.joinToString(", ")
                val sql = "INSERT OR REPLACE INTO $tableName ($colListStr) SELECT $colListStr FROM backup_db.$tableName;"
                db.execSQL(sql)
            } else {
                db.execSQL("INSERT OR REPLACE INTO $tableName SELECT * FROM backup_db.$tableName;")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                db.execSQL("INSERT OR REPLACE INTO $tableName SELECT * FROM backup_db.$tableName;")
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
