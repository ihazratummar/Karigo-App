package com.karigojobs.data.repository

import com.karigojobs.data.dto.toDomain
import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.share.model.StarterMaterial
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator


/**
 * @author hazratummar
 * Created on 22/05/26
 */

class MaterialRepositoryImpl(
    private val database: KarigojobsDatabase
) : MaterialRepository {

    override suspend fun insertMaterial(material: List<StarterMaterial>) {
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

    override suspend fun getAllMaterials(): List<StarterMaterial> {
        return database.materialQueries.getAllMaterials()
            .executeAsList()
            .map { it.toDomain() }
    }

    override suspend fun getMaterialById(id: String): StarterMaterial {
        return database.materialQueries.selectMaterialById(id = id)
            .executeAsOne()
            .toDomain()
    }


    override suspend fun deleteMaterial(id: String) {

        database.materialQueries.deleteMaterialById(id = id)
    }

    override suspend fun updateMaterial(material: StarterMaterial) {
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