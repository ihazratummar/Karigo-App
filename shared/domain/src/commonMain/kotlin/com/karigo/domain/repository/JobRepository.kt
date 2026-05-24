package com.karigo.domain.repository

import com.karigo.share.model.JobModel
import com.karigo.share.model.JobLabourItemModel
import com.karigo.share.model.JobMaterialItemModel
import com.karigo.share.model.JobStatus
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 23/05/26
 */

interface JobRepository {

    // Core Job
    fun getAllJobs() : Flow<List<JobModel>>
    fun getActiveJobs(): Flow<List<JobModel>>
    suspend fun getJobById(id: String): JobModel?
    suspend fun insertJob(job: JobModel)
    suspend fun updatedJobStatus(id: String, status: JobStatus)
    suspend fun deleteJob(id: String)

    // Line Items
    fun getLabourItems(jobId: String): Flow<List<JobLabourItemModel>>
    suspend fun addLabourItem(item: JobLabourItemModel)
    suspend fun updateLabourQuantity(itemId: String, quantity: Int)
    suspend fun removeLabourItem(itemId: String)

    fun getMaterials(jobId: String) : Flow<List<JobMaterialItemModel>>
    suspend fun addMaterial(item: JobMaterialItemModel)
    suspend fun removeMaterial(itemId: String)
}