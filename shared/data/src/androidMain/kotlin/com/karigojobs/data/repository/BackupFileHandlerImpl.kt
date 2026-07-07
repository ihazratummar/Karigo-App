package com.karigojobs.data.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.karigojobs.domain.repository.BackupFileHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.system.exitProcess

class BackupFileHandlerImpl(private val context: Context) : BackupFileHandler {

    override suspend fun getBackupBytes(): ByteArray {
        return withContext(Dispatchers.IO) {
            val dbName = "KarigojobsDatabase.db"
            val dbFile = context.getDatabasePath(dbName)
            
            // Force WAL checkpoint to ensure all data is in the main .db file
            try {
                val db = SQLiteDatabase.openDatabase(dbFile.absolutePath, null, SQLiteDatabase.OPEN_READWRITE)
                db.rawQuery("PRAGMA wal_checkpoint(FULL);", null).use { 
                    it.moveToFirst() 
                }
                db.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            val onboardingStore = File(context.filesDir, "onboarding.preferences_pb")
            val settingsStore = File(context.filesDir, "settings.preferences_pb")

            val byteStream = ByteArrayOutputStream()
            ZipOutputStream(byteStream).use { zos ->
                // Zip Database
                if (dbFile.exists()) {
                    zos.putNextEntry(ZipEntry(dbFile.name))
                    dbFile.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
                
                // Zip Datastore
                if (onboardingStore.exists()) {
                    zos.putNextEntry(ZipEntry("datastore/${onboardingStore.name}"))
                    onboardingStore.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
                
                if (settingsStore.exists()) {
                    zos.putNextEntry(ZipEntry("datastore/${settingsStore.name}"))
                    settingsStore.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
            }
            byteStream.toByteArray()
        }
    }

    override suspend fun restoreBackup(bytes: ByteArray) {
        withContext(Dispatchers.IO) {
            val dbName = "KarigojobsDatabase.db"
            val dbFile = context.getDatabasePath(dbName)
            val walFile = context.getDatabasePath("$dbName-wal")
            val shmFile = context.getDatabasePath("$dbName-shm")

            // Delete WAL and SHM files to prevent corruption with the new DB
            if (walFile.exists()) walFile.delete()
            if (shmFile.exists()) shmFile.delete()

            // If it's a zip file, extract it. Otherwise, it might be the old .db backup file
            try {
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
            } catch (e: Exception) {
                // Fallback for non-zip (legacy raw db file)
                dbFile.writeBytes(bytes)
            }
            
            // Restart the app to apply Datastore and Database changes
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            if (intent != null) {
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK or android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                exitProcess(0)
            }
        }
    }
}
