package com.karigojobs.ui.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


/**
 * @author hazratummar
 * Created on 23/05/26
 */

import org.jetbrains.compose.resources.StringResource
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.dialog_permission_title_notification
import karigojobs.shared.resources.generated.resources.dialog_permission_desc_notification
import karigojobs.shared.resources.generated.resources.dialog_permission_permanently_denied_notification
import karigojobs.shared.resources.generated.resources.dialog_permission_title_contact
import karigojobs.shared.resources.generated.resources.dialog_permission_desc_contact
import karigojobs.shared.resources.generated.resources.dialog_permission_permanently_denied_contact

sealed class AppPermission(
    val permission: List<String>,
    val title: StringResource,
    val description: StringResource,
    val isPermanentlyDeniedMessage: StringResource
){

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    data object Notification : AppPermission(
        permission = listOf(Manifest.permission.POST_NOTIFICATIONS),
        title = Res.string.dialog_permission_title_notification,
        description = Res.string.dialog_permission_desc_notification,
        isPermanentlyDeniedMessage = Res.string.dialog_permission_permanently_denied_notification
    )

    data object Contact : AppPermission(
        permission = listOf(Manifest.permission.READ_CONTACTS),
        title = Res.string.dialog_permission_title_contact,
        description = Res.string.dialog_permission_desc_contact,
        isPermanentlyDeniedMessage = Res.string.dialog_permission_permanently_denied_contact
    )

}

sealed class  PermissionState {
    data object Granted : PermissionState()
    data object Denied : PermissionState()
    data object PermanentlyDenied : PermissionState()
    data object NotRequested : PermissionState()
}
