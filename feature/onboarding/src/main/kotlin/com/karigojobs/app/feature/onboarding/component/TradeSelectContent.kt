package com.karigojobs.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.onboarding.OnboardingIntent
import com.karigojobs.presentation.onboarding.OnboardingState
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.onboarding_btn_continue
import karigojobs.shared.resources.generated.resources.onboarding_btn_trade_not_selected
import karigojobs.shared.resources.generated.resources.onboarding_progress
import karigojobs.shared.resources.generated.resources.onboarding_what_do_you_trade
import karigojobs.shared.resources.generated.resources.onboarding_what_do_you_trade_description
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 21/05/26
 */


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TradeSelectContent(
    modifier: Modifier = Modifier,
    onboardingState: OnboardingState = OnboardingState(),
    event: (OnboardingIntent) -> Unit = {}
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.Space.base, vertical = dimens.Space.sm)
            ) {
                IconButton(onClick = { event(OnboardingIntent.BackToWelcome) }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(dimens.Icon.base)
                    )
                }
            }
        },
        bottomBar = {
            Button(
                onClick = {event(OnboardingIntent.ConfirmTrades)},
                enabled = onboardingState.canContinue,
                modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.Space.base),
                shape = KarigojobsShapes.large,
                colors = ButtonDefaults.buttonColors(
                    disabledContentColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {

                val continueLabel = when {
                    onboardingState.selectedTrades.isEmpty() -> stringResource(Res.string.onboarding_btn_trade_not_selected)
                    else -> stringResource(Res.string.onboarding_btn_continue, onboardingState.selectedTrades.size.toLocaleString())
                }

                Text(
                    text = continueLabel,
                    modifier = Modifier.padding(dimens.Space.md),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (onboardingState.canContinue) MaterialTheme.colorScheme.onBackground else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(dimens.Space.lg),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.lg)
        ) {
            item {
                Text(
                    text = stringResource(Res.string.onboarding_progress, 1.toLocaleString(), 2.toLocaleString()),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = stringResource(Res.string.onboarding_what_do_you_trade),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = stringResource(Res.string.onboarding_what_do_you_trade_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
                    maxItemsInEachRow = 2
                ) {
                    TradeType.entries.forEach { tradeType ->
                        key(tradeType) {
                            TradeItem(
                                tradeType = tradeType,
                                isSelected = tradeType in onboardingState.selectedTrades,
                                onClick = { event(OnboardingIntent.ToggleTrade(tradeType)) }
                            )
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(dimens.Space.xl))
            }
        }
    }

}

@Composable
private fun FlowRowScope.TradeItem(
    tradeType: TradeType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .bounceClickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelected -> MaterialTheme.colorScheme.primary.copy(0.1f)
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (isSelected ) BorderStroke(
            width = dimens.Space._2xs,
            color = MaterialTheme.colorScheme.onBackground
        ) else null
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimens.Space.lg)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimens.Icon._2xl)
                    .clip(KarigojobsShapes.large)
                    .background(color = tradeType.color().copy(0.1f))
            ) {
                Icon(
                    painter = painterResource(tradeType.icon()),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.Icon._2xl / 1.5f),
                    tint = tradeType.color()
                )
            }

            Text(
                text = stringResource(tradeType.displayNameRes),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = stringResource(tradeType.descriptionRes),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 1,
                modifier = Modifier.basicMarquee(
                    iterations = 2000,
                    animationMode = MarqueeAnimationMode.Immediately,
                    initialDelayMillis = 5000
                )
            )
        }
    }
}
