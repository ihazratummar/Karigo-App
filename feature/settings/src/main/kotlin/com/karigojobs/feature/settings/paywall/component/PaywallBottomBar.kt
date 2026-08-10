package com.karigojobs.feature.settings.paywall.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.karigojobs.share.model.PlanTier
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_btn_lifetime
import karigojobs.shared.resources.generated.resources.paywall_btn_monthly
import karigojobs.shared.resources.generated.resources.paywall_btn_yearly
import karigojobs.shared.resources.generated.resources.paywall_footer_preview
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaywallBottomBar(
    selectedTab: PlanTier,
    priceLabel: String?,
    isLoadingPrices: Boolean,
    isPurchaseLoading: Boolean,
    onSubscribeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonText = when {
        isLoadingPrices -> stringResource(Res.string.paywall_btn_monthly, "…")
        priceLabel != null -> when (selectedTab) {
            PlanTier.PRO_MONTHLY -> stringResource(Res.string.paywall_btn_monthly, priceLabel)
            PlanTier.PRO_YEARLY -> stringResource(Res.string.paywall_btn_yearly, priceLabel)
            PlanTier.PRO_LIFETIME -> stringResource(Res.string.paywall_btn_lifetime, priceLabel)
            else -> stringResource(Res.string.paywall_btn_monthly, priceLabel)
        }
        else -> stringResource(Res.string.paywall_btn_monthly, "—")
    }

    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(appColor.background)
            .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.sm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSubscribeClick,
            enabled = !isPurchaseLoading && !isLoadingPrices,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.Height.buttonLg),
            shape = RoundedCornerShape(dimens.Radius.md),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
            )
        ) {
            if (isPurchaseLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(dimens.Icon.sm)
                            .rotate(rotation),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = dimens.Border.thin
                    )
                    Text("Processing…", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            } else if (isLoadingPrices) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(dimens.Icon.sm),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = dimens.Border.thin
                    )
                    Spacer(Modifier.width(dimens.Space.xs))
                    Text("Loading prices…", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            } else {
                Text(buttonText, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(dimens.Space.xs))

        Text(
            text = stringResource(Res.string.paywall_footer_preview),
            style = MaterialTheme.typography.labelSmall,
            color = appColor.tertiaryText,
            textAlign = TextAlign.Center
        )
    }
}
