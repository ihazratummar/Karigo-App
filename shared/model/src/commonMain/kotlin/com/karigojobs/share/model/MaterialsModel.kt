package com.karigojobs.share.model

data class MaterialsModel(
    val id: String = "",
    val name: String,
    val unit: String,
    val price: Double,
    val tradeType: TradeType,
    val categoryId: String? = null,
    val categoryName : String ? = null
)
