package com.karigo.data.repository

import com.karigo.data.dto.toModelList
import com.karigo.data.dto.toActiveModelList
import com.karigo.data.dto.toIdModel
import com.karigo.data.dto.toModelListJobMaterial
import com.karigo.domain.repository.JobRepository
import com.karigo.share.model.JobModel
import com.karigo.share.model.JobLabourItemModel
import com.karigo.share.model.JobMaterialItemModel
import com.karigo.share.model.JobStatus
import com.karigo.shared.database.EpochUtils
import com.karigo.shared.database.KarigoDatabase
import com.karigo.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class JobRepositoryImpl(
    private val karigoDatabase: KarigoDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : JobRepository {

    override fun getAllJobs(): Flow<List<JobModel>> = flow {
        emit(
            karigoDatabase.jobQueries
                .getAllJobs()
                .executeAsList()
                .toModelList()
        )
    }.flowOn(Dispatchers.IO)

    override fun getActiveJobs(): Flow<List<JobModel>> = flow {
        val job = karigoDatabase.jobQueries.getActiveJobs()
            .executeAsList()
            .toActiveModelList()
        emit(job)
    }.flowOn(Dispatchers.IO)

    override suspend fun getJobById(id: String): JobModel {
        val job = karigoDatabase.jobQueries.getJobById(id = id)
            .executeAsOne().toIdModel()
        return job
    }

    override suspend fun insertJob(job: JobModel) {
        karigoDatabase.transaction {  }


        karigoDatabase.jobQueries.insertJob(
            id = job.id,
            client_id = job.clientId,
            title = job.title,
            decription = job.description,
            status = job.status.name,
            material_total = job.materialTotal,
            total = job.total,
            notes = job.notes,
            job_date = job.jobDate,
            created_at = EpochUtils.now(),
            updated_at = EpochUtils.now(),
        )
    }

    override suspend fun updatedJobStatus(
        id: String,
        status: JobStatus
    ) {
        karigoDatabase.jobQueries.updateJobStatus(id = id, status = status.name)
    }

    override suspend fun deleteJob(id: String) {
        karigoDatabase.jobQueries.deleteJob(id = id)
    }

    override fun getLabourItems(jobId: String): Flow<List<JobLabourItemModel>> = flow {

        val jobLabours = karigoDatabase.jobLabourItemQueries
            .getLabourItemsByJob(job_id = jobId)
            .executeAsList()
            .map {
                JobLabourItemModel(
                    id = it.id,
                    jobId = it.job_id,
                    itemName = it.description,
                    quantity = it.quantity,
                    rate = it.rate,
                    unit = it.unit,
                    total = it.total
                )
            }
        emit(jobLabours)
    }.flowOn(Dispatchers.IO)

    override suspend fun addLabourItem(item: JobLabourItemModel) {
        karigoDatabase.jobLabourItemQueries.insertLabourItemsByJob(
            id = UuidGenerator.generate(),
            job_id = item.jobId,
            description = item.itemName,
            quantity = item.quantity,
            rate = item.rate,
            total = item.total,
            created_at = EpochUtils.now(),
        )
    }

    override suspend fun updateLabourQuantity(itemId: String, quantity: Int) {
        karigoDatabase.jobLabourItemQueries.updateLabourQuantity(id = itemId, quantity = quantity.toLong())
    }

    override suspend fun removeLabourItem(itemId: String) {
        karigoDatabase.jobLabourItemQueries.deleteLabourItem(id = itemId)
    }

    override fun getMaterials(jobId: String): Flow<List<JobMaterialItemModel>> = flow{
        val materials = karigoDatabase.jobMaterialQueries.getJobMaterialsByJob(job_id = jobId)
            .executeAsList()
            .toModelListJobMaterial()
        emit(materials)
    }.flowOn(Dispatchers.IO)

    override suspend fun addMaterial(item: JobMaterialItemModel) {
        karigoDatabase.jobMaterialQueries.insertJobMaterial(
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

    override suspend fun removeMaterial(itemId: String) {
        karigoDatabase.jobMaterialQueries.deleteJobMaterial(id = itemId)
    }
}