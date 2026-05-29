package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result


/**
 * @author hazratummar
 * Created on 26/05/26
 */

class DeleteJobUseCase(
    private val jobRepository: JobRepository
) {

    suspend operator fun invoke(jobId: String): Result<Unit, JobError> {
        return jobRepository.deleteJob(id = jobId)
    }

}