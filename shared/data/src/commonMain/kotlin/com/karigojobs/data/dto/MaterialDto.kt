package com.karigojobs.data.dto

import com.karigojobs.share.model.StarterMaterial
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.Materials


fun Materials.toDomain() : StarterMaterial {
    return StarterMaterial(
        name = this.name,
        unit = this.unit,
        price = this.rate,
        tradeType = TradeType.valueOf(this.trade_type)
    )
}

fun List<Materials>.toDomainList() : List<StarterMaterial> {
    return this.map { it.toDomain() }
}
