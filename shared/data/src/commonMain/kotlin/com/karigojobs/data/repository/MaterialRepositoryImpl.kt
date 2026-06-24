package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.karigojobs.data.dto.toMaterialDomain
import com.karigojobs.data.dto.toMaterialDomainList
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 22/05/26
 */

class MaterialRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : MaterialRepository {

    override suspend fun insertBulkMaterial(material: List<MaterialsModel>): Result<Unit, MaterialError> {
        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries.transaction {
                material.forEach { material ->
                    database.materialQueries.insertMaterial(
                        id = UuidGenerator.generate(),
                        name = material.name,
                        unit = material.unit,
                        rate = material.price,
                        trade_type = material.tradeType.name,
                        created_at = EpochUtils.now(),
                        updated_at = EpochUtils.now()
                    )
                }
            }
        }
    }

    override suspend fun insertMaterial(material: MaterialsModel): Result<Unit, MaterialError> {
        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries
                .insertMaterial(
                    id = UuidGenerator.generate(),
                    name = material.name,
                    unit = material.unit,
                    rate = material.price,
                    trade_type = material.tradeType.name,
                    created_at = EpochUtils.now(),
                    updated_at = EpochUtils.now()
                )
        }
    }

    override  fun getAllMaterials(): Flow<Result<List<MaterialsModel>, MaterialError>> {
        return database.materialQueries
            .getAllMaterials()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { materials ->
                Result.Success(materials.toMaterialDomainList())
            }.catch {
                Result.Error(MaterialError.DatabaseError)
            }
    }

    override fun searchMaterials(
        query: String,
        tradeTypes: Set<TradeType>?
    ): Flow<Result<List<MaterialsModel>, MaterialError>> {
        val types = tradeTypes?.map { it.name } ?: emptyList()
        return database.materialQueries
            .searchMaterials(
                query = query,
                tradeTypes = types,
                tradeTypesCount = types.size.toLong()
            )
            .asFlow()
            .mapToList(ioDispatcher)
            .map { materials ->
                Result.Success(materials.toMaterialDomainList())
            }.catch {
                Result.Error(MaterialError.DatabaseError)
            }
    }

    override suspend fun getMaterialById(id: String): Result<MaterialsModel?, MaterialError> {
        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries.selectMaterialById(id = id)
                .executeAsOneOrNull()
                ?.toMaterialDomain()
        }
    }


    override suspend fun deleteMaterial(id: String): Result<Unit, MaterialError> {

        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries.deleteMaterialById(id = id)
        }
    }

    override suspend fun updateMaterial(material: MaterialsModel): Result<Unit, MaterialError>  {
        return safeCall(MaterialError.DatabaseError){
            database.materialQueries.updateMaterial(
                id = material.id,
                name = material.name,
                unit = material.unit,
                rate = material.price,
                trade_type = material.tradeType.name,
                updated_at = EpochUtils.now(),
            )
        }
    }
}