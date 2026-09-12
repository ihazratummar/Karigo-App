package com.karigojobs.app.feature.job.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import android.print.PrintManager
import android.print.PrintAttributes
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.karigojob.share.utils.formatNumber
import com.karigojobs.app.android.ui.R
import com.karigojobs.app.feature.job.component.JobDetailsCard
import com.karigojobs.app.feature.job.component.JobDetailsClientInfo
import com.karigojobs.app.feature.job.component.JobDetailsStatusCard
import com.karigojobs.app.feature.job.component.JobStatusChangeModal
import com.karigojobs.app.feature.job.component.LabourItemList
import com.karigojobs.app.feature.job.component.MaterialItemList
import com.karigojobs.presentation.job.details.JobDetailsEffect
import com.karigojobs.presentation.job.details.JobDetailsIntent
import com.karigojobs.presentation.job.details.JobDetailsState
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.common.KarigoButtons
import com.karigojobs.ui.common.DocumentPreviewCard
import com.karigojobs.ui.common.PreviewItem
import com.karigojobs.ui.common.ShareChoiceDialog
import com.karigojobs.ui.ShareType
import com.karigojobs.ui.formatEpochMs
import com.karigojobs.ui.sharePdfFile
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.common.ProFeatureDialog
import com.karigojobs.ui.common.PreviewPaymentItem
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.BorderStroke
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow
import androidx.core.net.toUri
import com.karigojobs.ui.toLocaleString
import com.karigojobs.ui.common.PaymentsReceivedCard
import com.karigojobs.ui.common.RecordPaymentBottomSheet
import com.karigojobs.ui.common.UiPaymentRecord
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_export
import karigojobs.shared.resources.generated.resources.common_grand_total
import karigojobs.shared.resources.generated.resources.common_invoice_preview
import karigojobs.shared.resources.generated.resources.common_labour
import karigojobs.shared.resources.generated.resources.common_materials
import karigojobs.shared.resources.generated.resources.job_detail_title
import karigojobs.shared.resources.generated.resources.job_details_labour_and_materials
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 26/05/26
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    modifier: Modifier = Modifier,
    jobDetailsState: JobDetailsState,
    jobDetailsEffect: SharedFlow<JobDetailsEffect>?,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onPaywallClick: () -> Unit,
    event: (JobDetailsIntent) -> Unit
) {

    val context = LocalContext.current
    val snackbarState = remember { SnackbarHostState() }
    var showShareDialog by remember { mutableStateOf(false) }
    var shareType by remember { mutableStateOf<ShareType?>(null) }
    
    var showRecordPaymentSheet by remember { mutableStateOf(false) }
    var amountInput by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("Cash") }
    var paymentDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var noteInput by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        jobDetailsEffect?.collect { effect ->
            when (effect) {
                is JobDetailsEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                JobDetailsEffect.NavigationBack -> {
                    onBackClick()
                }

                is JobDetailsEffect.ShareInvoicePdf -> {
                    if (shareType == ShareType.SHARE_PDF) {
                        sharePdfFile(context, effect.html, effect.jobTitle)
                    } else {
                        try {
                            val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
                            if (printManager == null) {
                                android.widget.Toast.makeText(context, "Print service is not available on this device", android.widget.Toast.LENGTH_LONG).show()
                                return@collect
                            }
                            val webView = WebView(context).apply {
                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        try {
                                            val printAdapter = createPrintDocumentAdapter(effect.jobTitle)
                                            printManager.print(effect.jobTitle, printAdapter, PrintAttributes.Builder().build())
                                        } catch (e: Exception) {
                                            android.widget.Toast.makeText(context, "Failed to open printer: ${e.localizedMessage}", android.widget.Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                            webView.loadDataWithBaseURL(null, effect.html, "text/HTML", "UTF-8", null)
                        } catch (e: Exception) {
                            android.widget.Toast.makeText(context, "Failed to print: ${e.localizedMessage}", android.widget.Toast.LENGTH_LONG).show()
                        }
                    }
                }

                is JobDetailsEffect.ShareTextOnWhatsapp -> {
                    try {
                        val whatsappIntent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                            data = ("https://api.whatsapp.com/send?text=" + android.net.Uri.encode(
                                effect.text
                            )).toUri()
                        }
                        context.startActivity(whatsappIntent)
                    } catch (_: Exception) {
                        val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(android.content.Intent.EXTRA_TEXT, effect.text)
                        }
                        context.startActivity(android.content.Intent.createChooser(shareIntent, "Share Invoice"))
                    }
                }

                JobDetailsEffect.NavigateToPaywall -> {
                    onPaywallClick()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        topBar = {
            KarigoMiddleTextTopAppBar(
                onNavigationClick = { onBackClick() },
                title = stringResource(Res.string.job_detail_title),
                action = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(-dimens.Space.sm)
                    ) {

                        KarigoIconWIthBgCick(
                            icon = R.drawable.edit,
                            iconColor = KarigojobsText3,
                            size = dimens.Height.minTouch / 0.9f,
                            onClick = {
                                jobDetailsState.jobModel?.id?.let { onEditClick(it) }
                            }
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.delete,
                            iconColor = MaterialTheme.colorScheme.error,
                            iconBackGroundColor = Color.Transparent,
                            isBorder = true,
                            onClick = {
                                event(JobDetailsIntent.DeletePopUpOpen(isOpen = true))
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        jobDetailsState.jobModel?.let { jobModel ->

            if (jobDetailsState.isDeletePopUpOpen){
                DeleteDialog(
                    onCancelClick = {event(JobDetailsIntent.DeletePopUpOpen(isOpen = false))},
                    onConfirmClick = {
                        event(JobDetailsIntent.DeleteJob(jobId = jobModel.id))
                    }
                )
            }

            if (jobDetailsState.isJobStatusModalOpen) {
                JobStatusChangeModal(
                    onDismiss = {
                        event(JobDetailsIntent.ToggleJobStatusModal(false))
                    },
                    onStatusClick = {
                        event(JobDetailsIntent.ChangeJobStatus(id = jobModel.id, jobStatus = it))
                    },
                    jobStatus = jobModel.status
                )
            }

            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = dimens.Padding.base)
                    .padding(top = dimens.Padding.base),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                item {
                    JobDetailsCard(
                        job = jobModel
                    )
                }

                item {
                    JobDetailsStatusCard(
                        job = jobModel,
                        onChangeClick = {
                            event(JobDetailsIntent.ToggleJobStatusModal(isOpen = true))
                        }
                    )
                }
                jobDetailsState.clientModel?.let { clientModel ->
                    item {
                        JobDetailsClientInfo(clientModel = clientModel)
                    }
                }

                if (jobDetailsState.jobModel?.includeLabourInInvoice == true && jobDetailsState.jobLabourItems.isNotEmpty()) {
                    item {
                        LabourItemList(
                            labourItems = jobDetailsState.jobLabourItems,
                            total = jobDetailsState.labourTotal
                        )
                    }
                }
                if (jobDetailsState.jobMaterialItems.isNotEmpty()) {
                    item {
                        MaterialItemList(
                            materialItems = jobDetailsState.jobMaterialItems,
                            total = jobDetailsState.materialTotal
                        )
                    }
                }

                // Payments log list section
                item {
                    Spacer(modifier = Modifier.height(dimens.Space.sm))
                    val uiPayments = remember(jobDetailsState.jobPayments) {
                        jobDetailsState.jobPayments.map { pay ->
                            UiPaymentRecord(
                                id = pay.id,
                                amount = pay.amount,
                                paymentMethod = pay.paymentMethod,
                                paymentDate = pay.paymentDate,
                                note = pay.note
                            )
                        }
                    }
                    PaymentsReceivedCard(
                        currency = deviceInfo.currency,
                        payments = uiPayments,
                        totalReceived = jobDetailsState.paymentsTotal,
                        onAddPaymentClick = {
                            amountInput = ""
                            selectedMethod = "Cash"
                            paymentDate = System.currentTimeMillis()
                            noteInput = ""
                            showRecordPaymentSheet = true
                        },
                        onDeletePaymentClick = { paymentId ->
                            event(JobDetailsIntent.DeletePayment(paymentId))
                        },
                        formatEpochMs = { formatEpochMs(it) }
                    )
                }

                item {
                    val currency = deviceInfo.currency
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0C2424)
                        ),
                        shape = KarigojobsShapes.medium,
                        border = customCardBorder()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(dimens.Padding.base)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Grand Total",
                                        color = appColor.secondaryText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "$currency${jobDetailsState.jobModel?.total?.toLocaleString()}",
                                        color = Color(0xFF00FFCC),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "Remaining",
                                        color = appColor.secondaryText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "$currency${jobDetailsState.remainingBalance.toLocaleString()}",
                                        color = Color(0xFFEF4444),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Progress Bar
                            LinearProgressIndicator(
                                progress = { jobDetailsState.paidPercentage },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = Color(0xFF00FFCC),
                                trackColor = Color(0xFF1B3D3D)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${(jobDetailsState.paidPercentage * 100).toInt()}% paid",
                                    color = appColor.secondaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "$currency${jobDetailsState.paymentsTotal.toLocaleString()} of $currency${jobDetailsState.jobModel?.total?.toLocaleString()}",
                                    color = appColor.secondaryText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(dimens.Space.sm))
                    Text(
                        text = stringResource(Res.string.common_invoice_preview),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = appColor.secondaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            item {
                val currency = deviceInfo.currency
                val businessName = jobDetailsState.workerProfileModel?.businessName?.ifBlank { null }
                    ?: jobDetailsState.workerProfileModel?.ownerName?.ifBlank { null }
                    ?: "Karigo Provider"

                val previewItems = mutableListOf<PreviewItem>()
                if (jobDetailsState.jobModel?.includeLabourInInvoice == true) {
                    jobDetailsState.jobLabourItems.forEach { item ->
                        previewItems.add(
                            PreviewItem(
                                name = item.itemName,
                                subtitle = stringResource(Res.string.common_labour),
                                quantityText = item.quantity.toLocaleString(),
                                totalText = "$currency${item.mainTotal.toLocaleString()}"
                            )
                        )
                    }
                }
                jobDetailsState.jobMaterialItems.forEach { item ->
                    previewItems.add(
                        PreviewItem(
                            name = item.name,
                            subtitle = stringResource(Res.string.common_materials),
                            quantityText = item.quantity.toLocaleString(),
                            totalText = "$currency${item.mainTotal.toLocaleString()}"
                        )
                    )
                }

                val previewPayments = jobDetailsState.jobPayments.map { pay ->
                    PreviewPaymentItem(
                        dateText = formatEpochMs(pay.paymentDate),
                        note = pay.note.ifBlank { null },
                        amountText = "$currency${pay.amount.toLocaleString()}"
                    )
                }

                DocumentPreviewCard(
                    businessName = businessName,
                    documentId = "Invoice #INV-${jobDetailsState.jobModel?.id?.takeLast(6)?.uppercase()}",
                    dateText = jobDetailsState.jobModel?.createdAt?.let { formatEpochMs(it) } ?: "N/A",
                    clientName = jobDetailsState.clientModel?.name ?: jobDetailsState.jobModel?.clientName ?: "",
                    clientAddress = jobDetailsState.clientModel?.address?.ifBlank { null },
                    items = previewItems,
                    totalText = "$currency${jobDetailsState.jobModel?.total?.toLocaleString()}",
                    payments = previewPayments,
                    balanceDueText = "$currency${jobDetailsState.remainingBalance.toLocaleString()}"
                )
            }

                item {
                    val currency = deviceInfo.currency
                    val isPdfUnlocked = jobDetailsState.isPro || !jobDetailsState.monthlyJobLimit.isPdfQuotaExhausted
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                    ) {
                        // Export PDF Button
                        KarigoButtons(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                shareType = ShareType.PRINT
                                event(JobDetailsIntent.GenerateInvoicePdf(currencySymbol = currency))
                            },
                            buttonColor = appColor.cardColors,
                            contentColor = Color(0xFFEF5350),
                            label = stringResource(Res.string.common_export),
                            icon = R.drawable.ic_pdf,
                            isUnlocked = isPdfUnlocked,
                            onProRequiredClick = {
                                event(JobDetailsIntent.ToggleProDialog(isOpen = true, featureName = "PDF Export"))
                            }
                        )

                        // WhatsApp Button
                        KarigoButtons(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                showShareDialog = true
                            },
                            buttonColor = appColor.cardColors,
                            contentColor = Color(0xFF4CAF50),
                            label = "WhatsApp",
                            icon = R.drawable.whatsapp,
                            isUnlocked = true
                        )
                    }
                }
                item {
                    Spacer(Modifier.height(dimens.Space._8xl))
                }
            }
        }
    }

    if (showShareDialog) {
        val currency = deviceInfo.currency
        val isPdfUnlocked = jobDetailsState.isPro || !jobDetailsState.monthlyJobLimit.isPdfQuotaExhausted
        ShareChoiceDialog(
            onDismiss = { showShareDialog = false },
            onSharePdf = {
                shareType = ShareType.SHARE_PDF
                event(JobDetailsIntent.GenerateInvoicePdf(currencySymbol = currency))
            },
            onShareText = {
                event(JobDetailsIntent.ShareInvoiceOnWhatsapp(currencySymbol = currency))
            },
            isPdfUnlocked = isPdfUnlocked,
            onProRequiredClick = {
                showShareDialog = false
                event(JobDetailsIntent.ToggleProDialog(isOpen = true, featureName = "PDF Export"))
            }
        )
    }

    if (jobDetailsState.showProDialog) {
        ProFeatureDialog(
            onDismiss = {
                event(JobDetailsIntent.ToggleProDialog(isOpen = false))
            },
            onSeePlansClick = {
                event(JobDetailsIntent.ToggleProDialog(isOpen = false, featureName = "PAYWALL"))
            },
            featureName = jobDetailsState.proDialogFeatureName
        )
    }

    // Bottom Sheet for Record Payment
    if (showRecordPaymentSheet) {
        RecordPaymentBottomSheet(
            currency = deviceInfo.currency,
            remainingBalance = jobDetailsState.remainingBalance,
            amountInput = amountInput,
            onAmountChange = { amountInput = it.filter { char -> char.isDigit() || char == '.' } },
            selectedMethod = selectedMethod,
            onMethodSelect = { selectedMethod = it },
            paymentDate = paymentDate,
            onDatePickerClick = { showDatePicker = true },
            noteInput = noteInput,
            onNoteChange = { noteInput = it },
            onDismissRequest = { showRecordPaymentSheet = false },
            onAddPaymentClick = {
                val amount = amountInput.toDoubleOrNull() ?: 0.0
                event(JobDetailsIntent.AddPayment(
                    amount = amount,
                    paymentMethod = selectedMethod,
                    date = paymentDate,
                    note = noteInput
                ))
                showRecordPaymentSheet = false
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = paymentDate)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { paymentDate = it }
                    showDatePicker = false
                }) {
                    Text("OK", color = Color(0xFF00FFCC))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = appColor.secondaryText)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    titleContentColor = appColor.primaryText,
                    headlineContentColor = appColor.primaryText,
                    selectedDayContainerColor = Color(0xFF00FFCC),
                    selectedDayContentColor = Color.Black
                )
            )
        }
    }
}

