package com.karigojobs.data.repository

import com.karigojobs.domain.repository.BackupFileHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.Buffer

class BackupFileHandlerImpl(
    private val pathProvider: AppPathProvider
) : BackupFileHandler {

    override suspend fun getBackupBytes(): ByteArray {
        return withContext(Dispatchers.IO) {
            val dbPath = pathProvider.getDatabasePath()
            val walPath = pathProvider.getDatabasePath().plus("-wal")
            val shmPath = pathProvider.getDatabasePath().plus("-shm")
            
            val onboardingPath = pathProvider.getDatastorePath("onboarding.preferences_pb")
            val settingsPath = pathProvider.getDatastorePath("settings.preferences_pb")
            
            val dbBytes = if (NativeFileAccess.exists(dbPath)) NativeFileAccess.readBytes(dbPath) else ByteArray(0)
            val walBytes = if (NativeFileAccess.exists(walPath)) NativeFileAccess.readBytes(walPath) else null
            val shmBytes = if (NativeFileAccess.exists(shmPath)) NativeFileAccess.readBytes(shmPath) else null
            
            val onboardingBytes = if (NativeFileAccess.exists(onboardingPath)) NativeFileAccess.readBytes(onboardingPath) else null
            val settingsBytes = if (NativeFileAccess.exists(settingsPath)) NativeFileAccess.readBytes(settingsPath) else null
            
            val buffer = Buffer()
            buffer.writeUtf8("KARIGOBACKUP_V1\n")
            
            // Format: [Size Int32] [Data]
            fun writeChunk(bytes: ByteArray?) {
                if (bytes == null) {
                    buffer.writeInt(0)
                } else {
                    buffer.writeInt(bytes.size)
                    buffer.write(bytes)
                }
            }
            
            writeChunk(dbBytes)
            writeChunk(walBytes)
            writeChunk(shmBytes)
            writeChunk(onboardingBytes)
            writeChunk(settingsBytes)
            
            buffer.readByteArray()
        }
    }

    override suspend fun restoreBackup(bytes: ByteArray) {
        withContext(Dispatchers.IO) {
            val buffer = Buffer().apply { write(bytes) }
            val header = try { buffer.readUtf8Line() } catch (e: Exception) { null }
            
            if (header == "KARIGOBACKUP_V1") {
                fun readChunk(): ByteArray? {
                    if (buffer.exhausted()) return null
                    val size = buffer.readInt()
                    return if (size == 0) null else buffer.readByteArray(size.toLong())
                }
                
                val dbBytes = readChunk()
                val walBytes = readChunk()
                val shmBytes = readChunk()
                val onboardingBytes = readChunk()
                val settingsBytes = readChunk()
                
                if (dbBytes != null) {
                    pathProvider.mergeDatabaseBackup(dbBytes, walBytes, shmBytes)
                }
                
                if (onboardingBytes != null) {
                    val onboardingPath = pathProvider.getDatastorePath("onboarding.preferences_pb")
                    if (!NativeFileAccess.exists(onboardingPath)) {
                        NativeFileAccess.writeBytes(onboardingPath, onboardingBytes)
                    }
                }
                if (settingsBytes != null) {
                    val settingsPath = pathProvider.getDatastorePath("settings.preferences_pb")
                    if (!NativeFileAccess.exists(settingsPath)) {
                        NativeFileAccess.writeBytes(settingsPath, settingsBytes)
                    }
                }
                
                pathProvider.restartApp()
            } else {
                // Not V1 binary format, try legacy ZIP/raw DB fallback
                val success = pathProvider.extractLegacyBackup(bytes)
                if (success) {
                    pathProvider.restartApp()
                } else {
                    throw IllegalStateException("Failed to restore backup: Invalid format")
                }
            }
        }
    }
}
