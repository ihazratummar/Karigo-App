package com.karigojobs.domain.model

import com.karigojobs.share.model.ClientModel

data class MonthlyBarData(
    val monthLabel: String,
    val revenue: Double,
    val isCurrentMonth: Boolean = false
)

data class EarningsSummaryModel(
    val totalRevenue: Double = 0.0,
    val totalJobs: Int = 0,
    val avgPerJob: Double = 0.0,
    val estProfitMargin: Double = 0.0,
    val profitMarginPercent: Int = 0,
    val monthlyEarnings: List<MonthlyBarData> = emptyList(),
    val topClients: List<ClientModel> = emptyList(),
    val vsLastMonthPercent: Double = 0.0,
    val vsLastYearPercent: Double = 0.0,
    val bestMonthName: String = "N/A"
)
