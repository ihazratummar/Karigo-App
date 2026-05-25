package com.karigojobs.share.model

data class StarterMaterial(
    val name: String,
    val unit: String,
    val price: Double,
    val tradeType: TradeType,
    val id: String = ""
)
