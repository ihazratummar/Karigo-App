package com.karigojobs.domain.repository

import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.JobPaymentModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 23/05/26
 */

interface JobRepository {

    // Core Job
    fun getAllJobs(): Flow<Result<List<JobModel>, JobError>>
    fun searchJobs(query: String): Flow<Result<List<JobModel>, JobError>>
    fun getActiveJobs(): Flow<List<JobModel>>
    fun getJobById(id: String):  Flow<Result<JobModel?, JobError>>
    suspend fun saveJobTransaction(
        job: JobModel, jobLabourItemModel: List<JobLabourItemModel>,
        jobMaterialItemModel: List<JobMaterialItemModel>,
        jobLabourLogs: List<com.karigojobs.share.model.JobLabourLogModel> = emptyList()
    ) : Result<Unit, JobError>

    suspend fun updatedJobStatus(id: String, status: JobStatus) : Result<Unit, JobError>
    suspend fun deleteJob(id: String): Result<Unit, JobError>

    // Line Items
    fun getLabourItems(jobId: String): Flow<Result<List<JobLabourItemModel>, JobError>>
    suspend fun addLabourItem(item: JobLabourItemModel) : Result<Unit, JobError>
    suspend fun updateLabourQuantity(itemId: String, quantity: Int) : Result<Unit, JobError>
    suspend fun removeLabourItem(itemId: String) : Result<Unit, JobError>
    fun getLabourLogs(labourItemId: String): Flow<Result<List<com.karigojobs.share.model.JobLabourLogModel>, JobError>>

    fun getMaterials(jobId: String): Flow<Result<List<JobMaterialItemModel>, JobError>>
    suspend fun addMaterial(item: JobMaterialItemModel) : Result<Unit, JobError>
    suspend fun removeMaterial(itemId: String) : Result<Unit, JobError>

    fun getJobByClient(clientId: String) : Flow<Result<List<JobModel>, JobError>>

    // Payments
    fun getPaymentsForJob(jobId: String): Flow<Result<List<JobPaymentModel>, JobError>>
    suspend fun addPayment(payment: JobPaymentModel): Result<Unit, JobError>
    suspend fun deletePayment(paymentId: String): Result<Unit, JobError>
}