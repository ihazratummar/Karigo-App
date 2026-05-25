package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.karigojobs.data.dto.toActiveModelList
import com.karigojobs.data.dto.toIdModel
import com.karigojobs.data.dto.toModel
import com.karigojobs.data.dto.toModelList
import com.karigojobs.data.dto.toModelListJobMaterial
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.result.JobError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.GetAllJobs
import com.karigojobs.shared.database.Job
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlin.collections.List


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class JobRepositoryImpl(
    private val karigojobsDatabase: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : JobRepository {

    override fun getAllJobs(): Flow<Result<List<JobModel>, JobError>> {
        return karigojobsDatabase.jobQueries
            .getAllJobs()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { jobs ->
                Result.Success(jobs.toModelList()) as Result<List<JobModel>, JobError>
            }.catch {
                emit(Result.Error(JobError.NotFound))
            }
    }


    override fun getActiveJobs(): Flow<List<JobModel>> =
        karigojobsDatabase.jobQueries.getActiveJobs()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { it.toActiveModelList() }

    override suspend fun getJobById(id: String): JobModel {
        val job = karigojobsDatabase.jobQueries.getJobById(id = id)
            .executeAsOne().toIdModel()
        return job
    }

    override suspend fun saveJobTransaction(
        job: JobModel, jobLabourItemModel: List<JobLabourItemModel>,
        jobMaterialItemModel: List<JobMaterialItemModel>
    ): Result<Unit, JobError> {
        return safeCall(JobError.SaveFailed) {
            karigojobsDatabase.transaction {
                karigojobsDatabase.jobQueries.insertJob(
                    id = job.id,
                    client_id = job.clientId,
                    title = job.title,
                    decription = job.description,
                    status = job.status.name,
                    trade_type = job.tradeType.name,
                    material_total = job.materialTotal,
                    total = job.total,
                    notes = job.notes,
                    job_date = job.jobDate,
                    created_at = EpochUtils.now(),
                    updated_at = EpochUtils.now(),
                )
                jobLabourItemModel.forEach { itemModel ->
                    karigojobsDatabase.jobLabourItemQueries.insertLabourItemsByJob(
                        id = itemModel.id,
                        job_id = itemModel.jobId,
                        description = itemModel.itemName,
                        quantity = itemModel.quantity,
                        rate = itemModel.rate,
                        total = itemModel.total,
                        created_at = EpochUtils.now(),
                    )
                }

                jobMaterialItemModel.forEach { material ->
                    karigojobsDatabase.jobMaterialQueries.insertJobMaterial(
                        id = material.id,
                        job_id = material.jobId,
                        material_id = material.materialId,
                        name = material.name,
                        unit = material.unit,
                        unit_price = material.unitPrice,
                        quantity = material.quantity.toLong(),
                        total = material.total,
                        created_at = EpochUtils.now()
                    )
                }
            }
        }
    }

    override suspend fun updatedJobStatus(
        id: String,
        status: JobStatus
    ): Result<Unit, JobError> {
        return safeCall(JobError.UpdateFailed) {
            karigojobsDatabase.jobQueries.updateJobStatus(id = id, status = status.name)
        }
    }

    override suspend fun deleteJob(id: String): Result<Unit, JobError> {
        return safeCall(JobError.DeleteFailed) {
            karigojobsDatabase.jobQueries.deleteJob(id = id)
        }
    }

    override fun getLabourItems(jobId: String): Flow<Result<List<JobLabourItemModel>, JobError>> {
        return karigojobsDatabase.jobLabourItemQueries
            .getLabourItemsByJob(job_id = jobId)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { items ->
                Result.Success(items.toModelList())  as Result<List<JobLabourItemModel>, JobError>
            }.catch {
                emit(Result.Error(JobError.Database))
            }
    }


    override suspend fun addLabourItem(item: JobLabourItemModel): Result<Unit, JobError> {
        return safeCall(JobError.UpdateFailed) {
            karigojobsDatabase.jobLabourItemQueries.insertLabourItemsByJob(
                id = UuidGenerator.generate(),
                job_id = item.jobId,
                description = item.itemName,
                quantity = item.quantity,
                rate = item.rate,
                total = item.total,
                created_at = EpochUtils.now(),
            )
        }
    }

    override suspend fun updateLabourQuantity(
        itemId: String,
        quantity: Int
    ): Result<Unit, JobError> {
        return safeCall(JobError.UpdateFailed) {
            karigojobsDatabase.jobLabourItemQueries.updateLabourQuantity(
                id = itemId,
                quantity = quantity.toLong()
            )
        }
    }

    override suspend fun removeLabourItem(itemId: String): Result<Unit, JobError> {
        return safeCall(JobError.DeleteFailed) {
            karigojobsDatabase.jobLabourItemQueries.deleteLabourItem(id = itemId)
        }
    }

    override fun getMaterials(jobId: String):Flow<Result<List<JobMaterialItemModel>, JobError>> {
        return karigojobsDatabase.jobMaterialQueries
            .getJobMaterialsByJob(job_id = jobId)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { itemModels ->
                Result.Success(itemModels.toModelListJobMaterial()) as Result<List<JobMaterialItemModel>, JobError>
            }.catch {
                emit(Result.Error(JobError.Database))
            }

    }

    override suspend fun addMaterial(item: JobMaterialItemModel) : Result<Unit, JobError> {
        return safeCall(JobError.Database){
            karigojobsDatabase.jobMaterialQueries.insertJobMaterial(
                id = item.id,
                job_id = item.jobId,
                material_id = item.materialId,
                name = item.name,
                unit = item.unit,
                unit_price = item.unitPrice,
                quantity = item.quantity.toLong(),
                total = item.total,
                created_at = EpochUtils.now()
            )
        }
    }

    override suspend fun removeMaterial(itemId: String) : Result<Unit, JobError> {
        return safeCall(JobError.Database){
            karigojobsDatabase.jobMaterialQueries.deleteJobMaterial(id = itemId)
        }
    }
}