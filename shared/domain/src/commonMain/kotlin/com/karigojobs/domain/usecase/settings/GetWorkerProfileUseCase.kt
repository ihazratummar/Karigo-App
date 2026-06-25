package com.karigojobs.domain.usecase.settings

import com.karigojobs.domain.repository.WorkerRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.WorkerProfileError
import com.karigojobs.share.model.WorkerProfileModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class GetWorkerProfileUseCase(
    private val workerRepository: WorkerRepository
) {

    operator fun invoke() : Flow<Result<WorkerProfileModel?, WorkerProfileError>> {
        return workerRepository.getWorkerProfile()
    }

}