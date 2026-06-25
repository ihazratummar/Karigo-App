package com.karigojobs.share.model

data class WorkerProfileModel(
    val id: Long,
    val businessName: String,
    val ownerName : String,
    val address: String,
    val phone: String,
    val gstNumber: String  = "",
    val logoPath : String?  = "",
    val email : String = ""
)
