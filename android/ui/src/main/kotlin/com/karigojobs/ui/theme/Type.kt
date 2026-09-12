package com.karigojobs.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.karigojobs.app.android.ui.R


/**
 * @author hazratummar
 * Created on 17/05/26
 */



val InterFontFamily = FontFamily(
    Font(R.font.inter_regular,  FontWeight.Normal),
    Font(R.font.inter_medium,   FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold,     FontWeight.Bold),
)


// ─────────────────────────────────────────────────────────────────────────────
// Builder — creates a KarigojobsTypography for a given base scale factor
// compactFactor = 1.0, mediumFactor = 1.1, expandedFactor = 1.2
// This ensures every sp value scales proportionally, not hardcoded per size.
// ─────────────────────────────────────────────────────────────────────────────
private fun buildTypography(scale: Float): Typography {

    fun s(size: Float) = (size * scale).sp

    return Typography(

        displayLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = s(32f),
            lineHeight = s(40f),
            letterSpacing = (-0.5).sp
        ),

        displayMedium = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = s(28f),
            lineHeight = s(36f)
        ),

        headlineLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = s(24f),
            lineHeight = s(30f)
        ),

        headlineMedium = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = s(20f),
            lineHeight = s(28f)
        ),

        titleLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = s(18f),
            lineHeight = s(24f)
        ),

        titleMedium = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = s(16f),
            lineHeight = s(24f)
        ),

        titleSmall = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = s(14f),
            lineHeight = s(20f)
        ),

        bodyLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = s(16f),
            lineHeight = s(24f)
        ),

        bodyMedium = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = s(14f),
            lineHeight = s(20f)
        ),

        bodySmall = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = s(12f),
            lineHeight = s(16f)
        ),

        labelLarge = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = s(14f),
            lineHeight = s(20f)
        ),

        labelMedium = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = s(12f),
            lineHeight = s(16f)
        ),

        labelSmall = TextStyle(
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = s(10f),
            lineHeight = s(14f),
            letterSpacing = 1.sp
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Three resolved instances
//
// scale 1.0  = Compact  (base sizes measured from 501px-wide phone screenshots)
// scale 1.08 = Medium   (foldable inner / large phone landscape)
// scale 1.15 = Expanded (tablet / foldable fully open)
//
// Why proportional scaling and not hardcoded?
// Because on a tablet the user is further from the screen — text that was
// readable at 13sp on a phone held 25cm away needs to be ~15sp on a tablet
// viewed at 35cm. The scale factor encodes that viewing distance adjustment.
// ─────────────────────────────────────────────────────────────────────────────
val CompactTypography  = buildTypography(scale = 1.00f)
val MediumTypography   = buildTypography(scale = 1.08f)
val ExpandedTypography = buildTypography(scale = 1.15f)