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
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow
import androidx.core.net.toUri
import com.karigojobs.ui.toLocaleString
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

@Composable
fun JobDetailsScreen(
    modifier: Modifier = Modifier,
    jobDetailsState: JobDetailsState,
    jobDetailsEffect: SharedFlow<JobDetailsEffect>?,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    event: (JobDetailsIntent) -> Unit
) {

    val context = LocalContext.current
    val snackbarState = remember { SnackbarHostState() }
    var showShareDialog by remember { mutableStateOf(false) }
    var shareType by remember { mutableStateOf<ShareType?>(null) }

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

                if (jobDetailsState.jobLabourItems.isNotEmpty()) {
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

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = appColor.accentBg
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
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(Res.string.common_grand_total),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                                Text(
                                    text = stringResource(Res.string.job_details_labour_and_materials),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                            }

                            Text(
                                text = "${deviceInfo.currency}${jobDetailsState.jobModel?.total?.toLocaleString()}",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = KarigojobsIconColor
                                )
                            )
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

                DocumentPreviewCard(
                    businessName = businessName,
                    documentId = "Invoice #INV-${jobDetailsState.jobModel?.id?.takeLast(6)?.uppercase()}",
                    dateText = jobDetailsState.jobModel?.createdAt?.let { formatEpochMs(it) } ?: "N/A",
                    clientName = jobDetailsState.clientModel?.name ?: jobDetailsState.jobModel?.clientName ?: "",
                    clientAddress = jobDetailsState.clientModel?.address?.ifBlank { null },
                    items = previewItems,
                    totalText = "$currency${jobDetailsState.jobModel?.total?.toLocaleString()}"
                )
            }

                item {
                    val currency = deviceInfo.currency
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
                            icon = R.drawable.ic_pdf
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
                            icon = R.drawable.whatsapp
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
        ShareChoiceDialog(
            onDismiss = { showShareDialog = false },
            onSharePdf = {
                shareType = ShareType.SHARE_PDF
                event(JobDetailsIntent.GenerateInvoicePdf(currencySymbol = currency))
            },
            onShareText = {
                event(JobDetailsIntent.ShareInvoiceOnWhatsapp(currencySymbol = currency))
            }
        )
    }
}

