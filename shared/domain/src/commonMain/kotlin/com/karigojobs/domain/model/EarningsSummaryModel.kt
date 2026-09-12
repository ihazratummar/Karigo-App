package com.karigojobs.domain.model

import com.karigojobs.share.model.ClientModel

data class MonthlyBarData(
    val monthLabel: String,
    val revenue: Double,
    val isCurrentMonth: Boolean = false
)

data class TradeRevenueData(
    val tradeType: String,
    val jobCount: Int,
    val revenue: Double,
    val percentage: Float = 0f
)

data class EarningsSummaryModel(
    val totalRevenue: Double = 0.0,
    val totalJobs: Int = 0,
    val avgPerJob: Double = 0.0,
    val bestMonthName: String = "N/A",
    val bestMonthRevenue: Double = 0.0,
    val estProfitMargin: Double = 0.0,
    val profitMarginPercent: Int = 35,
    val estExpenses: Double = 0.0,
    val expenseMarginPercent: Int = 65,
    val avgMonthlyRevenue: Double = 0.0,
    val busiestMonthName: String = "N/A",
    val vsLastMonthPercent: Double = 0.0,
    val vsLastYearPercent: Double = 0.0,
    val monthlyEarnings: List<MonthlyBarData> = emptyList(),
    val topClients: List<ClientModel> = emptyList(),
    val tradeRevenues: List<TradeRevenueData> = emptyList()
)
