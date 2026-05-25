package com.karigojobs.data.repository

import com.karigojobs.data.dto.toModelList
import com.karigojobs.data.dto.toActiveModelList
import com.karigojobs.data.dto.toIdModel
import com.karigojobs.data.dto.toModelListJobMaterial
import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
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
    private val karigojobsDatabase: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : JobRepository {

    override fun getAllJobs(): Flow<List<JobModel>> = flow {
        emit(
            karigojobsDatabase.jobQueries
                .getAllJobs()
                .executeAsList()
                .toModelList()
        )
    }.flowOn(Dispatchers.IO)

    override fun getActiveJobs(): Flow<List<JobModel>> = flow {
        val job = karigojobsDatabase.jobQueries.getActiveJobs()
            .executeAsList()
            .toActiveModelList()
        emit(job)
    }.flowOn(Dispatchers.IO)

    override suspend fun getJobById(id: String): JobModel {
        val job = karigojobsDatabase.jobQueries.getJobById(id = id)
            .executeAsOne().toIdModel()
        return job
    }

    override suspend fun insertJob(job: JobModel) {
        karigojobsDatabase.transaction {  }


        karigojobsDatabase.jobQueries.insertJob(
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
        karigojobsDatabase.jobQueries.updateJobStatus(id = id, status = status.name)
    }

    override suspend fun deleteJob(id: String) {
        karigojobsDatabase.jobQueries.deleteJob(id = id)
    }

    override fun getLabourItems(jobId: String): Flow<List<JobLabourItemModel>> = flow {

        val jobLabours = karigojobsDatabase.jobLabourItemQueries
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

    override suspend fun updateLabourQuantity(itemId: String, quantity: Int) {
        karigojobsDatabase.jobLabourItemQueries.updateLabourQuantity(id = itemId, quantity = quantity.toLong())
    }

    override suspend fun removeLabourItem(itemId: String) {
        karigojobsDatabase.jobLabourItemQueries.deleteLabourItem(id = itemId)
    }

    override fun getMaterials(jobId: String): Flow<List<JobMaterialItemModel>> = flow{
        val materials = karigojobsDatabase.jobMaterialQueries.getJobMaterialsByJob(job_id = jobId)
            .executeAsList()
            .toModelListJobMaterial()
        emit(materials)
    }.flowOn(Dispatchers.IO)

    override suspend fun addMaterial(item: JobMaterialItemModel) {
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

    override suspend fun removeMaterial(itemId: String) {
        karigojobsDatabase.jobMaterialQueries.deleteJobMaterial(id = itemId)
    }
}