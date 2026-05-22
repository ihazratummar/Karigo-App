package com.karigo.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigo.presentation.onboarding.OnboardingIntent
import com.karigo.presentation.onboarding.OnboardingState
import com.karigo.share.model.TradeType
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
fun TradeSelectContent(
    modifier: Modifier = Modifier,
    onboardingState: OnboardingState = OnboardingState(),
    event: (OnboardingIntent) -> Unit = {}
) {

    Scaffold(
        bottomBar = {
            Button(
                onClick = {event(OnboardingIntent.ConfirmTrades)},
                enabled = onboardingState.canContinue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = onboardingState.continueLabel
                )
            }
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(it)
                .fillMaxSize(),
            contentPadding = PaddingValues(dimens.spacingLg),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingLg)
        ) {
            item {
                Text(
                    text = "Step 1 of 2",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "What do you trade?",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "Select your primary profession to help us personalize your experience.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.spacingSm),
                    verticalArrangement = Arrangement.spacedBy(dimens.spacingSm),
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
        }
    }

}

@Composable
private fun FlowRowScope.TradeItem(
    tradeType: TradeType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .weight(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(tradeType) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        try {
                            awaitRelease()
                        } finally {
                            isPressed = false
                        }
                    },
                    onTap = { onClick() }
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = when {
                isPressed -> MaterialTheme.colorScheme.primary.copy(0.2f)
                isSelected -> MaterialTheme.colorScheme.primary.copy(0.1f)
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        border = if (isSelected || isPressed) BorderStroke(
            width = dimens.spacingXxs,
            color = if (isPressed) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground
        ) else null
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.spacingSm),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(dimens.spacingLg)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimens.tradeIconSize)
                    .clip(KarigoShapes.large)
                    .background(color = tradeType.color().copy(0.1f))
            ) {
                Icon(
                    painter = painterResource(tradeType.icon()),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.tradeIconSize / 1.5f),
                    tint = tradeType.color()
                )
            }

            Text(
                text = tradeType.displayName,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = tradeType.description,
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
