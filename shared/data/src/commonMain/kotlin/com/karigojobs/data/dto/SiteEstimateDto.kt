package com.karigojobs.data.dto

import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.Estimate_materials
import com.karigojobs.shared.database.GetAllEstimate
import com.karigojobs.shared.database.GetEstimateById


/**
 * @author hazratummar
 * Created on 05/06/26
 */


fun GetAllEstimate.toEstimateModel(): SiteEstimateModel {
    return SiteEstimateModel(
        id = id,
        projectTitle = project_title,
        clientId = client_id,
        siteNote = site_notes,
        clientName = client_name,
        date = date,
        showRate = show_rate,
        total = total,
        createAt = create_at
    )
}

fun List<GetAllEstimate>.toEstimateListModel(): List<SiteEstimateModel> {
    return this.map { it.toEstimateModel() }
}


fun GetEstimateById.toOneEstimateModel() : SiteEstimateModel {
    return SiteEstimateModel(
        id = id,
        projectTitle = project_title,
        siteNote = site_notes,
        clientId = client_id,
        clientName = client_name,
        date = date,
        showRate = show_rate,
        total = total,
        createAt = create_at
    )
}


fun Estimate_materials.toEstimateMaterialModel() : SiteEstimateMaterial {
    return SiteEstimateMaterial(
        id = this.id,
        estimateId = this.estimate_id,
        materialId = this.material_id,
        materialName = this.material_name,
        quantity = this.quantity,
        unit = this.unit,
        rate = this.rate ?:0.0,
        quantityInput = this.quantity.toString()
    )
}

fun List<Estimate_materials>.toEstimateMaterialModelList() : List<SiteEstimateMaterial> {
    return this.map { it.toEstimateMaterialModel() }
}