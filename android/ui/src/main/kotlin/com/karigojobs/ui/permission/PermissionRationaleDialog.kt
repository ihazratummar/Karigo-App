package com.karigojobs.ui.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */




import org.jetbrains.compose.resources.stringResource
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.dialog_permission_btn_deny
import karigojobs.shared.resources.generated.resources.dialog_permission_btn_allow
import karigojobs.shared.resources.generated.resources.dialog_permission_btn_settings

@Composable
fun PermissionRationaleDialog(
    handlerState: PermissionHandlerState,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    if (!handlerState.isGranted) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = KarigojobsShapes.medium,
                color = Color(0xFF111111),
                tonalElevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(dimens.Padding.xl),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    Text(
                        text = stringResource(handlerState.permission.title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE8E6E0)
                    )

                    Text(
                        text = if (handlerState.isPermanentlyDenied)
                            stringResource(handlerState.permission.isPermanentlyDeniedMessage)
                        else
                            stringResource(handlerState.permission.description),
                        fontSize = 14.sp,
                        color = Color(0xFF8A8880),
                        lineHeight = 22.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Dismiss
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF8A8880)
                            )
                        ) {
                            Text(stringResource(Res.string.dialog_permission_btn_deny))
                        }

                        // Grant / Settings
                        Button(
                            onClick = {
                                if (handlerState.isPermanentlyDenied) {
                                    context.openAppSettings()
                                } else {
                                    handlerState.request()
                                }
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00D4A0),
                                contentColor = Color.Black
                            )
                        ) {
                            Text(
                                text = if (handlerState.isPermanentlyDenied)
                                    stringResource(Res.string.dialog_permission_btn_settings)
                                else
                                    stringResource(Res.string.dialog_permission_btn_allow),
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF000000)
                            )
                        }
                    }
                }
            }
        }
    }
}