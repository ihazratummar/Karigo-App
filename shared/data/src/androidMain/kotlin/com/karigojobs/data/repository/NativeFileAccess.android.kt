package com.karigojobs.data.repository

import java.io.File

actual object NativeFileAccess {
    actual fun readBytes(path: String): ByteArray? {
        val file = File(path)
        return if (file.exists()) file.readBytes() else null
    }

    actual fun writeBytes(path: String, bytes: ByteArray) {
        File(path).writeBytes(bytes)
    }

    actual fun delete(path: String) {
        File(path).delete()
    }

    actual fun exists(path: String): Boolean {
        return File(path).exists()
    }
}
