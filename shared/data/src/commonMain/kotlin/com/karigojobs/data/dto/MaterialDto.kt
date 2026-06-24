package com.karigojobs.data.dto

import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.tables.Materials


fun Materials.toMaterialDomain() : MaterialsModel {
    return MaterialsModel(
        id = this.id,
        name = this.name,
        unit = this.unit,
        price = this.rate,
        tradeType = TradeType.valueOf(this.trade_type)
    )
}

fun List<Materials>.toMaterialDomainList() : List<MaterialsModel> {
    return this.map { it.toMaterialDomain() }
}
