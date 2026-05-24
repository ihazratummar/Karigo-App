package com.karigo.data.dto

import com.karigo.share.model.ClientModel
import com.karigo.shared.database.Client


/**
 * @author hazratummar
 * Created on 24/05/26
 */





fun Client.toModel() : ClientModel {
    return ClientModel(
        id = id,
        name = name,
        phone = phone,
        email =  email,
        address = address,
        outStandingBalance = outstanding_balance,
        totalJob = total_jobs.toInt(),
        cratedAt = created_at
    )
}

fun List<Client>.toModelList() : List<ClientModel> {
    return this.map { it.toModel()}
}