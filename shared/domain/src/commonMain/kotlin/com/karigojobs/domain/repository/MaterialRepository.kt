package com.karigojobs.domain.repository

import com.karigojobs.share.model.StarterMaterial


/**
 * @author hazratummar
 * Created on 22/05/26
 */

interface MaterialRepository {

    suspend fun insertMaterial(material: List<StarterMaterial>)

    suspend fun getAllMaterials(): List<StarterMaterial>

    suspend fun getMaterialById(id: String): StarterMaterial?

    suspend fun deleteMaterial(id: String)

    suspend fun updateMaterial(material: StarterMaterial)

}