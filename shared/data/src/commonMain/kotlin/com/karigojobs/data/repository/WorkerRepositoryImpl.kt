package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.karigojobs.data.dto.toWorkerModel
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.WorkerRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.WorkerProfileError
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class WorkerRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : WorkerRepository {
    override fun getWorkerProfile(): Flow<Result<WorkerProfileModel?, WorkerProfileError>> {
        return database.workerProfileQueries
            .getProfile()
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { workerProfile ->
                Result.Success(workerProfile?.toWorkerModel())
            }.catch {
                Result.Error(WorkerProfileError.NotFound)
            }
    }

    override suspend fun insertOrUpdateWorkerProfile(workerProfileModel: WorkerProfileModel): Result<Unit, WorkerProfileError> {
        return safeCall(WorkerProfileError.FailedToInsert){
            database.workerProfileQueries
                .updateProfile(
                    business_name = workerProfileModel.businessName,
                    owner_name = workerProfileModel.ownerName,
                    address = workerProfileModel.address,
                    phone = workerProfileModel.phone,
                    gst_number = workerProfileModel.gstNumber,
                    email = workerProfileModel.email,
                    logo_path = workerProfileModel.logoPath,
                    created_at = EpochUtils.now(),
                    updated_at = EpochUtils.now()
                )
        }
    }

    override suspend fun updateBusinessName(newName: String): Result<Unit, WorkerProfileError> {
        return safeCall(WorkerProfileError.FailedToUpdate){
            database.workerProfileQueries
                .updateBusinessName(
                    business_name = newName,
                    updated_at = EpochUtils.now()
                )
        }
    }

    override suspend fun updateLogoPath(logoPath: String ): Result<Unit, WorkerProfileError> {
        return safeCall(WorkerProfileError.FailedToUpdate){
            database.workerProfileQueries
                .updateLogo(
                    logo_path = logoPath,
                    updated_at = EpochUtils.now()
                )
        }
    }
}