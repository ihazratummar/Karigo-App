package com.karigojobs.domain.repository

import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.StarterMaterial
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 22/05/26
 */

interface MaterialRepository {

    suspend fun insertMaterial(material: List<StarterMaterial>) : Result<Unit, MaterialError>

    suspend fun getAllMaterials(): Flow<Result<List<StarterMaterial>, MaterialError>>

    suspend fun getMaterialById(id: String): Result<StarterMaterial?, MaterialError>

    suspend fun deleteMaterial(id: String): Result<Unit, MaterialError>

    suspend fun updateMaterial(material: StarterMaterial): Result<Unit, MaterialError>

}