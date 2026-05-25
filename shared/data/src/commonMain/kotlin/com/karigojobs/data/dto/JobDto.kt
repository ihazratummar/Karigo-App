package com.karigojobs.data.dto

import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.shared.database.GetActiveJobs
import com.karigojobs.shared.database.GetAllJobs
import com.karigojobs.shared.database.GetJobById
import com.karigojobs.shared.database.Job_labour_item
import com.karigojobs.shared.database.Job_material


/**
 * @author hazratummar
 * Created on 23/05/26
 */



fun GetAllJobs.toModel(): JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}

fun List<GetAllJobs>.toModelList() : List<JobModel> {
    return this.map { it.toModel()}
}

fun GetActiveJobs.toActiveModel() : JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}

fun List<GetActiveJobs>.toActiveModelList() : List<JobModel> {
    return this.map { it.toActiveModel()}
}


fun GetJobById.toIdModel() : JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}


fun Job_labour_item.toModel() : JobLabourItemModel {
    return JobLabourItemModel(
        id = this.id,
        jobId = this.job_id,
        itemName = this.description,
        quantity = this.quantity,
        rate = this.rate,
        total = this.total,
        unit = this.unit
    )
}

fun Job_material.toModel() : JobMaterialItemModel {
    return JobMaterialItemModel(
        id = id,
        jobId = job_id,
        materialId = material_id,
        name = name,
        unit = unit,
        unitPrice = unit_price,
        quantity = quantity.toInt(),
        total = total,
    )
}
fun List<Job_material>.toModelListJobMaterial() : List<JobMaterialItemModel>{
    return this.map { it.toModel() }
}