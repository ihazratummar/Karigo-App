package com.karigo.domain.usecase

import com.karigo.domain.repository.JobRepository
import com.karigo.share.model.JobLabourItemModel
import com.karigo.share.model.JobMaterialItemModel
import com.karigo.share.model.JobModel


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