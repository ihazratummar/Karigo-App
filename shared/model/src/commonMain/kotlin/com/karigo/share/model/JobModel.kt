package com.karigo.share.model

data class JobModel(
    val id: String,
    val clientId: String,
    val clientName: String,
    val title: String,
    val description: String,
    val status: JobStatus,
    val materialTotal: Double,
    val total : Double,
    val notes: String,
    val jobDate: Long= 0L,
    val createdAt: Long = 0L
)

enum class JobStatus {
    DRAFT, IN_PROGRESS , COMPLETED, INVOICED, PAID
}

data class JobLabourItemModel(
    val id: String,
    val jobId: String,
    val itemName: String,
    val quantity: Long,
    val rate: Double,
    val total: Double,
    val unit: String
){

    val mainTotal : Double get() = rate * quantity

}
data class JobMaterialItemModel(
    val id : String,
    val jobId: String,
    val materialId: String?,
    val name: String,
    val unit: String,
    val unitPrice: Double,
    val quantity: Int,
    val total : Double
)