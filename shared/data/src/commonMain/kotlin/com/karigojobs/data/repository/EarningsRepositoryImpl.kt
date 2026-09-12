package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.karigojobs.domain.model.EarningsSummaryModel
import com.karigojobs.domain.model.MonthlyBarData
import com.karigojobs.domain.model.TradeRevenueData
import com.karigojobs.domain.repository.EarningsRepository
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.EarningsTimeframe
import com.karigojobs.shared.database.KarigojobsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class EarningsRepositoryImpl(
    private val database: KarigojobsDatabase
) : EarningsRepository {

    override fun getEarningsSummary(timeframe: EarningsTimeframe): Flow<EarningsSummaryModel> {
        val summaryFlow = database.monthlyEarningQueries.getEarningSummary()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)

        val growthFlow = database.monthlyEarningQueries.getGrowthSummary()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)

        val topClientsFlow = database.monthlyEarningQueries.getTopClients(limit = 5)
            .asFlow()
            .mapToList(Dispatchers.IO)

        val monthlyEarningsFlow = database.monthlyEarningQueries.getMonthlyEarnings()
            .asFlow()
            .mapToList(Dispatchers.IO)

        val tradeRevenuesFlow = database.monthlyEarningQueries.getRevenueByTrade()
            .asFlow()
            .mapToList(Dispatchers.IO)

        return combine(
            summaryFlow,
            growthFlow,
            topClientsFlow,
            monthlyEarningsFlow,
            tradeRevenuesFlow
        ) { summary, growth, topClients, monthlyEarnings, tradeRevenues ->
            val monthOrder = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

            val monthRevenueMap = monthlyEarnings.associate { row ->
                val parts = row.month.split("-")
                val monthName = if (parts.size == 2) {
                    val mIdx = parts[1].toIntOrNull()?.minus(1) ?: 0
                    monthOrder.getOrElse(mIdx) { "" }
                } else ""
                monthName to row.revenue
            }

            val currentMonthIdx = com.karigojobs.shared.database.EpochUtils.getCurrentMonthIndex()
            val currentMonthName = monthOrder.getOrElse(currentMonthIdx) { "" }

            val monthsCount = when (timeframe) {
                EarningsTimeframe.SIX_MONTHS -> 6
                EarningsTimeframe.ONE_YEAR -> 12
            }

            val trailingMonths = (monthsCount - 1 downTo 0).map { offset ->
                val idx = (currentMonthIdx - offset + 12) % 12
                monthOrder[idx]
            }

            val barDataList = trailingMonths.map { monthName ->
                val rev = monthRevenueMap[monthName] ?: 0.0
                MonthlyBarData(
                    monthLabel = monthName,
                    revenue = rev,
                    isCurrentMonth = (monthName == currentMonthName)
                )
            }

            val mappedTopClients = topClients.map { row ->
                ClientModel(
                    id = row.client_id,
                    name = row.client_name,
                    phone = "",
                    email = "",
                    address = "",
                    outStandingBalance = 0.0,
                    totalRevenue = row.revenue,
                    totalPaid = row.revenue,
                    pendingAmount = 0.0,
                    totalJob = row.job_count.toInt(),
                    cratedAt = 0L
                )
            }

            val bestMonthFormatted = summary?.best_month?.let { mStr ->
                if (mStr.isNotBlank()) {
                    val parts = mStr.split("-")
                    if (parts.size == 2) {
                        val mIdx = parts[1].toIntOrNull()?.minus(1) ?: 0
                        monthOrder.getOrElse(mIdx) { "N/A" }
                    } else mStr
                } else "N/A"
            } ?: "N/A"

            val busiestMonthFormatted = growth?.busiest_month?.let { mStr ->
                if (mStr.isNotBlank()) {
                    val parts = mStr.split("-")
                    if (parts.size == 2) {
                        val mIdx = parts[1].toIntOrNull()?.minus(1) ?: 0
                        monthOrder.getOrElse(mIdx) { "N/A" }
                    } else mStr
                } else "N/A"
            } ?: "N/A"

            val totalRevenue = summary?.revenue ?: 0.0
            val totalJobs = summary?.total_jobs?.toInt() ?: 0
            val avgPerJob = summary?.avg_per_job ?: 0.0

            val estProfitMargin = totalRevenue * 0.35
            val profitMarginPercent = if (totalRevenue > 0) 35 else 0
            val estExpenses = if (totalRevenue > 0) totalRevenue - estProfitMargin else 0.0
            val expenseMarginPercent = if (totalRevenue > 0) 65 else 0

            val bestMonthRevenue = summary?.best_month_revenue ?: 0.0
            val avgMonthly = growth?.avg_monthly ?: 0.0

            val maxTradeRev = tradeRevenues.maxOfOrNull { it.revenue }?.coerceAtLeast(1.0) ?: 1.0
            val mappedTradeRevenues = tradeRevenues.map { row ->
                TradeRevenueData(
                    tradeType = row.trade_type,
                    jobCount = row.job_count.toInt(),
                    revenue = row.revenue,
                    percentage = if (row.revenue > 0) (row.revenue / maxTradeRev).toFloat() else 0f
                )
            }

            EarningsSummaryModel(
                totalRevenue = totalRevenue,
                totalJobs = totalJobs,
                avgPerJob = avgPerJob,
                bestMonthName = bestMonthFormatted,
                bestMonthRevenue = bestMonthRevenue,
                estProfitMargin = estProfitMargin,
                profitMarginPercent = profitMarginPercent,
                estExpenses = estExpenses,
                expenseMarginPercent = expenseMarginPercent,
                avgMonthlyRevenue = avgMonthly,
                busiestMonthName = busiestMonthFormatted,
                vsLastMonthPercent = growth?.growth_percentage ?: 0.0,
                vsLastYearPercent = 0.0,
                monthlyEarnings = barDataList,
                topClients = mappedTopClients,
                tradeRevenues = mappedTradeRevenues
            )
        }
    }
}
