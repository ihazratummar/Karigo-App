package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.karigojobs.data.dto.toDomain
import com.karigojobs.data.dto.toDomainList
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.StarterMaterial
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

    override suspend fun insertMaterial(material: List<StarterMaterial>): Result<Unit, MaterialError> {
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

    override suspend fun getAllMaterials(): Flow<Result<List<StarterMaterial>, MaterialError>> {
        return database.materialQueries
            .getAllMaterials()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { materials ->
                Result.Success(materials.toDomainList())
            }.catch {
                Result.Error(MaterialError.DatabaseError)
            }
    }

    override suspend fun getMaterialById(id: String): Result<StarterMaterial?, MaterialError> {
        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries.selectMaterialById(id = id)
                .executeAsOneOrNull()
                ?.toDomain()
        }
    }


    override suspend fun deleteMaterial(id: String): Result<Unit, MaterialError> {

        return safeCall(MaterialError.DatabaseError) {
            database.materialQueries.deleteMaterialById(id = id)
        }
    }

    override suspend fun updateMaterial(material: StarterMaterial): Result<Unit, MaterialError>  {
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