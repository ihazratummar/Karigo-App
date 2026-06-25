package com.karigojobs.data.dto

import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.tables.MaterialCategory


/**
 * @author hazratummar
 * Created on 25/06/26
 */
 

fun MaterialCategory.toCategoryModel() : MaterialCategoryModel {
    return MaterialCategoryModel(
        id = id,
        name = name,
        tradeType = TradeType.valueOf(trade_type)
    )
}

fun List<MaterialCategory>.toCategoryModelList() : List<MaterialCategoryModel> {
    return this.map { it.toCategoryModel() }
}