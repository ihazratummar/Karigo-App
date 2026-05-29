package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobLabourItemModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 29/05/26
 */

class GetJobLabourItemUseCase(
    private val jobRepository: JobRepository
) {

    operator fun invoke(jobId: String) : Flow<Result<List<JobLabourItemModel>, JobError>> {
        return jobRepository.getLabourItems(jobId = jobId)
    }

}