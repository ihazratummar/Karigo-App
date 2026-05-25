package com.karigojobs.share.model

data class ClientModel(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
    val outStandingBalance: Double = 0.0,
    val totalJob: Int =0 ,
    val cratedAt: Long = 0L
)

