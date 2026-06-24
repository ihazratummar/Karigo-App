package com.karigojobs.data.dto

import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.TradeType
import com.karigojobs.shared.database.tables.GetActiveJobs
import com.karigojobs.shared.database.tables.GetAllJobs
import com.karigojobs.shared.database.tables.GetJobById
import com.karigojobs.shared.database.tables.Job
import com.karigojobs.shared.database.tables.Job_material
import com.karigojobs.shared.database.tables.SearchJobs


/**
 * @author hazratummar
 * Created on 23/05/26
 */



fun Job.toJobModel() : JobModel {
    return JobModel(
        id = id,
        clientId = client_id,
        clientName = "",
        title = title,
        description = decription,
        status = JobStatus.valueOf(status),
        tradeType = TradeType.valueOf(trade_type),
        materialTotal = material_total,
        total = total,
        totalItems = total_items.toInt(),
        notes = notes,
        jobDate = job_date,
        createdAt = created_at
    )
}

fun List<Job>.toJobListModel() : List<JobModel> {
    return this.map { it.toJobModel() }
}

fun GetAllJobs.toAllJobModel(): JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        tradeType = TradeType.valueOf( this.trade_type),
        materialTotal = this.material_total,
        totalItems = this.total_items.toInt(),
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}

fun List<GetAllJobs>.toAllJobModelList() : List<JobModel> {
    return this.map { it.toAllJobModel()}
}

fun SearchJobs.toAllJobModel(): JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        tradeType = TradeType.valueOf(this.trade_type),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}

fun List<SearchJobs>.toSearchModelList() : List<JobModel> {
    return this.map { it.toAllJobModel()}
}

fun GetActiveJobs.toAllJobModel() : JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        tradeType = TradeType.valueOf(this.trade_type),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}



fun List<GetActiveJobs>.toActiveModelList() : List<JobModel> {
    return this.map { it.toAllJobModel()}
}


fun GetJobById.toJobByIdModel() : JobModel {
    return JobModel(
        id = this.id,
        clientId = this.client_id,
        clientName = client_name,
        title = this.title,
        description = this.decription,
        status = JobStatus.valueOf(this.status),
        tradeType = TradeType.valueOf(this.trade_type),
        materialTotal = this.material_total,
        total = this.total,
        notes = this.notes,
        jobDate = this.job_date,
        createdAt = this.created_at
    )
}


fun Job_material.toJobMaterialModel() : JobMaterialItemModel {
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
    return this.map { it.toJobMaterialModel() }
}