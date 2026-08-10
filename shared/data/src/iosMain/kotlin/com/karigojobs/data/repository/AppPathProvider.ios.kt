@file:OptIn(ExperimentalForeignApi::class)
package com.karigojobs.data.repository

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL
import platform.posix.exit
import kotlinx.cinterop.ExperimentalForeignApi

actual class AppPathProvider {
    private val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )

    actual fun getDatabasePath(): String {
        return "${documentDirectory?.path}/KarigojobsDatabase.db"
    }

    actual fun getDatastorePath(fileName: String): String {
        return "${documentDirectory?.path}/$fileName"
    }

    actual fun restartApp() {
        exit(0)
    }

    actual fun extractLegacyBackup(bytes: ByteArray): Boolean {
        return false
    }

    actual fun mergeDatabaseBackup(dbBytes: ByteArray, walBytes: ByteArray?, shmBytes: ByteArray?): Boolean {
        return try {
            val dbPath = getDatabasePath()
            NativeFileAccess.writeBytes(dbPath, dbBytes)
            true
        } catch (e: Exception) {
            false
        }
    }
}
