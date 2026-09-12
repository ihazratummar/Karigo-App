package com.karigojobs.app.android.services

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallException
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallErrorCode
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * @author hazratummar
 * Created on 05/06/26
 */
class UpdateManagerImpl(
    private val context: Context,
    private val appUpdateManager: AppUpdateManager,
    private val updateType: Int = AppUpdateType.IMMEDIATE
) : UpdateManager {

    companion object {
        private const val TAG = "UpdateManager"
        const val UPDATE_REQUEST_CODE = 1001
        private const val FLEXIBLE_RESTART_DELAY_MS = 5_000L
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var isListenerRegistered = false

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> {
                Log.d(TAG, "Update downloaded successfully. Restarting in 5 seconds…")
                Toast.makeText(
                    context,
                    "Download complete. Restarting in 5 seconds…",
                    Toast.LENGTH_LONG
                ).show()
                scope.launch {
                    delay(FLEXIBLE_RESTART_DELAY_MS.milliseconds)
                    appUpdateManager.completeUpdate()
                }
            }
            InstallStatus.FAILED -> {
                Log.e(TAG, "Flexible update failed during install. Error code: ${state.installErrorCode()}")
            }
            else -> Unit
        }
    }

    init {
        registerListenerIfNeeded()
    }

    private fun registerListenerIfNeeded() {
        if (!isListenerRegistered) {
            appUpdateManager.registerListener(installStateUpdateListener)
            isListenerRegistered = true
        }
    }

    override fun checkForAppUpdates(activity: Activity) {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                val availability = info.updateAvailability()
                val isUpdateAvailable = availability == UpdateAvailability.UPDATE_AVAILABLE ||
                        availability == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS

                Log.d(
                    TAG,
                    "checkForAppUpdates: availability=$availability, " +
                            "availableVersionCode=${info.availableVersionCode()}, " +
                            "isImmediateAllowed=${info.isImmediateUpdateAllowed}, " +
                            "isFlexibleAllowed=${info.isFlexibleUpdateAllowed}, " +
                            "priority=${info.updatePriority()}"
                )

                if (isUpdateAvailable) {
                    // Choose allowed update type with intelligent fallback:
                    // If preferred updateType is allowed, use it; otherwise fallback to the other allowed type!
                    val chosenType = when {
                        updateType == AppUpdateType.IMMEDIATE && info.isImmediateUpdateAllowed -> AppUpdateType.IMMEDIATE
                        updateType == AppUpdateType.FLEXIBLE && info.isFlexibleUpdateAllowed -> AppUpdateType.FLEXIBLE
                        info.isFlexibleUpdateAllowed -> {
                            Log.d(TAG, "Immediate update not allowed by Google Play, falling back to Flexible update.")
                            AppUpdateType.FLEXIBLE
                        }
                        info.isImmediateUpdateAllowed -> {
                            Log.d(TAG, "Flexible update not allowed by Google Play, falling back to Immediate update.")
                            AppUpdateType.IMMEDIATE
                        }
                        else -> null
                    }

                    if (chosenType != null) {
                        registerListenerIfNeeded()
                        val options = AppUpdateOptions.newBuilder(chosenType).build()
                        appUpdateManager.startUpdateFlowForResult(
                            info,
                            activity,
                            options,
                            UPDATE_REQUEST_CODE
                        )
                        Log.d(TAG, "Started in-app update flow with type=$chosenType")
                    } else {
                        Log.w(
                            TAG,
                            "Update is available (${info.availableVersionCode()}) but neither Immediate nor Flexible update is permitted by Google Play."
                        )
                    }
                } else {
                    Log.d(TAG, "No update available. Current status: $availability")
                }
            }
            .addOnFailureListener { e ->
                if (e is InstallException && e.errorCode == InstallErrorCode.ERROR_APP_NOT_OWNED) {
                    Log.d(TAG, "App not installed from Google Play (sideloaded/debug build); skipping update check.")
                } else {
                    Log.w(TAG, "Failed to check for updates: ${e.message}", e)
                }
            }
    }

    override fun onResume(activity: Activity) {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                if (info.updateAvailability() ==
                    UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    val options = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        activity,
                        options,
                        UPDATE_REQUEST_CODE
                    )
                } else if (info.installStatus() == InstallStatus.DOWNLOADED) {
                    appUpdateManager.completeUpdate()
                }
            }
            .addOnFailureListener { e ->
                if (e is InstallException && e.errorCode == InstallErrorCode.ERROR_APP_NOT_OWNED) {
                    Log.d(TAG, "App not installed from Google Play (sideloaded/debug build); skipping onResume update check.")
                } else {
                    Log.w(TAG, "onResume update check failed: ${e.message}")
                }
            }
    }

    override fun onDestroy() {
        if (isListenerRegistered) {
            appUpdateManager.unregisterListener(installStateUpdateListener)
            isListenerRegistered = false
        }
        scope.cancel()
    }
}