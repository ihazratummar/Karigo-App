package com.karigojobs.data.repository

import com.karigojobs.domain.repository.BackupFileHandler
import com.karigojobs.domain.repository.GoogleDriveRepository
import com.karigojobs.domain.result.BackupError
import com.karigojobs.domain.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.patch
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.statement.readRawBytes
import io.ktor.client.request.delete
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.time.Instant

@Serializable
data class DriveFileList(val files: List<DriveFile> = emptyList())

@Serializable
data class DriveFile(val id: String, val name: String, val modifiedTime: String? = null)

class GoogleDriveRepositoryImpl(
    private val httpClient: HttpClient,
    private val backupFileHandler: BackupFileHandler
) : GoogleDriveRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun uploadBackup(accessToken: String): Result<Unit, BackupError> {
        return try {
            // 1. Get the local backup zip bytes
            val dbBytes = backupFileHandler.getBackupBytes()

            // 2. Check if file already exists
            val existingFiles = getBackupFiles(accessToken)
            val fileId = if (existingFiles != null && existingFiles.files.isNotEmpty()) {
                existingFiles.files.first().id
            } else {
                // Create new file metadata in appDataFolder
                val createResponse = httpClient.post("https://www.googleapis.com/drive/v3/files") {
                    header(HttpHeaders.Authorization, "Bearer $accessToken")
                    contentType(ContentType.Application.Json)
                    setBody("""
                        {
                            "name": "karigo_backup.zip",
                            "parents": ["appDataFolder"]
                        }
                    """.trimIndent())
                }
                if (!createResponse.status.isSuccess()) {
                    return Result.Error(BackupError.UnknownErrorWithMessage("Create err: ${createResponse.status} - ${createResponse.bodyAsText()}"))
                }
                json.decodeFromString<DriveFile>(createResponse.bodyAsText()).id
            }

            // 3. Upload the backup zip content using uploadType=media
            val uploadResponse = httpClient.patch("https://www.googleapis.com/upload/drive/v3/files/$fileId?uploadType=media") {
                header(HttpHeaders.Authorization, "Bearer $accessToken")
                contentType(ContentType.Application.OctetStream)
                setBody(dbBytes)
            }

            if (uploadResponse.status.isSuccess()) {
                cleanupOldBackups(accessToken)
                Result.Success(Unit)
            } else {
                Result.Error(BackupError.UnknownErrorWithMessage("Upload err: ${uploadResponse.status} - ${uploadResponse.bodyAsText()}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(BackupError.UnknownErrorWithMessage("Exception: ${e.message}"))
        }
    }

    override suspend fun downloadLatestBackup(accessToken: String): Result<Unit, BackupError> {
        return try {
            val fileListResponse = getBackupFiles(accessToken)
            val files = fileListResponse?.files ?: return Result.Error(BackupError.FileSystemError)

            if (files.isEmpty()) return Result.Error(BackupError.FileSystemError)

            // Get the most recent
            val latestFile = files.first()

            val downloadResponse = httpClient.get("https://www.googleapis.com/drive/v3/files/${latestFile.id}?alt=media") {
                header(HttpHeaders.Authorization, "Bearer $accessToken")
            }

            if (downloadResponse.status.isSuccess()) {
                val bytes = downloadResponse.readRawBytes()
                backupFileHandler.restoreBackup(bytes)
                Result.Success(Unit)
            } else {
                Result.Error(BackupError.NetworkError)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(BackupError.UnknownError)
        }
    }

    override suspend fun getLastBackupTimestamp(accessToken: String): Result<Long?, BackupError> {
        return try {
            val fileList = getBackupFiles(accessToken)
            val files = fileList?.files
            if (files.isNullOrEmpty()) {
                return Result.Success(null)
            }
            val latestFile = files.first()
            val modifiedTimeStr = latestFile.modifiedTime
            if (modifiedTimeStr != null) {
                val instant = Instant.parse(modifiedTimeStr)
                Result.Success(instant.toEpochMilliseconds())
            } else {
                Result.Success(1L)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(BackupError.UnknownErrorWithMessage(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getBackupFiles(accessToken: String): DriveFileList? {
        val response = httpClient.get("https://www.googleapis.com/drive/v3/files") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            parameter("spaces", "appDataFolder")
            parameter("q", "name='karigo_backup.zip' or name='karigo_backup.db'")
            parameter("fields", "files(id, name, modifiedTime)")
            parameter("orderBy", "modifiedTime desc")
        }
        return if (response.status.isSuccess()) {
            val text = response.readRawBytes().decodeToString()
            json.decodeFromString<DriveFileList>(text)
        } else null
    }

    private suspend fun cleanupOldBackups(accessToken: String) {
        val fileList = getBackupFiles(accessToken)
        val files = fileList?.files ?: return
        
        if (files.size > 3) {
            val filesToDelete = files.drop(3)
            filesToDelete.forEach { file ->
                try {
                    httpClient.delete("https://www.googleapis.com/drive/v3/files/${file.id}") {
                        header(HttpHeaders.Authorization, "Bearer $accessToken")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
