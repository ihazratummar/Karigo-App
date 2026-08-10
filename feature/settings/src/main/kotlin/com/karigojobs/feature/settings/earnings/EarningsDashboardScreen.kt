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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.karigojobs.app.android.ui.R
import com.karigojobs.feature.settings.earnings.earningCompoent.EarningTopCard
import com.karigojobs.feature.settings.earnings.earningCompoent.EarningTopCardData
import com.karigojobs.feature.settings.earnings.earningCompoent.TopClientCard
import com.karigojobs.presentation.earnings.EarningsEvent
import com.karigojobs.presentation.earnings.EarningsState
import com.karigojobs.share.model.EarningsTimeframe
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.ProLockBadge
import com.karigojobs.ui.common.SectionWithTitle
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.toLocaleString
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.*
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
        containerColor = MaterialTheme.colorScheme.background,
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

            // ── 1. Top 2 Metric Cards: Revenue & Avg Per Job ───────────────────────
            item {

                val cardData = listOf(
                    EarningTopCardData(
                        headerText = Res.string.earnings_label_revenue,
                        balanceText = "$currency${summary?.totalRevenue?.toLocaleString() ?: "0"}",
                        labelText = stringResource(
                            Res.string.earnings_jobs_count,
                            summary?.totalJobs ?: 0
                        )
                    ),
                    EarningTopCardData(
                        headerText = Res.string.earnings_label_avg_per_job,
                        balanceText = "$currency${summary?.avgPerJob?.toLocaleString() ?: "0"}",
                        labelText = stringResource(Res.string.earnings_this_year)
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    cardData.forEach { data ->
                        EarningTopCard(
                            modifier = Modifier.weight(1f),
                            headerText = data.headerText,
                            balanceText = data.balanceText,
                            labelText = data.labelText
                        )
                    }
                }
            }

            // ── 2. Monthly Revenue Bar Chart ───────────────────────────────
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                    shape = RoundedCornerShape(dimens.Radius.md),
                    border = customCardBorder()
                ) {
                    Column(
                        modifier = Modifier.padding(dimens.Padding.base),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                    ) {
                        // Title + Timeframe Filter Chips (6 Months, 1 Year, Lifetime)
                        Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                            Text(
                                text = stringResource(Res.string.earnings_monthly_revenue),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = appColor.primaryText,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                            ) {
                                EarningsTimeframe.entries.forEach { tf ->
                                    val isSelected = state.selectedTimeframe == tf
                                    val chipText = when (tf) {
                                        EarningsTimeframe.SIX_MONTHS -> stringResource(Res.string.earnings_tf_6_months)
                                        EarningsTimeframe.ONE_YEAR -> stringResource(Res.string.earnings_tf_1_year)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(dimens.Radius.full))
                                            .background(
                                                if (isSelected) Color(0xFF0E2929) else Color(
                                                    0xFF1E2124
                                                )
                                            )
                                            .border(
                                                BorderStroke(
                                                    dimens.Border.thin,
                                                    if (isSelected) Color(0xFF00FFCC) else Color.Transparent
                                                ),
                                                RoundedCornerShape(dimens.Radius.full)
                                            )
                                            .clickable { event(EarningsEvent.SelectTimeframe(tf)) }
                                            .padding(
                                                horizontal = dimens.Space.base,
                                                vertical = dimens.Space._2xs
                                            )
                                    ) {
                                        Text(
                                            text = chipText,
                                            color = if (isSelected) Color(0xFF00FFCC) else appColor.secondaryText,
                                            fontSize = dimens.Text._2xs,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        // Sleek Vertical Bar Chart
                        val maxRevenue = (summary?.monthlyEarnings?.maxOfOrNull { it.revenue }
                            ?: 1.0).coerceAtLeast(1.0)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimens.Space._8xl * 1.5f),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            summary?.monthlyEarnings?.forEach { bar ->
                                val barHeightFraction =
                                    if (bar.revenue > 0) (bar.revenue / maxRevenue).toFloat()
                                        .coerceIn(0.2f, 1f) else 0.08f
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom,
                                    modifier = Modifier.weight(1f)
                                ) {
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
                                                if (bar.isCurrentMonth && bar.revenue > 0) Color(
                                                    0xFF00FFCC
                                                )
                                                else if (bar.revenue > 0) Color(0xFF00B388)
                                                else Color(0xFF24272B)
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(dimens.Space.xs))
                                    Text(
                                        text = bar.monthLabel,
                                        color = if (bar.isCurrentMonth) Color(0xFF00FFCC) else appColor.secondaryText,
                                        fontSize = dimens.Text._2xs,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (bar.revenue > 0) {
                                        Text(
                                            text = "$currency${bar.revenue.toLocaleString()}",
                                            color = Color(0xFF00FFCC),
                                            fontSize = dimens.Text._2xs,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ── 3. Top Clients ─────────────────────────────────────────────
            item {
                SectionWithTitle(
                    title = stringResource(Res.string.earnings_top_clients)
                ) {
                    val topClients = summary?.topClients ?: emptyList()
                    if (topClients.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.earnings_no_clients),
                            color = appColor.secondaryText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        topClients.forEachIndexed { index, client ->
                            val rank = index + 1
                            TopClientCard(
                                modifier = Modifier.fillMaxWidth(),
                                index = rank,
                                clientName = client.name,
                                totalJobs = client.totalJob,
                                totalRevenue = "$currency${client.totalRevenue.toLocaleString()}"
                            )
                        }
                    }
                }
            }

            // ── 4. Growth Trend (With XML Crown Drawable) ───────────────────
            item {
                SectionWithTitle(
                    title =  stringResource(Res.string.earnings_growth_trend)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                        shape = RoundedCornerShape(dimens.Radius.md),
                        border = customCardBorder()
                    ) {
                        Column(
                            modifier = Modifier.padding(dimens.Padding.base),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                            ) {
                                // Vs Last Month
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(dimens.Radius.sm))
                                        .background(Color(0xFF1E2124))
                                        .padding(dimens.Padding.sm),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = stringResource(Res.string.earnings_vs_last_month),
                                            color = appColor.secondaryText,
                                            fontSize = dimens.Text._2xs
                                        )
                                        Spacer(modifier = Modifier.height(dimens.Space._2xs))
                                        Text(
                                            text = "+${summary?.vsLastMonthPercent?.toInt()}%",
                                            color = Color(0xFF4CAF50),
                                            fontSize = dimens.Text.base,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                // Vs Last Year
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(dimens.Radius.sm))
                                        .background(Color(0xFF1E2124))
                                        .padding(dimens.Padding.sm),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = stringResource(Res.string.earnings_vs_last_year),
                                            color = appColor.secondaryText,
                                            fontSize = dimens.Text._2xs
                                        )
                                        Spacer(modifier = Modifier.height(dimens.Space._2xs))
                                        Text(
                                            text = "+${summary?.vsLastYearPercent?.toInt()}%",
                                            color = Color(0xFF4CAF50),
                                            fontSize = dimens.Text.base,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                // Best Month
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(dimens.Radius.sm))
                                        .background(Color(0xFF1E2124))
                                        .padding(dimens.Padding.sm),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = stringResource(Res.string.earnings_best_month),
                                            color = appColor.secondaryText,
                                            fontSize = dimens.Text._2xs
                                        )
                                        Spacer(modifier = Modifier.height(dimens.Space._2xs))
                                        Text(
                                            text = summary?.bestMonthName ?: "N/A",
                                            color = Color(0xFF00FFCC),
                                            fontSize = dimens.Text.base,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(dimens.Space.xl)) }
        }
    }
}
