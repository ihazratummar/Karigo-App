package com.karigojobs.domain.repository

import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 22/05/26
 */

interface MaterialRepository {

    suspend fun insertBulkMaterial(material: List<MaterialsModel>): Result<Unit, MaterialError>

    suspend fun insertMaterial(material: MaterialsModel): Result<Unit, MaterialError>

    fun getAllMaterials(): Flow<Result<List<MaterialsModel>, MaterialError>>
    fun searchMaterials(
        query: String,
        tradeTypes: Set<TradeType>? = null,
        categoryId: String ? = null
    ): Flow<Result<List<MaterialsModel>, MaterialError>>

    suspend fun getMaterialById(id: String): Result<MaterialsModel?, MaterialError>

    suspend fun deleteMaterial(id: String): Result<Unit, MaterialError>

    suspend fun updateMaterial(material: MaterialsModel): Result<Unit, MaterialError>

}