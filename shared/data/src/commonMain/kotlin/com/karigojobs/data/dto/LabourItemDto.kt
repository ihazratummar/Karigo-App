package com.karigojobs.data.dto

import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.shared.database.Job_labour_item


/**
 * @author hazratummar
 * Created on 25/05/26
 */
 

fun Job_labour_item.toModel() : JobLabourItemModel {
    return JobLabourItemModel(
        id = id,
        jobId = job_id,
        itemName = description,
        quantity = quantity,
        rate = rate,
        total = total,
        unit = unit
    )
}

fun List<Job_labour_item>.toModelList() : List<JobLabourItemModel> {
    return this.map { it.toModel() }
}