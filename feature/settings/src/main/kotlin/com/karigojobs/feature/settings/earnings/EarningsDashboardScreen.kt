package com.karigojobs.feature.settings.earnings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.feature.settings.earnings.earningCompoent.EarningTopCard
import com.karigojobs.feature.settings.earnings.earningCompoent.GrowthTrendsCard
import com.karigojobs.feature.settings.earnings.earningCompoent.ProInsightsCard
import com.karigojobs.feature.settings.earnings.earningCompoent.TopClientCard
import com.karigojobs.feature.settings.earnings.earningCompoent.TradeRevenueCard
import com.karigojobs.presentation.earnings.EarningsEvent
import com.karigojobs.presentation.earnings.EarningsState
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.ProLockBadge
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.ChartBarActive
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.earnings_dashboard_title
import karigojobs.shared.resources.generated.resources.earnings_jobs_count
import karigojobs.shared.resources.generated.resources.earnings_label_avg_per_job
import karigojobs.shared.resources.generated.resources.earnings_label_best_month
import karigojobs.shared.resources.generated.resources.earnings_label_revenue
import karigojobs.shared.resources.generated.resources.earnings_monthly_revenue
import karigojobs.shared.resources.generated.resources.earnings_months_count
import karigojobs.shared.resources.generated.resources.earnings_no_clients
import karigojobs.shared.resources.generated.resources.earnings_no_trades
import karigojobs.shared.resources.generated.resources.earnings_revenue_by_trade
import karigojobs.shared.resources.generated.resources.earnings_this_year
import karigojobs.shared.resources.generated.resources.earnings_top_clients
import org.jetbrains.compose.resources.stringResource

@Composable
fun EarningsDashboardScreen(
    state: EarningsState,
    event: (EarningsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val currency = deviceInfo.currency
    val summary = state.summary

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = appColor.background,
        topBar = {
            KarigoTopAppBar(
                title = stringResource(Res.string.earnings_dashboard_title),
                onNavigationClick = { event(EarningsEvent.BackClick) },
                isNavBack = true,
                isDivider = false,
                action = {
                    ProLockBadge()
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item { Spacer(modifier = Modifier.height(dimens.Space.xs)) }

            // ── 1. Top 3 Metric Cards: Revenue, Avg Per Job, Best Month ──────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    // Revenue Card
                    EarningTopCard(
                        modifier = Modifier.weight(1f),
                        headerText = Res.string.earnings_label_revenue,
                        balanceText = "$currency${summary?.totalRevenue?.toLocaleString() ?: "0"}",
                        labelText = stringResource(
                            Res.string.earnings_jobs_count,
                            summary?.totalJobs ?: 0
                        )
                    )

                    // Avg Per Job Card
                    EarningTopCard(
                        modifier = Modifier.weight(1f),
                        headerText = Res.string.earnings_label_avg_per_job,
                        balanceText = "$currency${summary?.avgPerJob?.toLocaleString() ?: "0"}",
                        labelText = stringResource(Res.string.earnings_this_year)
                    )

                    // Best Month Card
                    val bestMonthLabel = summary?.bestMonthName?.takeIf { it.isNotBlank() && it != "N/A" } ?: "-"
                    EarningTopCard(
                        modifier = Modifier.weight(1f),
                        headerText = Res.string.earnings_label_best_month,
                        balanceText = "$currency${summary?.bestMonthRevenue?.toLocaleString() ?: "0"}",
                        labelText = bestMonthLabel
                    )
                }
            }

            // ── 2. PRO INSIGHTS Card (Profit vs Expenses split) ──────────────
            item {
                ProInsightsCard(
                    currency = currency,
                    estProfit = summary?.estProfitMargin ?: 0.0,
                    profitMarginPercent = summary?.profitMarginPercent ?: 35,
                    estExpenses = summary?.estExpenses ?: 0.0,
                    expenseMarginPercent = summary?.expenseMarginPercent ?: 65
                )
            }

            // ── 3. Monthly Revenue Bar Chart ─────────────────────────────────
            item {
                val monthsList = summary?.monthlyEarnings ?: emptyList()
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_monthly_revenue),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        if (monthsList.isNotEmpty()) {
                            Text(
                                text = stringResource(Res.string.earnings_months_count, monthsList.size),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = appColor.secondaryText
                                )
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                        shape = RoundedCornerShape(dimens.Radius.lg),
                        border = customCardBorder()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimens.Padding.base),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                        ) {
                            val maxRevenue = (monthsList.maxOfOrNull { it.revenue } ?: 1.0).coerceAtLeast(1.0)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimens.Space._8xl * 1.5f),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                monthsList.forEach { bar ->
                                    val barHeightFraction = if (bar.revenue > 0) {
                                        (bar.revenue / maxRevenue).toFloat().coerceIn(0.18f, 1f)
                                    } else 0.08f

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Bottom,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        // Vertical Bar
                                        Box(
                                            modifier = Modifier
                                                .width(dimens.Space.lg)
                                                .height(dimens.Space._8xl * 1.1f * barHeightFraction)
                                                .clip(
                                                    RoundedCornerShape(
                                                        topStart = dimens.Radius.xs,
                                                        topEnd = dimens.Radius.xs
                                                    )
                                                )
                                                .background(
                                                    if (bar.isCurrentMonth || bar.revenue == maxRevenue && bar.revenue > 0) {
                                                        ChartBarActive
                                                    } else {
                                                        appColor.iconBgColor
                                                    }
                                                )
                                        )

                                        Spacer(modifier = Modifier.height(dimens.Space.xs))

                                        // Month Name
                                        Text(
                                            text = bar.monthLabel,
                                            color = if (bar.isCurrentMonth) KarigojobsAccent else appColor.secondaryText,
                                            fontSize = dimens.Text._2xs,
                                            fontWeight = if (bar.isCurrentMonth) FontWeight.Bold else FontWeight.Normal
                                        )

                                        // Amount below month if revenue > 0
                                        if (bar.revenue > 0) {
                                            Text(
                                                text = "$currency${bar.revenue.toLocaleString()}",
                                                color = if (bar.isCurrentMonth) KarigojobsAccent else appColor.primaryText,
                                                fontSize = dimens.Text._2xs,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── 4. Growth Trends Card ────────────────────────────────────────
            item {
                GrowthTrendsCard(
                    currency = currency,
                    avgMonthly = summary?.avgMonthlyRevenue ?: 0.0,
                    vsLastMonthPercent = summary?.vsLastMonthPercent ?: 0.0,
                    busiestMonthName = summary?.busiestMonthName?.takeIf { it.isNotBlank() && it != "N/A" } ?: "-"
                )
            }

            // ── 5. Revenue by Trade ──────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_revenue_by_trade),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        ProLockBadge()
                    }

                    val tradeRevenues = summary?.tradeRevenues ?: emptyList()
                    if (tradeRevenues.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                            shape = RoundedCornerShape(dimens.Radius.lg),
                            border = customCardBorder()
                        ) {
                            Text(
                                text = stringResource(Res.string.earnings_no_trades),
                                color = appColor.secondaryText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(dimens.Padding.base)
                            )
                        }
                    } else {
                        tradeRevenues.forEach { trade ->
                            val tradeTitle = TradeType.entries.find {
                                it.name.equals(trade.tradeType, ignoreCase = true)
                            }?.displayNameRes?.let { stringResource(it) } ?: trade.tradeType

                            TradeRevenueCard(
                                tradeTitle = tradeTitle,
                                jobCount = trade.jobCount,
                                revenueText = "$currency${trade.revenue.toLocaleString()}",
                                fraction = trade.percentage
                            )
                        }
                    }
                }
            }

            // ── 6. Top Clients ───────────────────────────────────────────────
            item {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.earnings_top_clients),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        ProLockBadge()
                    }

                    val topClients = summary?.topClients ?: emptyList()
                    if (topClients.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                            shape = RoundedCornerShape(dimens.Radius.lg),
                            border = customCardBorder()
                        ) {
                            Text(
                                text = stringResource(Res.string.earnings_no_clients),
                                color = appColor.secondaryText,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(dimens.Padding.base)
                            )
                        }
                    } else {
                        topClients.forEachIndexed { index, client ->
                            TopClientCard(
                                index = index + 1,
                                clientName = client.name,
                                totalJobs = client.totalJob,
                                totalRevenue = "$currency${client.totalRevenue.toLocaleString()}"
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(dimens.Space.xl)) }
        }
    }
}
