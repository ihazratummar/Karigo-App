package com.karigojobs.domain.repository

import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 25/06/26
 */

interface MaterialCategoryRepository {

    suspend fun insertCategory(category: MaterialCategoryModel) : Result<Unit, MaterialError>
    suspend fun updateCategory(category: MaterialCategoryModel) : Result<Unit, MaterialError>
    suspend fun deleteCategory(id: String) : Result<Unit, MaterialError>
    fun getCategoryByTrade(tradeType: TradeType?) : Flow<Result<List<MaterialCategoryModel>, MaterialError>>

}