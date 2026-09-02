package com.karigojobs.data.repository

import com.karigojobs.domain.repository.CustomUpdateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.AppUpdateInfo
import com.karigojobs.share.model.AppUpdateState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CustomUpdateRepositoryImpl(
    private val httpClient: HttpClient,
    private val pathProvider: AppPathProvider
) : CustomUpdateRepository {

    // Remote JSON endpoint for checking app updates
    private val updateConfigUrl = "https://raw.githubusercontent.com/ihazratummar/Karigo-App/main/app_update.json"

    override suspend fun fetchUpdateInfo(): Result<AppUpdateInfo, String> {
        return try {
            val response = httpClient.get(updateConfigUrl)
            if (response.status.value != 200) {
                return Result.Error("Unable to check for updates right now")
            }
            val responseText = response.bodyAsText()
            val jsonObject = Json.parseToJsonElement(responseText).jsonObject

            val versionCode = jsonObject["versionCode"]?.jsonPrimitive?.intOrNull ?: 1
            val versionName = jsonObject["versionName"]?.jsonPrimitive?.contentOrNull ?: "1.0.0"
            val fileSizeBytes = jsonObject["fileSizeBytes"]?.jsonPrimitive?.contentOrNull?.toLongOrNull() ?: 0L
            val fileSizeFormatted = jsonObject["fileSizeFormatted"]?.jsonPrimitive?.contentOrNull ?: "8.4 MB"
            val apkUrl = jsonObject["apkUrl"]?.jsonPrimitive?.contentOrNull ?: ""
            val playStoreUrl = jsonObject["playStoreUrl"]?.jsonPrimitive?.contentOrNull ?: ""
            val isForceUpdate = jsonObject["isForceUpdate"]?.jsonPrimitive?.contentOrNull?.toBoolean() ?: false

            val whatsNewList = jsonObject["whatsNew"]?.jsonArray?.mapNotNull {
                it.jsonPrimitive.contentOrNull
            } ?: emptyList()

            val info = AppUpdateInfo(
                versionCode = versionCode,
                versionName = versionName,
                fileSizeBytes = fileSizeBytes,
                fileSizeFormatted = fileSizeFormatted,
                whatsNew = whatsNewList,
                apkUrl = apkUrl,
                playStoreUrl = playStoreUrl,
                isForceUpdate = isForceUpdate
            )
            Result.Success(info)
        } catch (e: Exception) {
            Result.Error("Unable to check for updates right now")
        }
    }

    override fun downloadApk(apkUrl: String, updateInfo: AppUpdateInfo): Flow<AppUpdateState> = flow {
        val targetPath = pathProvider.getCachePath("karigo_update_${updateInfo.versionName}.apk")
        if (apkUrl.isBlank()) {
            emit(AppUpdateState.Error("Invalid download URL"))
            return@flow
        }

        try {
            emit(AppUpdateState.Downloading(updateInfo, 10, "0.8 MB"))
            val response = httpClient.get(apkUrl)
            val bytes = response.body<ByteArray>()

            NativeFileAccess.writeBytes(targetPath, bytes)
            val downloadedMB = "${(bytes.size / (1024.0 * 1024.0)).let { (it * 10).toInt() / 10.0 }} MB"
            emit(AppUpdateState.Downloading(updateInfo, 100, downloadedMB))

            emit(AppUpdateState.ReadyToInstall(updateInfo, targetPath))
        } catch (e: Exception) {
            val existingBytes = if (NativeFileAccess.exists(targetPath)) NativeFileAccess.readBytes(targetPath) else null
            if (existingBytes != null && existingBytes.isNotEmpty()) {
                emit(AppUpdateState.ReadyToInstall(updateInfo, targetPath))
            } else {
                emit(AppUpdateState.Error(e.message ?: "Download failed"))
            }
        }
    }
}
