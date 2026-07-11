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
        return try {
            val dbName = "KarigojobsDatabase.db"
            val dbFile = context.getDatabasePath(dbName)
            val byteStream = java.io.ByteArrayInputStream(bytes)
            val zis = java.util.zip.ZipInputStream(byteStream)
            var entry = zis.nextEntry
            var isZip = false
            
            while (entry != null) {
                isZip = true
                val fileName = entry.name
                if (fileName == dbName) {
                    dbFile.outputStream().use { zis.copyTo(it) }
                } else if (fileName.startsWith("datastore/")) {
                    val actualFileName = fileName.removePrefix("datastore/")
                    val datastoreFile = File(context.filesDir, actualFileName)
                    datastoreFile.parentFile?.mkdirs()
                    datastoreFile.outputStream().use { zis.copyTo(it) }
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
            zis.close()
            
            // Backwards compatibility: If it wasn't a zip, it must be the raw DB file from previous backups
            if (!isZip) {
                dbFile.writeBytes(bytes)
            }
            true
        } catch (e: Exception) {
            try {
                val dbFile = context.getDatabasePath("KarigojobsDatabase.db")
                dbFile.writeBytes(bytes)
                true
            } catch (ex: Exception) {
                false
            }
        }
    }
}
