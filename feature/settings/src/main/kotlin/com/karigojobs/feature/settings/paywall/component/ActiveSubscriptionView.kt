package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.karigojob.share.utils.EpochUtils
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.monetization.MonetizationEvent
import com.karigojobs.presentation.monetization.MonetizationState
import com.karigojobs.presentation.monetization.MonetizationViewModel
import com.karigojobs.share.model.PlanTier
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.billed_via_google_play
import karigojobs.shared.resources.generated.resources.billed_via_google_play_desc
import karigojobs.shared.resources.generated.resources.cancel_subscription
import karigojobs.shared.resources.generated.resources.lifetime_days_text
import karigojobs.shared.resources.generated.resources.lifetime_renewal_text
import karigojobs.shared.resources.generated.resources.manage_in_google_play
import karigojobs.shared.resources.generated.resources.manage_pro_plan
import karigojobs.shared.resources.generated.resources.need_help_contact
import karigojobs.shared.resources.generated.resources.paywall_tab_lifetime
import karigojobs.shared.resources.generated.resources.paywall_tab_monthly
import karigojobs.shared.resources.generated.resources.paywall_tab_yearly
import karigojobs.shared.resources.generated.resources.your_subscription_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActiveSubscriptionView(
    state: MonetizationState,
    viewModel: MonetizationViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val planTier = state.proStatus.planTier
    val isLifetime = planTier == PlanTier.PRO_LIFETIME

    val now = EpochUtils.now()
    val purchaseDateMs = state.proStatus.purchaseDateEpochMs ?: now
    val effectiveExpDateMs = state.proStatus.expirationDateEpochMs ?: when (planTier) {
        PlanTier.PRO_MONTHLY -> purchaseDateMs + 30L * 24 * 60 * 60 * 1000
        PlanTier.PRO_YEARLY -> purchaseDateMs + 365L * 24 * 60 * 60 * 1000
        else -> null
    }

    val activatedOnFormatted = EpochUtils.formatDate(purchaseDateMs)
    val nextRenewalFormatted = if (isLifetime || effectiveExpDateMs == null) {
        stringResource(Res.string.lifetime_renewal_text)
    } else {
        EpochUtils.formatDate(effectiveExpDateMs)
    }

    val daysRemainingText = if (isLifetime || effectiveExpDateMs == null) {
        stringResource(Res.string.lifetime_days_text)
    } else {
        val remMs = (effectiveExpDateMs - now).coerceAtLeast(0L)
        val remDays = (remMs / (1000 * 60 * 60 * 24)).toInt()
        "$remDays days"
    }

    val progressFraction = if (isLifetime || effectiveExpDateMs == null) {
        1.0f
    } else {
        val totalMs = (effectiveExpDateMs - purchaseDateMs).coerceAtLeast(1L)
        val remMs = (effectiveExpDateMs - now).coerceAtLeast(0L)
        (remMs.toFloat() / totalMs.toFloat()).coerceIn(0f, 1f)
    }

    val planTitle = when (planTier) {
        PlanTier.PRO_YEARLY -> stringResource(Res.string.paywall_tab_yearly) + " Plan"
        PlanTier.PRO_LIFETIME -> stringResource(Res.string.paywall_tab_lifetime) + " Plan"
        else -> stringResource(Res.string.paywall_tab_monthly) + " Plan"
    }

    val priceLabel = when (planTier) {
        PlanTier.PRO_YEARLY -> "${state.yearlyPackage?.formattedPrice ?: "₹599.00"} / year"
        PlanTier.PRO_LIFETIME -> "${state.lifetimePackage?.formattedPrice ?: "₹349.00"} one-time"
        else -> "${state.monthlyPackage?.formattedPrice ?: "₹79.00"} / month"
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(appColor.background)
            .contentHorizontalPadding(),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
    ) {
        // Back Button
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.Padding.md),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(dimens.Height.minTouch / 1.2f)
                        .clip(CircleShape)
                        .background(appColor.cardColors)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Back",
                        tint = appColor.primaryText,
                        modifier = Modifier.size(dimens.Icon.sm)
                    )
                }
            }
        }

        // Crown Header Badge & Title
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(dimens.Icon._5xl)
                        .clip(RoundedCornerShape(dimens.Radius.lg))
                        .background(Color(0xFF0E2929))
                        .border(BorderStroke(dimens.Border.thin, Color(0xFF00FFCC).copy(alpha = 0.4f)), RoundedCornerShape(dimens.Radius.lg)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.crown_fill),
                        contentDescription = null,
                        tint = Color(0xFF00FFCC),
                        modifier = Modifier.size(dimens.Icon.xl)
                    )
                }

                Spacer(Modifier.height(dimens.Space.sm))

                Text(
                    text = stringResource(Res.string.your_subscription_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = appColor.primaryText
                )
                Spacer(Modifier.height(dimens.Space._2xs))
                Text(
                    text = stringResource(Res.string.manage_pro_plan),
                    style = MaterialTheme.typography.bodyMedium,
                    color = appColor.secondaryText
                )
            }
        }

        // Active Subscription Card
        item {
            ActiveSubscriptionCard(
                planTitle = planTitle,
                priceLabel = priceLabel,
                activatedOnFormatted = activatedOnFormatted,
                nextRenewalFormatted = nextRenewalFormatted,
                daysRemainingText = daysRemainingText,
                progressFraction = progressFraction
            )
        }

        // What's included in Pro List
        item {
            PaywallFeaturesComparison()
        }

        // Billed via Google Play Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimens.Radius.md))
                    .background(appColor.cardColors)
                    .border(BorderStroke(dimens.Border.thin, appColor.divider), RoundedCornerShape(dimens.Radius.md))
                    .padding(dimens.Padding.md)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm), verticalAlignment = Alignment.Top) {
                    Icon(
                        painter = painterResource(R.drawable.ic_google_play),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(dimens.Icon.sm)
                    )
                    Column {
                        Text(
                            text = stringResource(Res.string.billed_via_google_play),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = appColor.primaryText
                        )
                        Spacer(Modifier.height(dimens.Space._2xs))
                        Text(
                            text = stringResource(Res.string.billed_via_google_play_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = appColor.secondaryText
                        )
                    }
                }
            }
        }

        // Action Buttons
        item {
            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)) {
                Button(
                    onClick = { viewModel.onEvent(MonetizationEvent.OpenGooglePlaySubscriptions) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.Height.buttonLg),
                    shape = RoundedCornerShape(dimens.Radius.md),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appColor.cardColors,
                        contentColor = appColor.primaryText
                    ),
                    border = BorderStroke(dimens.Border.thin, appColor.divider)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_google_play),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(dimens.Icon.xs)
                        )
                        Text(
                            text = stringResource(Res.string.manage_in_google_play),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.onEvent(MonetizationEvent.ToggleCancelDialog(true)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.Height.buttonLg),
                    shape = RoundedCornerShape(dimens.Radius.md),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = BorderStroke(dimens.Border.thin, MaterialTheme.colorScheme.error.copy(alpha = 0.6f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(dimens.Icon.xs)
                        )
                        Text(
                            text = stringResource(Res.string.cancel_subscription),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Footer Text
        item {
            Text(
                text = stringResource(Res.string.need_help_contact),
                style = MaterialTheme.typography.labelSmall,
                color = appColor.tertiaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimens.Padding.md)
            )
        }
    }
}
