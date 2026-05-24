package com.karigo.ui.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat


/**
 * @author hazratummar
 * Created on 23/05/26
 */

@Stable
class PermissionHandlerState(
    val permission: AppPermission,
    initialState: PermissionState = PermissionState.NotRequested
){

    var state: PermissionState by mutableStateOf(initialState)
        internal set

    internal var launcher: (() -> Unit)? = null

    fun request() {launcher?.invoke()}

    val isGranted: Boolean get() = state == PermissionState.Granted
    val isPermanentlyDenied : Boolean get() = state == PermissionState.PermanentlyDenied
    val isDenied : Boolean get() = state == PermissionState.Denied
}

@Composable
fun rememberPermissionHandler(
    permission: AppPermission,
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    onPermanentlyDenied: () -> Unit = {}
): PermissionHandlerState {
    val context = LocalContext.current
    val activity = context.findActivity()

    val state = remember(permission) {
        PermissionHandlerState(
            permission = permission,
            initialState = context.checkPermissionState(permission)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val allGranted = results.values.all { it }
        val anyPermanentlyDenied = results.entries.any { (perm, granted) ->
            !granted && activity != null &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)
        }

        state.state = when {
            allGranted           -> PermissionState.Granted
            anyPermanentlyDenied -> PermissionState.PermanentlyDenied
            else                 -> PermissionState.Denied
        }

        when (state.state) {
            PermissionState.Granted           -> onGranted()
            PermissionState.PermanentlyDenied -> onPermanentlyDenied()
            else                               -> onDenied()
        }
    }

    return remember(permission) {
        state.also {
            it.launcher = { launcher.launch(permission.permission.toTypedArray()) }
        }
    }
}