package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.btn_cancel_anyway
import karigojobs.shared.resources.generated.resources.btn_keep_plan
import karigojobs.shared.resources.generated.resources.cancel_dialog_desc
import karigojobs.shared.resources.generated.resources.cancel_dialog_loss_box
import karigojobs.shared.resources.generated.resources.cancel_dialog_notice
import karigojobs.shared.resources.generated.resources.cancel_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun CancelSubscriptionModal(
    onDismiss: () -> Unit,
    onConfirmCancel: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimens.Radius.xl))
                .background(Color(0xFF14181B))
                .border(BorderStroke(dimens.Border.thin, appColor.divider), RoundedCornerShape(dimens.Radius.xl))
                .padding(dimens.Padding.lg)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Box(
                    modifier = Modifier
                        .size(dimens.Icon._3xl)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.alert),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(dimens.Icon.md)
                    )
                }

                Text(
                    text = stringResource(Res.string.cancel_dialog_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = appColor.primaryText,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(Res.string.cancel_dialog_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = appColor.secondaryText,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dimens.Radius.md))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                        .border(BorderStroke(dimens.Border.thin, MaterialTheme.colorScheme.error.copy(alpha = 0.4f)), RoundedCornerShape(dimens.Radius.md))
                        .padding(dimens.Padding.md)
                ) {
                    Text(
                        text = stringResource(Res.string.cancel_dialog_loss_box),
                        style = MaterialTheme.typography.bodySmall,
                        color = appColor.secondaryText
                    )
                }

                Text(
                    text = stringResource(Res.string.cancel_dialog_notice),
                    style = MaterialTheme.typography.labelSmall,
                    color = appColor.tertiaryText,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(dimens.Height.buttonLg),
                        shape = RoundedCornerShape(dimens.Radius.md),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = appColor.primaryText),
                        border = BorderStroke(dimens.Border.thin, appColor.divider)
                    ) {
                        Text(
                            text = stringResource(Res.string.btn_keep_plan),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onConfirmCancel,
                        modifier = Modifier
                            .weight(1f)
                            .height(dimens.Height.buttonLg),
                        shape = RoundedCornerShape(dimens.Radius.md),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.btn_cancel_anyway),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
