package com.karigo.data.dto

import com.karigo.share.model.StarterMaterial
import com.karigo.share.model.TradeType
import com.karigo.shared.database.Materials


fun Materials.toDomain() : StarterMaterial {
    return StarterMaterial(
        name = this.name,
        unit = this.unit,
        price = this.price,
        tradeType = TradeType.valueOf(this.trade_type)
    )
}

