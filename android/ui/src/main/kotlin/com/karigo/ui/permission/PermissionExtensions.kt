package com.karigo.ui.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat


/**
 * @author hazratummar
 * Created on 23/05/26
 */
 

fun Context.checkPermissionState(permission: AppPermission): PermissionState {
    val allGranted = permission.permission.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
    return  if (allGranted) PermissionState.Granted else PermissionState.NotRequested
}

fun Context.findActivity() : Activity ? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return  context
        context = context.baseContext
    }
    return null
}

fun Context.openAppSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).also { intent ->
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }
}