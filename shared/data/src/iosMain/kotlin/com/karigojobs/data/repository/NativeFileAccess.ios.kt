package com.karigojobs.data.repository

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
actual object NativeFileAccess {
    actual fun readBytes(path: String): ByteArray? {
        val fileManager = NSFileManager.defaultManager
        if (!fileManager.fileExistsAtPath(path)) return null
        
        val data = fileManager.contentsAtPath(path) ?: return null
        if (data.length == 0uL) return ByteArray(0)
        
        val bytes = ByteArray(data.length.toInt())
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), data.bytes, data.length)
        }
        return bytes
    }

    actual fun writeBytes(path: String, bytes: ByteArray) {
        if (bytes.isEmpty()) {
            NSData().writeToFile(path, atomically = true)
            return
        }
        bytes.usePinned { pinned ->
            val data = NSData.dataWithBytes(pinned.addressOf(0), bytes.size.toULong())
            data.writeToFile(path, atomically = true)
        }
    }

    actual fun delete(path: String) {
        NSFileManager.defaultManager.removeItemAtPath(path, null)
    }

    actual fun exists(path: String): Boolean {
        return NSFileManager.defaultManager.fileExistsAtPath(path)
    }
}
