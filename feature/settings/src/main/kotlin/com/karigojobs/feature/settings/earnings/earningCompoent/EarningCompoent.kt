package com.karigojobs.feature.settings.earnings.earningCompoent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.ChartBarInactive
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.RankBronze
import com.karigojobs.ui.theme.RankGold
import com.karigojobs.ui.theme.RankSilver
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_jobs_count
import karigojobs.shared.resources.generated.resources.earnings_avg_monthly
import karigojobs.shared.resources.generated.resources.earnings_busiest_month
import karigojobs.shared.resources.generated.resources.earnings_est_expenses
import karigojobs.shared.resources.generated.resources.earnings_est_profit
import karigojobs.shared.resources.generated.resources.earnings_expenses_percent
import karigojobs.shared.resources.generated.resources.earnings_growth_trends
import karigojobs.shared.resources.generated.resources.earnings_materials_labour
import karigojobs.shared.resources.generated.resources.earnings_profit_margin
import karigojobs.shared.resources.generated.resources.earnings_profit_percent
import karigojobs.shared.resources.generated.resources.earnings_pro_insights
import karigojobs.shared.resources.generated.resources.earnings_vs_last_month
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class EarningTopCardData(
    val headerText: StringResource,
    val balanceText: String,
    val labelText: String
)

@Composable
fun EarningTopCard(
    modifier: Modifier = Modifier,
    headerText: StringResource,
    balanceText: String,
    labelText: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = RoundedCornerShape(dimens.Radius.md),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.Padding.sm),
            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
        ) {
            Text(
                text = stringResource(headerText),
                color = appColor.secondaryText,
                fontSize = dimens.Text._2xs,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = balanceText,
                color = appColor.primaryText,
                fontSize = dimens.Text.base,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )
            Text(
                text = labelText,
                color = appColor.secondaryText,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ProInsightsCard(
    modifier: Modifier = Modifier,
    currency: String,
    estProfit: Double,
    profitMarginPercent: Int,
    estExpenses: Double,
    expenseMarginPercent: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.accentBg),
        shape = RoundedCornerShape(dimens.Radius.lg),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            // Header: Crown icon + PRO INSIGHTS
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
            ) {
                Icon(
                    painter = painterResource(R.drawable.crown_fill),
                    contentDescription = null,
                    tint = KarigojobsAccent,
                    modifier = Modifier.size(dimens.Icon.xs)
                )
                Text(
                    text = stringResource(Res.string.earnings_pro_insights),
                    color = KarigojobsAccent,
                    fontSize = dimens.Text.xs,
                    fontWeight = FontWeight.Bold
                )
            }

            // Columns: Est. Profit & Est. Expenses
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                ) {
                    Text(
                        text = stringResource(Res.string.earnings_est_profit),
                        color = appColor.secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "$currency${estProfit.toLocaleString()}",
                        color = KarigojobsAccent,
                        fontSize = dimens.Text.xl,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = stringResource(Res.string.earnings_profit_margin, profitMarginPercent),
                        color = KarigojobsAccent,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                ) {
                    Text(
                        text = stringResource(Res.string.earnings_est_expenses),
                        color = appColor.secondaryText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "$currency${estExpenses.toLocaleString()}",
                        color = appColor.primaryText,
                        fontSize = dimens.Text.xl,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = stringResource(Res.string.earnings_materials_labour),
                        color = appColor.secondaryText,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Split Progress Bar
            val profitFraction = (profitMarginPercent.toFloat() / 100f).coerceIn(0.05f, 0.95f)
            val expenseFraction = (1f - profitFraction).coerceIn(0.05f, 0.95f)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Space.sm)
                    .clip(RoundedCornerShape(dimens.Radius.full))
                    .background(appColor.iconBgColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(profitFraction)
                        .background(KarigojobsAccent)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(expenseFraction)
                        .background(appColor.iconBgColor)
                )
            }

            // Sub labels below bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.earnings_profit_percent, profitMarginPercent),
                    color = KarigojobsAccent,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(Res.string.earnings_expenses_percent, expenseMarginPercent),
                    color = appColor.secondaryText,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun GrowthTrendsCard(
    modifier: Modifier = Modifier,
    currency: String,
    avgMonthly: Double,
    vsLastMonthPercent: Double,
    busiestMonthName: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = RoundedCornerShape(dimens.Radius.lg),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
            ) {
                Icon(
                    painter = painterResource(R.drawable.crown_fill),
                    contentDescription = null,
                    tint = KarigojobsAccent,
                    modifier = Modifier.size(dimens.Icon.xs)
                )
                Text(
                    text = stringResource(Res.string.earnings_growth_trends),
                    color = appColor.primaryText,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                // Avg Monthly
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = appColor.iconBgColor),
                    shape = RoundedCornerShape(dimens.Radius.md)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.Padding.sm),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_avg_monthly),
                            color = appColor.secondaryText,
                            fontSize = dimens.Text._2xs,
                            maxLines = 1
                        )
                        Text(
                            text = "$currency${avgMonthly.toLocaleString()}",
                            color = appColor.primaryText,
                            fontSize = dimens.Text.sm,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }

                // Vs Last Month
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = appColor.iconBgColor),
                    shape = RoundedCornerShape(dimens.Radius.md)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.Padding.sm),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_vs_last_month),
                            color = appColor.secondaryText,
                            fontSize = dimens.Text._2xs,
                            maxLines = 1
                        )
                        val sign = if (vsLastMonthPercent >= 0) "+" else ""
                        Text(
                            text = "$sign${vsLastMonthPercent.toInt()}%",
                            color = Color(0xFF34C759),
                            fontSize = dimens.Text.sm,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }

                // Busiest Month
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = appColor.iconBgColor),
                    shape = RoundedCornerShape(dimens.Radius.md)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.Padding.sm),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_busiest_month),
                            color = appColor.secondaryText,
                            fontSize = dimens.Text._2xs,
                            maxLines = 1
                        )
                        Text(
                            text = busiestMonthName,
                            color = KarigojobsAccent,
                            fontSize = dimens.Text.sm,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TradeRevenueCard(
    modifier: Modifier = Modifier,
    tradeTitle: String,
    jobCount: Int,
    revenueText: String,
    fraction: Float
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = RoundedCornerShape(dimens.Radius.lg),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)) {
                    Text(
                        text = tradeTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = stringResource(Res.string.common_jobs_count, jobCount),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = appColor.secondaryText
                        )
                    )
                }

                Text(
                    text = revenueText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Horizontal progress bar
            val safeFraction = fraction.coerceIn(0.02f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Space._2xs * 2.5f)
                    .clip(RoundedCornerShape(dimens.Radius.full))
                    .background(appColor.iconBgColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(safeFraction)
                        .clip(RoundedCornerShape(dimens.Radius.full))
                        .background(KarigojobsAccent)
                )
            }
        }
    }
}

@Composable
fun TopClientCard(
    modifier: Modifier = Modifier,
    index: Int,
    clientName: String,
    totalJobs: Int,
    totalRevenue: String
) {
    val (badgeBg, badgeText) = when (index) {
        1 -> RankGold.copy(alpha = 0.2f) to RankGold
        2 -> RankSilver.copy(alpha = 0.2f) to RankSilver
        3 -> RankBronze.copy(alpha = 0.2f) to RankBronze
        else -> appColor.iconBgColor to appColor.secondaryText
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = RoundedCornerShape(dimens.Radius.lg),
        border = customCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            // Rank Number Badge
            Box(
                modifier = Modifier
                    .size(dimens.Height.minTouch / 1.5f)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = index.toLocaleString(),
                    color = badgeText,
                    fontSize = dimens.Text.sm,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
            ) {
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(Res.string.common_jobs_count, totalJobs),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = appColor.secondaryText
                    )
                )
            }

            Text(
                text = totalRevenue,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}