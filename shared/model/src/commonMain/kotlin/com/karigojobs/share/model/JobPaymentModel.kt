package com.karigojobs.share.model

data class JobPaymentModel(
    val id: String,
    val jobId: String,
    val amount: Double,
    val paymentMethod: String,
    val paymentDate: Long,
    val note: String,
    val createdAt: Long
)
