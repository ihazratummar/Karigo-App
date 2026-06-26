package com.karigojobs.domain.usecase.settings

import com.karigojobs.domain.repository.WorkerRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.WorkerProfileError
import com.karigojobs.share.model.WorkerProfileModel

/**
 * Usecase to save or update the worker profile.
 */
class SaveWorkerProfileUseCase(
    private val workerRepository: WorkerRepository
) {
    suspend operator fun invoke(workerProfileModel: WorkerProfileModel): Result<Unit, WorkerProfileError> {
        return workerRepository.insertOrUpdateWorkerProfile(workerProfileModel)
    }
}
