package com.karigojobs.data.repository

expect object NativeFileAccess {
    fun readBytes(path: String): ByteArray?
    fun writeBytes(path: String, bytes: ByteArray)
    fun delete(path: String)
    fun exists(path: String): Boolean
}
