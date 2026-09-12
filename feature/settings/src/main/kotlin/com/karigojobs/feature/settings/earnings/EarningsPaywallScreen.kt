package com.karigojobs.feature.settings.earnings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.earnings.EarningsEvent
import com.karigojobs.presentation.earnings.EarningsState
import com.karigojobs.share.model.PlanTier
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_btn_cancel
import karigojobs.shared.resources.generated.resources.earnings_dashboard_title
import karigojobs.shared.resources.generated.resources.earnings_paywall_btn_unlock
import karigojobs.shared.resources.generated.resources.earnings_paywall_desc
import karigojobs.shared.resources.generated.resources.earnings_paywall_subtitle
import karigojobs.shared.resources.generated.resources.earnings_paywall_title
import karigojobs.shared.resources.generated.resources.paywall_badge_discount
import karigojobs.shared.resources.generated.resources.paywall_feat_earnings
import karigojobs.shared.resources.generated.resources.paywall_feat_material_lib
import karigojobs.shared.resources.generated.resources.paywall_feat_pdf_export
import karigojobs.shared.resources.generated.resources.paywall_feat_unlimited_clients
import karigojobs.shared.resources.generated.resources.paywall_feat_unlimited_jobs
import karigojobs.shared.resources.generated.resources.paywall_one_time
import karigojobs.shared.resources.generated.resources.paywall_per_month
import karigojobs.shared.resources.generated.resources.paywall_tab_lifetime
import karigojobs.shared.resources.generated.resources.paywall_tab_monthly
import karigojobs.shared.resources.generated.resources.paywall_tab_yearly
import org.jetbrains.compose.resources.stringResource

@Composable
fun EarningsPaywallScreen(
    state: EarningsState,
    event: (EarningsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val currency = deviceInfo.currency

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = appColor.background,
        topBar = {
            KarigoTopAppBar(
                title = stringResource(Res.string.earnings_dashboard_title),
                onNavigationClick = { event(EarningsEvent.BackClick) },
                isNavBack = true,
                isDivider = false
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimens.Padding.base)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Spacer(modifier = Modifier.height(dimens.Space.sm))

            // Large Icon Header Badge
            Box(
                modifier = Modifier
                    .size(dimens.Icon._6xl)
                    .clip(RoundedCornerShape(dimens.Radius.xl))
                    .background(KarigojobsAccent.copy(alpha = 0.15f))
                    .border(
                        BorderStroke(dimens.Border.thin, KarigojobsAccent.copy(alpha = 0.3f)),
                        RoundedCornerShape(dimens.Radius.xl)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.crown_fill),
                    contentDescription = null,
                    tint = KarigojobsAccent,
                    modifier = Modifier.size(dimens.Icon._2xl)
                )
            }

            // Title & Description
            Text(
                text = stringResource(Res.string.earnings_paywall_title),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(Res.string.earnings_paywall_subtitle),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = appColor.secondaryText
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(Res.string.earnings_paywall_desc),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = appColor.secondaryText.copy(alpha = 0.7f)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimens.Space.xs))

            // 3 Pricing Cards (Monthly, Yearly, Lifetime)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                val plans = listOf(
                    PlanCardData(
                        PlanTier.PRO_MONTHLY,
                        stringResource(Res.string.paywall_tab_monthly),
                        "${currency}79",
                        stringResource(Res.string.paywall_per_month),
                        null
                    ),
                    PlanCardData(
                        PlanTier.PRO_YEARLY,
                        stringResource(Res.string.paywall_tab_yearly),
                        "${currency}599",
                        null,
                        stringResource(Res.string.paywall_badge_discount, "37%")
                    ),
                    PlanCardData(
                        PlanTier.PRO_LIFETIME,
                        stringResource(Res.string.paywall_tab_lifetime),
                        "${currency}349",
                        stringResource(Res.string.paywall_one_time),
                        null
                    )
                )

                plans.forEach { plan ->
                    val isSelected = state.selectedPlanTier == plan.tier
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(dimens.Radius.md))
                            .background(if (isSelected) KarigojobsAccent.copy(alpha = 0.12f) else appColor.cardColors)
                            .border(
                                BorderStroke(
                                    dimens.Border.thin,
                                    if (isSelected) KarigojobsAccent else appColor.divider
                                ),
                                RoundedCornerShape(dimens.Radius.md)
                            )
                            .clickable { event(EarningsEvent.SelectPlan(plan.tier)) }
                            .padding(vertical = dimens.Space.base, horizontal = dimens.Space.xs),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                        ) {
                            Text(
                                text = plan.title,
                                color = if (isSelected) KarigojobsAccent else appColor.secondaryText,
                                fontSize = dimens.Text.xs,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = plan.price,
                                color = if (isSelected) KarigojobsAccent else appColor.primaryText,
                                fontSize = dimens.Text.lg,
                                fontWeight = FontWeight.ExtraBold
                            )
                            if (plan.subtext != null) {
                                Text(
                                    text = plan.subtext,
                                    color = if (isSelected) KarigojobsAccent else appColor.secondaryText,
                                    fontSize = dimens.Text._2xs
                                )
                            }
                            if (plan.badge != null) {
                                Text(
                                    text = plan.badge,
                                    color = Color(0xFF34C759),
                                    fontSize = dimens.Text._2xs,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimens.Space.xs))

            // Features Checklist
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                val features = listOf(
                    stringResource(Res.string.paywall_feat_unlimited_jobs),
                    stringResource(Res.string.paywall_feat_unlimited_clients),
                    stringResource(Res.string.paywall_feat_earnings),
                    stringResource(Res.string.paywall_feat_material_lib),
                    stringResource(Res.string.paywall_feat_pdf_export)
                )

                features.forEach { feature ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(dimens.Icon.sm)
                                .clip(CircleShape)
                                .background(KarigojobsAccent.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.check),
                                contentDescription = null,
                                tint = KarigojobsAccent,
                                modifier = Modifier.size(dimens.Icon._2xs)
                            )
                        }
                        Text(
                            text = feature,
                            color = appColor.primaryText,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimens.Space.md))

            // Primary Action: Unlock Button
            Button(
                onClick = { event(EarningsEvent.UnlockProClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = KarigojobsAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(dimens.Radius.xl),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Height.minTouch)
            ) {
                Text(
                    text = stringResource(Res.string.earnings_paywall_btn_unlock),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimens.Text.base
                )
            }

            // Secondary Action: Cancel Button
            Button(
                onClick = { event(EarningsEvent.BackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = appColor.iconBgColor,
                    contentColor = appColor.primaryText
                ),
                shape = RoundedCornerShape(dimens.Radius.xl),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Height.minTouch)
            ) {
                Text(
                    text = stringResource(Res.string.common_btn_cancel),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimens.Text.base
                )
            }

            Spacer(modifier = Modifier.height(dimens.Space.xl))
        }
    }
}

private data class PlanCardData(
    val tier: PlanTier,
    val title: String,
    val price: String,
    val subtext: String?,
    val badge: String?
)
