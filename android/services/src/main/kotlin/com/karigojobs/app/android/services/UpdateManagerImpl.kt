package com.karigojobs.app.android.services

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
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



    // ✅ Listener declared and will be registered properly in init
    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> {
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
                Log.e(TAG, "Flexible update failed during install.")
            }
            else -> Unit
        }
    }

    init {
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdateListener)
        }
    }


    override fun checkForAppUpdates(activity: Activity) {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                val isUpdateAvailable =
                    info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAllowed = when (updateType) {
                    AppUpdateType.FLEXIBLE  -> info.isFlexibleUpdateAllowed
                    AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                    else -> false
                }

                // ✅ Optional: only nag user after update is stale enough (3+ days)
                val isStaleEnough = (info.clientVersionStalenessDays() ?: 0) >= 3

                if (isUpdateAvailable && isUpdateAllowed && isStaleEnough) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        activity,
                        UPDATE_REQUEST_CODE  // ✅ named constant
                    )
                }
            }
            .addOnFailureListener { e ->
                // ✅ Fail silently in production; log for debugging
                Log.e(TAG, "Failed to check for updates", e)
            }
    }

    override fun onResume(activity: Activity) {
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo
                .addOnSuccessListener { info ->
                    if (info.updateAvailability() ==
                        UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                    ) {
                        appUpdateManager.startUpdateFlowForResult(
                            info,
                            updateType,
                            activity,
                            UPDATE_REQUEST_CODE
                        )
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "onResume update check failed", e)
                }
        }

        // ✅ For flexible: if update was downloaded but not yet installed, complete it
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.appUpdateInfo
                .addOnSuccessListener { info ->
                    if (info.installStatus() == InstallStatus.DOWNLOADED) {
                        appUpdateManager.completeUpdate()
                    }
                }
        }
    }

    override fun onDestroy() {
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }
        scope.cancel()  // ✅ Cancel coroutine scope — no leaks
    }
}