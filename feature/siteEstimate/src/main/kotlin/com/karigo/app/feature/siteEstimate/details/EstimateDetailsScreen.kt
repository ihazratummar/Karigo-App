package com.karigo.app.feature.siteEstimate.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import com.karigo.app.feature.siteEstimate.component.EstimateDetailsCard
import com.karigo.app.feature.siteEstimate.component.EstimateMaterialsList
import com.karigo.app.feature.siteEstimate.component.EstimateTotalCard
import com.karigojob.share.utils.formatNumber
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.estimate.details.EstimateDetailsEffect
import com.karigojobs.presentation.estimate.details.EstimateDetailsEvent
import com.karigojobs.presentation.estimate.details.EstimateDetailsState
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.DocumentPreviewCard
import com.karigojobs.ui.common.KarigoButtons
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.PreviewItem
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.common.ShareChoiceDialog
import com.karigojobs.ui.ShareType
import com.karigojobs.ui.formatEpochMs
import com.karigojobs.ui.sharePdfFile
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.common.ProFeatureDialog
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource
import karigojobs.shared.resources.generated.resources.*

@Composable
fun EstimateDetailsScreen(
    modifier: Modifier = Modifier,
    state: EstimateDetailsState,
    event: (EstimateDetailsEvent) -> Unit,
    effect: SharedFlow<EstimateDetailsEffect>?,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    onPaywallClick: () -> Unit
) {

    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }
    var showShareDialog by remember { mutableStateOf(false) }
    var shareType by remember { mutableStateOf<ShareType?>(null) }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is EstimateDetailsEffect.ShowError -> {
                    snackBarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                is EstimateDetailsEffect.ShareToWhatsApp -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            "https://api.whatsapp.com/send?text=${Uri.encode(effect.message)}".toUri()
                    }
                    context.startActivity(intent)
                }

                is EstimateDetailsEffect.ShareTextOnWhatsapp -> {
                    try {
                        val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("https://api.whatsapp.com/send?text=" + Uri.encode(effect.text))
                        }
                        context.startActivity(whatsappIntent)
                    } catch (e: Exception) {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, effect.text)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share text"))
                    }
                }

                is EstimateDetailsEffect.ShareEstimatePdf -> {
                    if (shareType == ShareType.SHARE_PDF) {
                        sharePdfFile(context, effect.html, effect.estimateTitle)
                    } else {
                        val printUnavailableMsg = org.jetbrains.compose.resources.getString(Res.string.estimate_detail_toast_print_unavailable)
                        val printFailedTemplate = org.jetbrains.compose.resources.getString(Res.string.estimate_detail_toast_print_failed)
                        val genericFailedTemplate = org.jetbrains.compose.resources.getString(Res.string.estimate_detail_toast_generic_failed)
                        try {
                            val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
                            if (printManager == null) {
                                android.widget.Toast.makeText(context, printUnavailableMsg, android.widget.Toast.LENGTH_LONG).show()
                                return@collect
                            }
                            val webView = WebView(context).apply {
                                webViewClient = object : WebViewClient() {
                                    override fun onPageFinished(view: WebView?, url: String?) {
                                        try {
                                            val printAdapter = createPrintDocumentAdapter(effect.estimateTitle)
                                            printManager.print(effect.estimateTitle, printAdapter, PrintAttributes.Builder().build())
                                        } catch (e: Exception) {
                                            val msg = printFailedTemplate.format(e.localizedMessage ?: "")
                                            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                            webView.loadDataWithBaseURL(null, effect.html, "text/HTML", "UTF-8", null)
                        } catch (e: Exception) {
                            val msg = genericFailedTemplate.format(e.localizedMessage ?: "")
                            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_LONG).show()
                        }
                    }
                }

                EstimateDetailsEffect.NavigationBack -> {
                    onBackClick()
                }

                EstimateDetailsEffect.NavigateToPaywall -> {
                    onPaywallClick()
                }
            }
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        },
        topBar = {
            KarigoTopAppBar(
                onNavigationClick = { onBackClick() },
                title = stringResource(Res.string.estimate_detail_title),
                action = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(-dimens.Space.sm)
                    ) {

                        KarigoIconWIthBgCick(
                            icon = R.drawable.whatsapp,
                            iconColor = Color(0xFF25D366),
                            iconBackGroundColor = Color.Transparent,
                            size = dimens.Height.minTouch / 1f,
                            onClick = {
                                showShareDialog = true
                            },
                            isBorder = true
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.edit,
                            iconColor = KarigojobsText2,
                            size = dimens.Height.minTouch / 0.9f,
                            onClick = {
                                state.estimateDetails?.id?.let { onEditClick(it) }
                            }
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.delete,
                            iconColor = MaterialTheme.colorScheme.error,
                            iconBackGroundColor = Color.Transparent,
                            size = dimens.Height.minTouch / 1f,
                            onClick = {
                                event(
                                    EstimateDetailsEvent.ToggleDelete(isOpen = true)
                                )
                            },
                            isBorder = true
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (state.isDeleting) {
            DeleteDialog(
                onCancelClick = {
                    event(
                        EstimateDetailsEvent.ToggleDelete(
                            isOpen = false
                        )
                    )
                },
                onConfirmClick = {
                    state.estimateDetails?.id?.let { id ->
                        event(EstimateDetailsEvent.DeleteEstimate(estimateId = id))
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .contentHorizontalPadding()
                .padding(top = dimens.Padding.base),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item {
                EstimateDetailsCard(state = state)
            }
            item {
                EstimateMaterialsList(state = state)
            }

            item {
                state.estimateDetails?.showRate?.let {
                    if (it) {
                        EstimateTotalCard(
                            totalItemSize = state.siteEstimateMaterial.size,
                            estimateTotal = state.estimateDetails?.total ?: 0.0
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(dimens.Space.sm))
                Text(
                    text = stringResource(Res.string.estimate_detail_preview_title),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.secondaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            item {
                val currency = deviceInfo.currency
                val showRate = state.estimateDetails?.showRate ?: true
                val defaultProvider = stringResource(Res.string.estimate_detail_default_provider)
                val businessName = state.workerProfileModel?.businessName?.ifBlank { null }
                    ?: state.workerProfileModel?.ownerName?.ifBlank { null }
                    ?: defaultProvider

                val previewItems = state.siteEstimateMaterial.map { item ->
                    val quantityText = "${item.quantity.formatNumber()} ${item.unit}"
                    PreviewItem(
                        name = item.materialName,
                        quantityText = quantityText,
                        totalText = if (showRate) "$currency${item.total.formatNumber()}" else null
                    )
                }

                DocumentPreviewCard(
                    businessName = businessName,
                    documentId = "Estimate #EST-${state.estimateDetails?.id?.takeLast(6)?.uppercase() ?: ""}",
                    dateText = state.estimateDetails?.date?.let { formatEpochMs(it) } ?: "N/A",
                    clientName = state.clientModel?.name ?: state.estimateDetails?.clientName ?: "",
                    clientAddress = state.clientModel?.address?.ifBlank { null },
                    items = previewItems,
                    totalText = if (showRate) "$currency${state.estimateDetails?.total?.formatNumber() ?: "0"}" else null
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
                            event(EstimateDetailsEvent.GenerateEstimatePdf(currencySymbol = currency))
                        },
                        buttonColor = appColor.cardColors,
                        contentColor = Color(0xFFEF5350),
                        label = stringResource(Res.string.common_export),
                        icon = R.drawable.ic_pdf,
                        onProRequiredClick = {
                            event(EstimateDetailsEvent.ToggleProDialog(isOpen = true, featureName = "PDF Export"))
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
                        label = stringResource(Res.string.estimate_detail_btn_whatsapp),
                        icon = R.drawable.whatsapp,
                        onProRequiredClick = {
                            event(EstimateDetailsEvent.ToggleProDialog(isOpen = true, featureName = "WhatsApp Sharing"))
                        }
                    )
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space._8xl))
            }
        }
    }

    if (showShareDialog) {
        val currency = deviceInfo.currency
        ShareChoiceDialog(
            onDismiss = { showShareDialog = false },
            onSharePdf = {
                shareType = ShareType.SHARE_PDF
                event(EstimateDetailsEvent.GenerateEstimatePdf(currencySymbol = currency))
            },
            onShareText = {
                event(EstimateDetailsEvent.ShareEstimateOnWhatsapp(currencySymbol = currency))
            },
            title = stringResource(Res.string.dialog_share_title),
            description = stringResource(Res.string.dialog_share_desc_estimate)
        )
    }

    if (state.showProDialog) {
        ProFeatureDialog(
            onDismiss = {
                event(EstimateDetailsEvent.ToggleProDialog(isOpen = false))
            },
            onSeePlansClick = {
                event(EstimateDetailsEvent.ToggleProDialog(isOpen = false, featureName = "PAYWALL"))
            },
            featureName = state.proDialogFeatureName
        )
    }
}
