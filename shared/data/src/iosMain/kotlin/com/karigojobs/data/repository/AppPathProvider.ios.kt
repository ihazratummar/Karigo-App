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
        // Exiting the app to simulate a restart, the user must launch it again on iOS
        exit(0)
    }

    actual fun extractLegacyBackup(bytes: ByteArray): Boolean {
        // iOS never had legacy ZIP backups since it was Android-first
        return false
    }
}
