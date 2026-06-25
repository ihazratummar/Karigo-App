package com.karigojobs.data.dto

import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.shared.database.tables.Worker_profile


/**
 * @author hazratummar
 * Created on 24/06/26
 */
 


fun Worker_profile.toWorkerModel() : WorkerProfileModel {
    return WorkerProfileModel(
        id = id,
        businessName =business_name,
        ownerName = owner_name,
        address = address,
        phone = phone,
        gstNumber = gst_number,
        logoPath = logo_path
    )
}