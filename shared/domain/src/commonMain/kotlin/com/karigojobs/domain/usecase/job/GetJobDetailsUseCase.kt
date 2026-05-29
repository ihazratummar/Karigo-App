package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobModel
import kotlinx.coroutines.flow.Flow

/**
 * @author hazratummar
 * Created on 26/05/26
 */

class GetJobDetailsUseCase(
    private val jobRepository: JobRepository
) {

    operator fun invoke(id: String): Flow<Result<JobModel?, JobError>> {
        return jobRepository.getJobById(id)
    }

}