package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 23/06/26
 */

class GetJobByClientUseCase(
    private val jobRepository: JobRepository
) {

    operator fun invoke(clientId: String) : Flow<Result<List<JobModel>, JobError>>{
        return jobRepository.getJobByClient(clientId = clientId)
    }

}