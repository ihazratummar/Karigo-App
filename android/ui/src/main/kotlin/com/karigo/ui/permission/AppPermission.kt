package com.karigo.ui.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


/**
 * @author hazratummar
 * Created on 23/05/26
 */

sealed class AppPermission(
    val permission: List<String>,
    val title: String,
    val description: String,
    val isPermanentlyDeniedMessage: String
){

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object Notification : AppPermission(
        permission = listOf(Manifest.permission.POST_NOTIFICATIONS),
        title = "Enable Notification",
        description = "Get Remainder for unpaid invoices and upcoming jobs.",
        isPermanentlyDeniedMessage = "Notification permission was denied. Enable it in the settings"
    )

    data object Contact : AppPermission(
        permission = listOf(Manifest.permission.READ_CONTACTS),
        title = "Access Contact",
        description = "Quickly add clients from your phone contacts.",
        isPermanentlyDeniedMessage = "Contacts permission was denied. Enable it from app settings"
    )

}

sealed class  PermissionState {
    data object Granted : PermissionState()
    data object Denied : PermissionState()
    data object PermanentlyDenied : PermissionState()
    data object NotRequested : PermissionState()
}
