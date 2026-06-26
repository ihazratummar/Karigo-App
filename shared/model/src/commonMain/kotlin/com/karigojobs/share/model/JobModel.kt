package com.karigojobs.share.model

data class JobModel(
    val id: String,
    val clientId: String,
    val clientName: String,
    val title: String,
    val description: String,
    val status: JobStatus,
    val tradeType: TradeType,
    val materialTotal: Double,
    val total: Double,
    val totalItems: Int = 0,
    val notes: String,
    val jobDate: Long = 0L,
    val createdAt: Long = 0L
)

enum class JobStatus {
    PENDING{
        override fun toString(): String {
            return "Pending"
        }
    },
    IN_PROGRESS{
        override fun toString(): String {
            return "In Progress"
        }
    },
    COMPLETED{
        override fun toString(): String {
            return "Complete"
        }
    },
    INVOICED {
        override fun toString(): String {
            return "Invoiced"
        }
    },
    PAID {
        override fun toString(): String {
            return "Paid"
        }
    }
}

data class JobLabourItemModel(
    val id: String,
    val jobId: String,
    val itemName: String,
    val quantity: Long,
    val rate: Double,
    val total: Double,
    val unit: String
) {

    val mainTotal: Double get() = rate * quantity

}

data class JobMaterialItemModel(
    val id: String,
    val jobId: String,
    val materialId: String?,
    val name: String,
    val unit: String,
    val unitPrice: Double,
    val quantity: Int,
    val total: Double,
    val quantityInput: String = quantity.toString()
) {

    val mainTotal: Double get() = unitPrice * quantity

}
