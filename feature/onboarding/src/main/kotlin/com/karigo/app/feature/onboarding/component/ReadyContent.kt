package com.karigo.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.karigo.app.android.ui.R
import com.karigo.presentation.onboarding.OnboardingIntent
import com.karigo.presentation.onboarding.OnboardingState
import com.karigo.ui.color
import com.karigo.ui.icon
import com.karigo.ui.theme.KarigoShapes
import com.karigo.ui.theme.KarigoThemePreview
import com.karigo.ui.theme.dimens


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

    KarigoThemePreview {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(dimens.spacingLg),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingLg),
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
                    shape = KarigoShapes.medium,
                    border = BorderStroke(
                        width = dimens.spacingXxs / 1.1f,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.spacingLg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.spacingSm)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(dimens.tradeIconSize * 1.3f)
                                .clip(KarigoShapes.medium)
                                .background(color = trade.color().copy(0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(trade.icon()),
                                contentDescription = null,
                                modifier = Modifier.size(dimens.iconMd),
                                tint = trade.color()
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.spacingSm)
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
                            modifier = Modifier.size(dimens.iconSm),
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
                    shape = KarigoShapes.medium,
                    border = BorderStroke(
                        width = dimens.spacingXxs / 1.5f,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.spacingLg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.spacingMd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.stack),
                            contentDescription = null,
                            modifier = Modifier.size(dimens.iconLg),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.spacingSm)
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
                    onClick = {event(OnboardingIntent.LetsGo)},
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigoShapes.medium
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

}
