package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 25/05/26
 */

class SearchJobUseCase (
    private val jobRepository: JobRepository
) {

    operator fun invoke(query: String) : Flow<Result<List<JobModel>, JobError>> {
        return jobRepository.searchJobs(query = query)
    }

}