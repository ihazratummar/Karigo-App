package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.share.model.PlanTier
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_badge_best_value
import karigojobs.shared.resources.generated.resources.paywall_badge_discount
import karigojobs.shared.resources.generated.resources.paywall_one_time
import karigojobs.shared.resources.generated.resources.paywall_per_month
import karigojobs.shared.resources.generated.resources.paywall_per_year
import karigojobs.shared.resources.generated.resources.paywall_tab_lifetime
import karigojobs.shared.resources.generated.resources.paywall_tab_monthly
import karigojobs.shared.resources.generated.resources.paywall_tab_yearly
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaywallTabSelector(
    selectedTab: PlanTier,
    monthlyPrice: String?,
    yearlyPrice: String?,
    lifetimePrice: String?,
    onTabSelected: (PlanTier) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.lg))
            .background(appColor.cardColors)
            .padding(dimens.Padding.xs),
        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
    ) {
        PaywallTabItem(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.paywall_tab_monthly),
            price = monthlyPrice ?: "…",
            subtext = stringResource(Res.string.paywall_per_month),
            badge = null,
            isSelected = selectedTab == PlanTier.PRO_MONTHLY,
            onClick = { onTabSelected(PlanTier.PRO_MONTHLY) }
        )

        PaywallTabItem(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.paywall_tab_yearly),
            price = yearlyPrice ?: "…",
            subtext = stringResource(Res.string.paywall_per_year),
            badge = stringResource(Res.string.paywall_badge_discount, "37%"),
            isSelected = selectedTab == PlanTier.PRO_YEARLY,
            onClick = { onTabSelected(PlanTier.PRO_YEARLY) }
        )

        PaywallTabItem(
            modifier = Modifier.weight(1f),
            label = stringResource(Res.string.paywall_tab_lifetime),
            price = lifetimePrice ?: "…",
            subtext = stringResource(Res.string.paywall_one_time),
            badge = stringResource(Res.string.paywall_badge_best_value),
            isSelected = selectedTab == PlanTier.PRO_LIFETIME,
            onClick = { onTabSelected(PlanTier.PRO_LIFETIME) }
        )
    }
}

@Composable
private fun PaywallTabItem(
    label: String,
    price: String,
    subtext: String,
    badge: String?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val bgColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.Radius.md))
            .background(bgColor)
            .border(dimens.Border.thin, borderColor, RoundedCornerShape(dimens.Radius.md))
            .clickable(onClick = onClick)
            .padding(vertical = dimens.Padding.sm, horizontal = dimens.Padding.xs),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (badge != null) {
                Text(
                    text = badge,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimens.Radius.xs))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .padding(horizontal = dimens.Padding.xs, vertical = dimens.Padding._2xs)
                )
                Spacer(Modifier.height(dimens.Space._2xs))
            } else {
                Spacer(Modifier.height(dimens.Space.sm))
            }

            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) appColor.primaryText else appColor.secondaryText
            )

            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )

            Text(
                text = subtext,
                style = MaterialTheme.typography.labelSmall,
                color = appColor.tertiaryText
            )
        }
    }
}
