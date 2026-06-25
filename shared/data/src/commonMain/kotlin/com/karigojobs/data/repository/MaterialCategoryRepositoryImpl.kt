package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.karigojobs.data.dto.toCategoryModelList
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.MaterialCategoryRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 25/06/26
 */

class MaterialCategoryRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : MaterialCategoryRepository {

    override suspend fun insertCategory(category: MaterialCategoryModel): Result<Unit, MaterialError> {
        return safeCall(MaterialError.DatabaseError) {
            database.materialCategoryQueries
                .insertCategory(
                    id = category.id,
                    name = category.name,
                    trade_type = category.tradeType.name,
                    createdAt = EpochUtils.now(),
                    updatedAt = EpochUtils.now()
                )
        }
    }

    override suspend fun updateCategory(category: MaterialCategoryModel): Result<Unit, MaterialError> {
        return safeCall(MaterialError.FailedUpdate) {
            database.materialCategoryQueries
                .updateCategory(
                    id = category.id,
                    name = category.name,
                    updatedAt = EpochUtils.now()
                )
        }
    }

    override suspend fun deleteCategory(id: String): Result<Unit, MaterialError> {
        return safeCall(MaterialError.DatabaseError){
            database.materialCategoryQueries
                .deleteCategory(id = id)
        }
    }

    override fun getCategoryByTrade(tradeType: TradeType?): Flow<Result<List<MaterialCategoryModel>, MaterialError>> {
        return database.materialCategoryQueries
            .getCategoryByTrade(tradeType = tradeType?.name)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { categories ->
                Result.Success(categories.toCategoryModelList())
            }.catch {
                Result.Error(MaterialError.DatabaseError)
            }
    }
}