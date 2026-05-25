package com.karigojobs.domain.usecase

import com.karigojobs.domain.repository.JobRepository
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
    ) {
        jobRepository.insertJob(job = job)

        jobLabourItemModel.forEach { labour ->
            jobRepository.addLabourItem(labour)
        }

        jobMaterialItemModel.forEach { material ->
            jobRepository.addMaterial(material)
        }
    }

}