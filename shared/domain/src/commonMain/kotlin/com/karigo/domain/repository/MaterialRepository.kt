package com.karigo.domain.repository

import com.karigo.share.model.StarterMaterial


/**
 * @author hazratummar
 * Created on 22/05/26
 */

interface MaterialRepository {

    suspend fun insertMaterial(material: List<StarterMaterial>)

    suspend fun getAllMaterials(): List<StarterMaterial>

    suspend fun getMaterialById(id: Long): StarterMaterial?

    suspend fun deleteMaterial(id: Long)

    suspend fun updateMaterial(material: StarterMaterial)

}