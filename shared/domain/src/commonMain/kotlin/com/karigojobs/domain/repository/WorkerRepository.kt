package com.karigojobs.domain.repository

import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.WorkerProfileError
import com.karigojobs.share.model.WorkerProfileModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 24/06/26
 */

interface WorkerRepository {

    fun getWorkerProfile() : Flow<Result<WorkerProfileModel?, WorkerProfileError>>

    suspend fun insertOrUpdateWorkerProfile(workerProfileModel: WorkerProfileModel) : Result<Unit, WorkerProfileError>
    suspend fun updateBusinessName(newName : String) : Result<Unit, WorkerProfileError>
    suspend fun updateLogoPath(logoPath : String) : Result<Unit, WorkerProfileError>

}