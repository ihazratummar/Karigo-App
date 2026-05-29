package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobMaterialItemModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 29/05/26
 */

class GetJobMaterialItemsUseCase(
    private val jobRepository: JobRepository
) {

    operator fun invoke(jobId: String) : Flow<Result<List<JobMaterialItemModel>, JobError>> {
        return jobRepository.getMaterials(jobId = jobId)
    }

}