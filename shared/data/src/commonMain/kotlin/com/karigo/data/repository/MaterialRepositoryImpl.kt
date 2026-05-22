package com.karigo.data.repository

import com.karigo.data.dto.toDomain
import com.karigo.domain.repository.MaterialRepository
import com.karigo.share.model.StarterMaterial
import com.karigo.share.model.TradeType
import com.karigo.shared.database.KarigoDatabase
import kotlin.time.Clock


/**
 * @author hazratummar
 * Created on 22/05/26
 */

class MaterialRepositoryImpl(
    private val database: KarigoDatabase
) : MaterialRepository {

    override suspend fun insertMaterial(material: List<StarterMaterial>) {
        material.forEach { material ->
            database.karigoDatabaseQueries.insertMaterial(
                name = material.name,
                unit = material.unit,
                price = material.price,
                trade_type = material.tradeType.name,
                created_at = Clock.System.now()
            )
        }
    }

    override suspend fun getAllMaterials(): List<StarterMaterial> {
        return database.karigoDatabaseQueries.selectAllMaterials()
            .executeAsList()
            .map { it.toDomain() }
    }

    override suspend fun getMaterialById(id: Long): StarterMaterial {
        return database.karigoDatabaseQueries.selectMaterialById(id = id)
            .executeAsOne()
            .toDomain()
    }


    override suspend fun deleteMaterial(id: Long) {

        database.karigoDatabaseQueries.deleteMaterialById(id = id)
    }

    override suspend fun updateMaterial(material: StarterMaterial) {
        database.karigoDatabaseQueries.updateMaterial(
            name = material.name,
            unit = material.unit,
            price = material.price,
            trade_type = material.tradeType.name,
            id = material.id
        )
    }
}