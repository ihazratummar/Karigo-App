package com.karigojobs.ui

import android.content.Context
import android.content.Intent
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.TradeType
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_greeting_afternoon
import karigojobs.shared.resources.generated.resources.common_greeting_evening
import karigojobs.shared.resources.generated.resources.common_greeting_morning
import karigojobs.shared.resources.generated.resources.common_greeting_night
import org.jetbrains.compose.resources.StringResource
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale


/**
 * @author hazratummar
 * Created on 21/05/26
 */




fun TradeType.icon () : Int {
    return when(this){
        TradeType.PLUMBER -> R.drawable.plumber
        TradeType.ELECTRICIAN -> R.drawable.electrician
        TradeType.CARPENTER ->  R.drawable.carpenter
        TradeType.PAINTER -> R.drawable.painter
        TradeType.MASON -> R.drawable.mason
        TradeType.GRILL -> R.drawable.grill
        TradeType.AC_TECHNICIAN -> R.drawable.ac_technician
        TradeType.APPLIANCE_REPAIR -> R.drawable.appliance_repair
        TradeType.WATERPROOFING -> R.drawable.water_proffing
        TradeType.PEST_CONTROL -> R.drawable.pest_control
        TradeType.CAR_BIKE_MECHANIC -> R.drawable.mechanic
        TradeType.CCTV_SECURITY -> R.drawable.cctv
        TradeType.SOLAR_INSTALLER -> R.drawable.solar
        TradeType.ALUMINIUM_UPVC -> R.drawable.alumuniam
        TradeType.CIVIL_CONTRACTOR -> R.drawable.civil_contractor
        TradeType.BORE_WELL -> R.drawable.bore_well_water
        TradeType.GAS_LPG_FITTER -> R.drawable.gas_lpg_fitter
        TradeType.NETWORK_SUPPORT -> R.drawable.network_it_support
    }
}

fun TradeType.color() : Color {
    return when(this){
        TradeType.PLUMBER -> Color(0xFF3b82f6)
        TradeType.ELECTRICIAN -> Color(0xFFed990c)
        TradeType.CARPENTER -> Color(0xFF8b5cf6)
        TradeType.PAINTER -> Color(0xFFd6438b)
        TradeType.MASON -> Color(0xFFef4544)
        TradeType.GRILL -> Color(0xFF6366f0)
        TradeType.AC_TECHNICIAN -> Color(0xFF09b6d4)
        TradeType.APPLIANCE_REPAIR -> Color(0xFF15a998)
        TradeType.WATERPROOFING -> Color(0xFF1195d0)
        TradeType.PEST_CONTROL -> Color(0xFF10b981)
        TradeType.CAR_BIKE_MECHANIC -> Color(0xFFf17016)
        TradeType.CCTV_SECURITY -> Color(0xFF64748b)
        TradeType.SOLAR_INSTALLER -> Color(0xFFca9c0c)
        TradeType.ALUMINIUM_UPVC -> Color(0xFF94a3b8)
        TradeType.CIVIL_CONTRACTOR -> Color(0xFF69635f)
        TradeType.BORE_WELL -> Color(0xFF0d9488)
        TradeType.GAS_LPG_FITTER -> Color(0xFFdc2626)
        TradeType.NETWORK_SUPPORT -> Color(0xFF234ba7)
    }
}

enum class ShareType {
    PRINT, SHARE_PDF
}

fun formatEpochMs(epochMs: Long): String {
    if (epochMs == 0L) return "N/A"
    return try {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        sdf.format(Date(epochMs))
    } catch (e: Exception) {
        "N/A"
    }
}

fun sharePdfFile(context: Context, html: String, documentTitle: String) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
    if (printManager == null) {
        Toast.makeText(context, "Print service is not available on this device", Toast.LENGTH_LONG).show()
        return
    }
    
    val webView = WebView(context).apply {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                try {
                    val printAdapter = view?.createPrintDocumentAdapter(documentTitle) ?: return
                    val printAttributes = PrintAttributes.Builder()
                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                        .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                        .build()

                    val pdfPrint = android.print.PdfPrint(printAttributes)
                    pdfPrint.print(printAdapter, context.cacheDir, "$documentTitle.pdf") { file ->
                        if (file != null) {
                            try {
                                val authority = "${context.packageName}.fileprovider"
                                val uri = FileProvider.getUriForFile(context, authority, file)
                                
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "application/pdf"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    `package` = "com.whatsapp"
                                }
                                context.startActivity(shareIntent)
                            } catch (e: Exception) {
                                try {
                                    val authority = "${context.packageName}.fileprovider"
                                    val uri = FileProvider.getUriForFile(context, authority, file)
                                    val chooserIntent = Intent.createChooser(
                                        Intent(Intent.ACTION_SEND).apply {
                                            type = "application/pdf"
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }, 
                                        "Share PDF"
                                    )
                                    context.startActivity(chooserIntent)
                                } catch (ex: Exception) {
                                    Toast.makeText(context, "Sharing failed: ${ex.localizedMessage}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Failed to render PDF document", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "PDF layout error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    webView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null)
}



fun getGreeting() : StringResource {
    val hour = LocalTime.now().hour

    return when(hour){
        in 5..11 -> Res.string.common_greeting_morning
        in 12..16 -> Res.string.common_greeting_afternoon
        in 17..20 -> Res.string.common_greeting_evening
        else -> Res.string.common_greeting_night
    }
}


fun String.localizeDigits(): String {
    val language = Locale.getDefault().language
    return when (language) {
        "hi", "mr" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x0966).toChar() else char
        }.joinToString("")
        "bn" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x09E6).toChar() else char
        }.joinToString("")
        "ml" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x0D66).toChar() else char
        }.joinToString("")
        "ta" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x0BE6).toChar() else char
        }.joinToString("")
        "te" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x0C66).toChar() else char
        }.joinToString("")
        "ur" -> this.map { char ->
            if (char in '0'..'9') (char - '0' + 0x06F0).toChar() else char
        }.joinToString("")
        else -> this
    }
}

object NumberUtils {
    private val formatter : NumberFormat
        get() = NumberFormat.getNumberInstance(Locale.getDefault())

    fun format(
        number: Number,
        maxFractionDigits: Int = 2,
        minFractionDigit: Int = 0
    ) : String {
        return formatter.apply {
            maximumFractionDigits = maxFractionDigits
            minimumFractionDigits = minFractionDigit
            isGroupingUsed = true
        }.format(number).localizeDigits()
    }
}

fun Number.toLocaleString(): String =
    NumberFormat.getNumberInstance(Locale.getDefault()).format(this).localizeDigits()