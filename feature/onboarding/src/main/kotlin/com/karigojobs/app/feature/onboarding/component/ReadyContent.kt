package com.karigojobs.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.onboarding.OnboardingIntent
import com.karigojobs.presentation.onboarding.OnboardingState
import com.karigojobs.ui.color
import com.karigojobs.ui.icon
import com.karigojobs.ui.permission.AppPermission
import com.karigojobs.ui.permission.PermissionRationaleDialog
import com.karigojobs.ui.permission.rememberPermissionHandler
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 21/05/26
 */


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ReadyContent(
    modifier: Modifier = Modifier,
    onboardingState: OnboardingState = OnboardingState(),
    event: (OnboardingIntent) -> Unit = {}
) {

    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionHandler(
            permission = AppPermission.Notification,
            onGranted = { event(OnboardingIntent.LetsGo) },
            onDenied = { event(OnboardingIntent.LetsGo) },
            onPermanentlyDenied = { event(OnboardingIntent.LetsGo) }
        )
    } else null

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog && notificationPermission != null) {
        PermissionRationaleDialog(
            handlerState = notificationPermission,
            onDismiss = { showDialog = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(dimens.Space.lg),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.lg),
    ) {
        item {
            Text(
                text = "Step 2 of 2",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Text(
                text = "You're all set",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "We've loaded starter materials for your works.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        items(onboardingState.selectedTrades.toList()) { trade ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = KarigojobsShapes.medium,
                border = BorderStroke(
                    width = dimens.Space._2xs / 1.1f,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.Space.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon._2xl * 1.3f)
                            .clip(KarigojobsShapes.medium)
                            .background(color = trade.color().copy(0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(trade.icon()),
                            contentDescription = null,
                            modifier = Modifier.size(dimens.Icon.sm),
                            tint = trade.color()
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {

                        Text(
                            text = trade.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "10 starter items loaded",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Spacer(Modifier.weight(1f))
                    Icon(
                        painter = painterResource(R.drawable.check),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.Icon.xs),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = KarigojobsShapes.medium,
                border = BorderStroke(
                    width = dimens.Space._2xs / 1.5f,
                    color = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.Space.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.stack),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.Icon.base),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Text(
                            text = "${onboardingState.selectedTrades.size * 10} materials ready",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "Edit prices anytime in Material Library",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    when {
                        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> {
                            event(OnboardingIntent.LetsGo)
                        }
                        notificationPermission?.isGranted == true -> {
                            event(OnboardingIntent.LetsGo)
                        }
                        else -> showDialog = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = KarigojobsShapes.medium
            ) {
                Text(
                    text = "Let's Go",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            TextButton(
                onClick = {
                    event(OnboardingIntent.BackToTrades)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Back to trade selection",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

    }

}
