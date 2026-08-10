package com.karigojobs.presentation.job.details

import com.karigojob.share.utils.formatNumber
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.share.model.JobPaymentModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class PrintItem(
    val name: String,
    val subtitle: String?,
    val quantity: Double,
    val unit: String,
    val rate: Double,
    val total: Double
)

data class SubtotalItem(
    val label: String,
    val value: Double
)

object InvoiceHtmlBuilder {

    private fun formatEpochMs(epochMs: Long): String {
        if (epochMs == 0L) return "N/A"
        return try {
            val instant = Instant.fromEpochMilliseconds(epochMs)
            val date = instant.toLocalDateTime(TimeZone.UTC).date
            val monthName = date.month.name.lowercase()
                .replaceFirstChar { it.uppercase() }
                .take(3)
            "${date.dayOfMonth} $monthName ${date.year}"
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun buildWhatsappText(
        job: JobModel,
        client: ClientModel?,
        workerProfile: WorkerProfileModel?,
        labourItems: List<JobLabourItemModel>,
        materialItems: List<JobMaterialItemModel>,
        payments: List<JobPaymentModel> = emptyList(),
        currencySymbol: String
    ): String {
        val invoiceNumber = "INV-${job.id.takeLast(6).uppercase()}"
        val dateString = formatEpochMs(job.createdAt)
        val businessName = workerProfile?.businessName?.ifBlank { null } ?: workerProfile?.ownerName?.ifBlank { null } ?: "Karigo Provider"
        val clientName = client?.name ?: job.clientName
        val clientAddress = client?.address?.ifBlank { null }
        val clientPhone = client?.phone?.ifBlank { null }
        val clientEmail = client?.email?.ifBlank { null }

        val sb = StringBuilder()
        sb.append("*INVOICE* from *$businessName*\n")
        sb.append("Invoice No: $invoiceNumber\n")
        sb.append("Date: $dateString\n")
        sb.append("Status: ${job.status}\n\n")
        sb.append("*BILL TO:*\n")
        sb.append("$clientName\n")
        if (!clientAddress.isNullOrBlank()) sb.append("$clientAddress\n")
        if (!clientPhone.isNullOrBlank()) sb.append("Phone: $clientPhone\n")
        if (!clientEmail.isNullOrBlank()) sb.append("Email: $clientEmail\n")
        sb.append("\n*ITEMS:*\n")
        
        if (job.includeLabourInInvoice) {
            labourItems.forEach {
                val workerPrefix = if (it.workersCount > 1) "${it.workersCount} Workers x " else ""
                sb.append("- ${it.itemName} ${workerPrefix}x${it.quantity}: $currencySymbol${it.mainTotal.formatNumber()}\n")
            }
        }
        materialItems.forEach {
            sb.append("- ${it.name} x${it.quantity}: $currencySymbol${it.mainTotal.formatNumber()}\n")
        }
        
        sb.append("\n*TOTAL: $currencySymbol${job.total.formatNumber()}*\n")

        if (payments.isNotEmpty()) {
            sb.append("\n*PAYMENTS:*\n")
            payments.forEach { pay ->
                val dateStr = formatEpochMs(pay.paymentDate)
                val noteStr = if (pay.note.isNotBlank()) " - ${pay.note}" else ""
                sb.append("- $dateStr$noteStr: $currencySymbol${pay.amount.formatNumber()}\n")
            }
            val paymentsTotal = payments.sumOf { it.amount }
            val balanceDue = job.total - paymentsTotal
            sb.append("\n*BALANCE DUE: $currencySymbol${balanceDue.formatNumber()}*\n")
        }

        sb.append("\nThank you for your business!")
        
        return sb.toString()
    }

    /**
     * Specific helper to build Invoice HTML from a JobModel.
     */
    fun buildInvoiceHtml(
        job: JobModel,
        client: ClientModel?,
        workerProfile: WorkerProfileModel?,
        labourItems: List<JobLabourItemModel>,
        materialItems: List<JobMaterialItemModel>,
        payments: List<JobPaymentModel> = emptyList(),
        currencySymbol: String
    ): String {
        val invoiceNumber = "INV-${job.id.takeLast(6).uppercase()}"
        val dateString = formatEpochMs(job.createdAt)
        
        val items = mutableListOf<PrintItem>()
        
        // Map Labour items
        if (job.includeLabourInInvoice) {
            labourItems.forEach {
                items.add(
                    PrintItem(
                        name = it.itemName,
                        subtitle = if (it.workersCount > 1) "Labour / Services (${it.workersCount} Workers)" else "Labour / Services",
                        quantity = it.quantity.toDouble(),
                        unit = it.unit,
                        rate = it.rate,
                        total = it.mainTotal
                    )
                )
            }
        }
        
        // Map Material items
        materialItems.forEach {
            items.add(
                PrintItem(
                    name = it.name,
                    subtitle = "Material",
                    quantity = it.quantity.toDouble(),
                    unit = it.unit,
                    rate = it.unitPrice,
                    total = it.mainTotal
                )
            )
        }

        val subtotals = mutableListOf<SubtotalItem>()
        if (job.includeLabourInInvoice && labourItems.isNotEmpty()) {
            val labourTotalVal = labourItems.sumOf { it.quantity * it.rate }
            subtotals.add(SubtotalItem("Labour Subtotal", labourTotalVal))
        }
        if (materialItems.isNotEmpty()) {
            subtotals.add(SubtotalItem("Materials Subtotal", job.materialTotal))
        }

        val statusColor = when (job.status.name) {
            "PAID" -> "#10b981"
            "COMPLETED" -> "#3b82f6"
            "IN_PROGRESS" -> "#f59e0b"
            else -> "#6b7280"
        }

        return buildGenericHtml(
            title = "INVOICE",
            documentNumber = invoiceNumber,
            dateString = dateString,
            statusText = job.status.toString(),
            statusColor = statusColor,
            workerProfile = workerProfile,
            client = client,
            clientNameFallback = job.clientName,
            items = items,
            subtotals = subtotals,
            grandTotal = job.total,
            payments = payments,
            currencySymbol = currencySymbol,
            description = job.description,
            notes = job.notes
        )
    }

    /**
     * Generic, reusable HTML generator for invoices, estimates, etc.
     */
    fun buildGenericHtml(
        title: String,
        documentNumber: String,
        dateString: String,
        statusText: String?,
        statusColor: String?,
        workerProfile: WorkerProfileModel?,
        client: ClientModel?,
        clientNameFallback: String,
        items: List<PrintItem>,
        subtotals: List<SubtotalItem>,
        grandTotal: Double,
        payments: List<JobPaymentModel> = emptyList(),
        currencySymbol: String,
        description: String? = null,
        notes: String? = null,
        showRate: Boolean = true
    ): String {
        // Build items table rows
        val itemsHtml = StringBuilder()
        items.forEach { item ->
            itemsHtml.append("""
                <tr>
                    <td class="desc-cell">
                        <div class="item-name">${item.name}</div>
                        ${if (item.subtitle != null) "<div class=\"item-sub\">${item.subtitle}</div>" else ""}
                    </td>
                    <td class="qty-cell">${item.quantity.formatNumber()} ${item.unit}</td>
                    ${if (showRate) "<td class=\"rate-cell\">$currencySymbol${item.rate.formatNumber()}</td>" else ""}
                    ${if (showRate) "<td class=\"total-cell\">$currencySymbol${item.total.formatNumber()}</td>" else ""}
                </tr>
            """.trimIndent())
        }

        // Build subtotals section
        val subtotalsHtml = StringBuilder()
        subtotals.forEach { sub ->
            subtotalsHtml.append("""
                <tr>
                    <td class="summary-label">${sub.label}:</td>
                    <td class="summary-val">$currencySymbol${sub.value.formatNumber()}</td>
                </tr>
            """.trimIndent())
        }

        val businessName = workerProfile?.businessName?.ifBlank { null } ?: workerProfile?.ownerName?.ifBlank { null } ?: "Karigo Provider"
        val businessAddress = workerProfile?.address?.ifBlank { null }
        val businessPhone = workerProfile?.phone?.ifBlank { null }
        val businessEmail = workerProfile?.email?.ifBlank { null }
        val businessGst = workerProfile?.gstNumber?.ifBlank { null }

        val clientName = client?.name ?: clientNameFallback
        val clientAddress = client?.address?.ifBlank { null }
        val clientPhone = client?.phone?.ifBlank { null }
        val clientEmail = client?.email?.ifBlank { null }

        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>$title $documentNumber</title>
                <style>
                    @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
                    
                    @page {
                        size: A4;
                        margin: 6mm 10mm;
                    }
                    
                    body {
                        font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
                        color: #1e293b;
                        margin: 0;
                        padding: 0;
                        font-size: 11px;
                        line-height: 1.4;
                        background-color: #ffffff;
                    }
                    
                    .invoice-container {
                        max-width: 800px;
                        margin: 0 auto;
                    }
                    
                    /* Header styling */
                    .header-table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-bottom: 12px;
                    }
                    
                    .header-table td {
                        vertical-align: top;
                        padding: 0;
                    }
                    
                    .company-info {
                        text-align: left;
                    }
                    
                    .company-name {
                        font-size: 16px;
                        font-weight: 700;
                        color: #0f172a;
                        margin-bottom: 4px;
                    }
                    
                    .company-detail {
                        font-size: 10px;
                        color: #475569;
                        margin-bottom: 1px;
                    }
                    
                    .invoice-meta {
                        text-align: right;
                    }
                    
                    .invoice-title {
                        font-size: 20px;
                        font-weight: 800;
                        color: #0f172a;
                        letter-spacing: -0.5px;
                        margin-bottom: 4px;
                    }
                    
                    .meta-badge {
                        display: inline-block;
                        padding: 2px 8px;
                        font-size: 9px;
                        font-weight: 600;
                        text-transform: uppercase;
                        border-radius: 4px;
                        color: white;
                        margin-top: 2px;
                        margin-bottom: 8px;
                    }
                    
                    .meta-item {
                        font-size: 11px;
                        margin-bottom: 2px;
                    }
                    
                    .meta-label {
                        color: #64748b;
                        font-weight: 500;
                    }
                    
                    .meta-value {
                        color: #0f172a;
                        font-weight: 600;
                    }
                    
                    /* Divider */
                    .divider {
                        border-top: 1px solid #e2e8f0;
                        margin: 12px 0;
                    }
                    
                    /* Billing Info */
                    .billing-table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-bottom: 15px;
                    }
                    
                    .billing-table td {
                        vertical-align: top;
                        padding: 0;
                        width: 50%;
                    }
                    
                    .section-title {
                        font-size: 9px;
                        font-weight: 600;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                        color: #64748b;
                        margin-bottom: 4px;
                    }
                    
                    .client-name {
                        font-size: 13px;
                        font-weight: 700;
                        color: #0f172a;
                        margin-bottom: 4px;
                    }
                    
                    .client-detail {
                        font-size: 10px;
                        color: #475569;
                        margin-bottom: 1px;
                    }
                    
                    /* Items Table */
                    .items-table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-bottom: 12px;
                    }
                    
                    .items-table th {
                        font-size: 9px;
                        font-weight: 600;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                        color: #475569;
                        background-color: #f8fafc;
                        border-bottom: 2px solid #e2e8f0;
                        padding: 6px 8px;
                        text-align: left;
                    }
                    
                    .items-table td {
                        padding: 5px 8px;
                        border-bottom: 1px solid #e2e8f0;
                        vertical-align: middle;
                    }
                    
                    .items-table th.qty-cell, .items-table td.qty-cell {
                        text-align: center;
                    }
                    
                    .items-table th.rate-cell, .items-table td.rate-cell,
                    .items-table th.total-cell, .items-table td.total-cell {
                        text-align: right;
                    }
                    
                    .desc-cell {
                        max-width: 300px;
                    }
                    
                    .item-name {
                        font-size: 11px;
                        font-weight: 600;
                        color: #0f172a;
                    }
                    
                    .item-sub {
                        font-size: 9px;
                        color: #64748b;
                        margin-top: 1px;
                    }
                    
                    /* Summary Section */
                    .summary-container {
                        float: right;
                        width: 250px;
                        margin-top: 5px;
                        margin-bottom: 15px;
                    }
                    
                    .summary-table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    
                    .summary-table td {
                        padding: 4px 8px;
                        font-size: 11px;
                    }
                    
                    .summary-label {
                        color: #64748b;
                        text-align: left;
                    }
                    
                    .summary-val {
                        text-align: right;
                        color: #334155;
                        font-weight: 500;
                    }
                    
                    .summary-total-row td {
                        padding-top: 8px;
                        border-top: 1px solid #cbd5e1;
                    }
                    
                    .total-label {
                        font-size: 13px;
                        font-weight: 700;
                        color: #0f172a;
                    }
                    
                    .total-val {
                        font-size: 15px;
                        font-weight: 800;
                        color: #0f172a;
                    }
                    
                    .clear-fix {
                        clear: both;
                    }
                    
                    /* Notes & Footer */
                    .notes-section {
                        margin-top: 15px;
                        background-color: #f8fafc;
                        border-radius: 6px;
                        padding: 10px;
                        border-left: 3px solid #cbd5e1;
                    }
                    
                    .notes-content {
                        font-size: 10px;
                        color: #475569;
                    }
                    
                    .footer {
                        margin-top: 25px;
                        text-align: center;
                        color: #94a3b8;
                        font-size: 9px;
                    }
                </style>
            </head>
            <body>
                <div class="invoice-container">
                    <!-- Top Header -->
                    <table class="header-table">
                        <tr>
                            <td style="width: 35%; vertical-align: top;">
                                <div class="company-info">
                                    <div class="company-name">$businessName</div>
                                    ${if (!businessAddress.isNullOrBlank()) "<div class=\"company-detail\">$businessAddress</div>" else ""}
                                    ${if (!businessPhone.isNullOrBlank()) "<div class=\"company-detail\">Phone: $businessPhone</div>" else ""}
                                    ${if (!businessEmail.isNullOrBlank()) "<div class=\"company-detail\">Email: $businessEmail</div>" else ""}
                                    ${if (businessGst != null) "<div class=\"company-detail\">GST/TAX: $businessGst</div>" else ""}
                                </div>
                            </td>
                            <td style="width: 35%; vertical-align: top; padding-left: 15px;">
                                <div>
                                    <div class="section-title">Bill To</div>
                                    <div class="client-name">$clientName</div>
                                    ${if (!clientAddress.isNullOrBlank()) "<div class=\"client-detail\">$clientAddress</div>" else ""}
                                    ${if (!clientPhone.isNullOrBlank()) "<div class=\"client-detail\">Phone: $clientPhone</div>" else ""}
                                    ${if (!clientEmail.isNullOrBlank()) "<div class=\"client-detail\">Email: $clientEmail</div>" else ""}
                                </div>
                            </td>
                            <td style="width: 30%; vertical-align: top; text-align: right;">
                                <div class="invoice-meta">
                                    <div class="invoice-title">$title</div>
                                    ${if (statusText != null) "<div class=\"meta-badge\" style=\"background-color: ${statusColor ?: "#6b7280"};\">$statusText</div>" else ""}
                                    <div class="meta-item">
                                        <span class="meta-label">Document No:</span>
                                        <span class="meta-value">$documentNumber</span>
                                    </div>
                                    <div class="meta-item">
                                        <span class="meta-label">Date:</span>
                                        <span class="meta-value">$dateString</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    
                    <div class="divider" style="margin: 6px 0;"></div>
                    
                    <!-- Items Table -->
                    <table class="items-table">
                        <thead>
                            <tr>
                                ${if (showRate) """
                                    <th style="width: 50%;">Description</th>
                                    <th class="qty-cell" style="width: 15%;">Quantity</th>
                                    <th class="rate-cell" style="width: 15%;">Rate</th>
                                    <th class="total-cell" style="width: 20%;">Total</th>
                                """.trimIndent() else """
                                    <th style="width: 75%;">Description</th>
                                    <th class="qty-cell" style="width: 25%;">Quantity</th>
                                """.trimIndent()}
                            </tr>
                        </thead>
                        <tbody>
                            $itemsHtml
                        </tbody>
                    </table>
                    
                    <!-- Summary and Notes Side-by-Side -->
                    <table style="width: 100%; border-collapse: collapse; margin-top: 5px;">
                        <tr>
                            <td style="vertical-align: top; width: 55%; padding: 0 12px 0 0;">
                                ${if (!description.isNullOrBlank() || !notes.isNullOrBlank()) """
                                    <div class="notes-section" style="margin-top: 0;">
                                        <div class="section-title">Notes & Description</div>
                                        <div class="notes-content">
                                            ${if (!description.isNullOrBlank()) "<p style=\"margin: 2px 0;\"><strong>Details:</strong> $description</p>" else ""}
                                            ${if (!notes.isNullOrBlank()) "<p style=\"margin: 2px 0;\"><strong>Additional Notes:</strong> $notes</p>" else ""}
                                        </div>
                                    </div>
                                """.trimIndent() else ""}
                            </td>
                            <td style="vertical-align: top; width: 45%; padding: 0;">
                                 ${if (showRate) """
                                     <div class="summary-container" style="float: none; width: 100%; margin: 0;">
                                         <table class="summary-table">
                                             $subtotalsHtml
                                             <tr class="summary-total-row">
                                                 <td class="total-label">Total:</td>
                                                 <td class="total-val">$currencySymbol${grandTotal.formatNumber()}</td>
                                             </tr>
                                             ${if (payments.isNotEmpty()) {
                                                 val paymentsHtml = StringBuilder()
                                                 paymentsHtml.append("""
                                                     <tr>
                                                         <td colspan="2" style="padding-top: 10px; border-top: 1px dashed #cbd5e1;">
                                                             <div style="font-size: 8px; font-weight: 700; color: #64748b; letter-spacing: 0.5px; text-transform: uppercase; margin-bottom: 2px;">Payments</div>
                                                         </td>
                                                     </tr>
                                                 """.trimIndent())
                                                 payments.forEach { pay ->
                                                     val dateStr = formatEpochMs(pay.paymentDate)
                                                     val noteStr = if (pay.note.isNotBlank()) " &middot; ${pay.note}" else ""
                                                     paymentsHtml.append("""
                                                         <tr>
                                                             <td class="summary-label" style="font-size: 10px; padding: 2px 0;">$dateStr$noteStr</td>
                                                             <td class="summary-val" style="font-size: 10px; color: #10b981; font-weight: 600; padding: 2px 0;">$currencySymbol${pay.amount.formatNumber()}</td>
                                                         </tr>
                                                     """.trimIndent())
                                                 }
                                                 val paymentsTotal = payments.sumOf { it.amount }
                                                 val balanceDue = grandTotal - paymentsTotal
                                                 paymentsHtml.append("""
                                                     <tr style="border-top: 1px solid #cbd5e1;">
                                                         <td class="total-label" style="font-size: 11px; padding-top: 6px;">Balance Due:</td>
                                                         <td class="total-val" style="font-size: 12px; color: #ef4444; font-weight: 700; padding-top: 6px;">$currencySymbol${balanceDue.formatNumber()}</td>
                                                     </tr>
                                                 """.trimIndent())
                                                 paymentsHtml.toString()
                                             } else ""}
                                         </table>
                                     </div>
                                 """.trimIndent() else ""}
                            </td>
                        </tr>
                    </table>
                    
                    <!-- Footer -->
                    <table style="width: 100%; margin-top: 15px; border-top: 1px solid #cbd5e1; padding-top: 5px; font-size: 9px; color: #94a3b8;">
                        <tr>
                            <td style="text-align: left; padding: 0;">Thank you for your business!</td>
                            <td style="text-align: right; padding: 0;">Generated by Karigo App</td>
                        </tr>
                    </table>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
