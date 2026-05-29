package com.karigojobs.domain.usecase.job

import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class SaveFullJobTransactionUseCase(
    private val jobRepository: JobRepository
) {

    suspend operator fun invoke(
        job: JobModel,
        jobLabourItemModel: List<JobLabourItemModel>,
        jobMaterialItemModel: List<JobMaterialItemModel>
    ) : Result<Unit, JobError> {
        return jobRepository.saveJobTransaction(
            job = job,
            jobLabourItemModel = jobLabourItemModel,
            jobMaterialItemModel = jobMaterialItemModel
        )
    }

}