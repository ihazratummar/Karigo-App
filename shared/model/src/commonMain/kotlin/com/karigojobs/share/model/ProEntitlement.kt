package com.karigojobs.share.model

enum class PlanTier {
    FREE,
    PRO_MONTHLY,
    PRO_YEARLY,
    PRO_LIFETIME
}

enum class ProFeature {
    PDF_EXPORT,
    WHATSAPP_SHARE,
    MULTI_WORKER,
    EARNINGS_DASHBOARD,
    BUSINESS_LOGO,
    INVOICE_DISCOUNT,
    CSV_EXPORT,
    PAYMENT_REMINDERS,
    UNLIMITED_JOBS
}

data class ProStatus(
    val isProActive: Boolean = false,
    val planTier: PlanTier = PlanTier.FREE,
    val purchaseDateEpochMs: Long? = null,
    val expirationDateEpochMs: Long? = null,
    val isTrialActive: Boolean = false
) {
    val hasProAccess: Boolean
        get() = isProActive
}

data class MonthlyJobLimit(
    val maxFreeJobs: Int = 5,
    val usedJobsCount: Int = 0
) {
    val remainingJobsCount: Int
        get() = (maxFreeJobs - usedJobsCount).coerceAtLeast(0)

    val isQuotaExhausted: Boolean
        get() = usedJobsCount >= maxFreeJobs
}

data class PaywallPackage(
    val planTier: PlanTier,
    val productId: String,
    val formattedPrice: String,
    val rawPrice: Double,
    val currencyCode: String,
    val discountPercentBadge: String? = null
)
