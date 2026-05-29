package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobStatus


/**
 * @author hazratummar
 * Created on 26/05/26
 */

class ChangeJobStatusUseCase(
    private val jobRepository: JobRepository
) {

    suspend operator fun invoke(jobId: String, jobStatus: JobStatus) : Result<Unit, JobError> {
        return jobRepository.updatedJobStatus(id = jobId, status = jobStatus)
    }

}