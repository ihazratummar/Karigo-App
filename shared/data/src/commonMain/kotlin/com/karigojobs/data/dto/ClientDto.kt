package com.karigojobs.data.dto

import com.karigojobs.share.model.ClientModel
import com.karigojobs.shared.database.tables.Client


/**
 * @author hazratummar
 * Created on 24/05/26
 */





fun Client.toClientModel() : ClientModel {
    return ClientModel(
        id = id,
        name = name,
        phone = phone,
        email =  email,
        address = address,
        outStandingBalance = outstanding_balance,
        totalPaid = this.total_paid,
        totalRevenue = this.total_revenue,
        totalJob = total_jobs.toInt(),
        cratedAt = created_at
    )
}

fun List<Client>.toJobLabourModelList() : List<ClientModel> {
    return this.map { it.toClientModel()}
}