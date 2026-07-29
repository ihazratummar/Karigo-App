package com.karigojobs.share.model

data class SiteEstimateModel(
    val id: String,
    val projectTitle: String,
    val siteNote: String?,
    val clientId: String,
    val clientName: String,
    val date : Long,
    val showRate: Boolean,
    val totalItems : Double,
    val total: Double?,
    val createAt: Long = 0L
)




data class SiteEstimateMaterial(
    val id: String,
    val estimateId: String,
    val materialId: String?,
    val materialName : String,
    val quantity: Double,
    val unit: String,
    val rate: Double,
    val quantityInput: String = quantity.toString(),
    val rateInput: String = rate.toString()
){
    val total : Double get() = rate * quantity
}
