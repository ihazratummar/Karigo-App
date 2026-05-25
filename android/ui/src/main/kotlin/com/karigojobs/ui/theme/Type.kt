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
// KarigojobsTypography — data class
//
// Named by ROLE (what the text IS), not by size (not "text14Bold").
// Every field maps to a real element seen in the 5 screens.
//
// Three instances: CompactTypography / MediumTypography / ExpandedTypography
// Resolved by Theme.kt based on WindowSizeClass.
//
// Usage:
//   val t = LocalAppTypography.current
//   Text(text = "Hazrat Ummar", style = t.screenTitle)
// ─────────────────────────────────────────────────────────────────────────────
data class KarigojobsTypography(

    // ── Screen titles ─────────────────────────────────────────────────────
    // "All Jobs", "Clients", "Earnings", "Settings" — top of each screen
    val screenTitle: TextStyle,

    // ── User greeting ─────────────────────────────────────────────────────
    // "Hazrat Ummar" — the large name on Dashboard
    val greetingName: TextStyle,

    // "Good afternoon, Hazrat Ummar" — subtitle above the name
    val greetingSubtitle: TextStyle,

    // ── Section headers ───────────────────────────────────────────────────
    // "Recent Jobs", "Monthly Revenue", "Top Clients"
    val sectionHeader: TextStyle,

    // Section count / filter label: "6 total", "6 clients", "5 months"
    val sectionCount: TextStyle,

    // ── Stat cards ────────────────────────────────────────────────────────
    // "BILLED", "UNPAID", "DONE" — ALL CAPS tracked labels above the amounts
    val statLabel: TextStyle,

    // "₹9,010" — the large billed amount (white)
    val statAmountNormal: TextStyle,

    // "₹4,045" — the highlighted unpaid amount (larger, red)
    val statAmountHighlighted: TextStyle,

    // ── Job list items ────────────────────────────────────────────────────
    // "Bathroom leak repair" — job title
    val jobTitle: TextStyle,

    // "Rajesh Kumar" — client name below job title
    val jobClientName: TextStyle,

    // "14 May · 2:00 pm" — date/time row
    val jobDate: TextStyle,

    // "₹1,505" — job amount right-aligned
    val jobAmount: TextStyle,

    // ── Status badges ─────────────────────────────────────────────────────
    // "In Progress", "Pending", "Done", "Invoiced", "Paid"
    val statusBadge: TextStyle,

    // ── Search bar ────────────────────────────────────────────────────────
    // "Search jobs or clients..." placeholder + input
    val searchInput: TextStyle,

    // ── Filter chips ──────────────────────────────────────────────────────
    // "All", "Active", "Pending", "Done", "Paid"
    val filterChip: TextStyle,

    // ── Client list ───────────────────────────────────────────────────────
    // "Amit Patel" — client name (larger than job screen client name)
    val clientName: TextStyle,

    // "+91 76543 21098" — phone number
    val clientPhone: TextStyle,

    // "1 jobs · ₹720 billed" — meta row below client
    val clientMeta: TextStyle,

    // "₹4,215" — outstanding amount badge
    val clientOutstandingBadge: TextStyle,

    // "Total Outstanding" — label above big outstanding amount
    val outstandingLabel: TextStyle,

    // "₹7,540" — total outstanding amount
    val outstandingAmount: TextStyle,

    // ── Earnings screen ───────────────────────────────────────────────────
    // "REVENUE", "AVG PER JOB" — ALL CAPS tracked card labels
    val earningsCardLabel: TextStyle,

    // "₹2,13,000" — large revenue figure
    val earningsCardAmount: TextStyle,

    // "70 jobs", "this year" — sub-label below amount
    val earningsCardSublabel: TextStyle,

    // Chart month labels: "Jan", "Feb", "May"
    val chartMonthLabel: TextStyle,

    // Chart highlighted amount below active bar: "₹24,500"
    val chartBarAmount: TextStyle,

    // "1", "2", "3" in rank badges
    val rankBadgeNumber: TextStyle,

    // ── Settings screen ───────────────────────────────────────────────────
    // "Hazrat Ummar" — profile name in settings card
    val profileName: TextStyle,

    // "42 Andheri West, Mumbai 400058" — address
    val profileAddress: TextStyle,

    // "MY TRADES", "APPEARANCE", "JOB DEFAULTS", "PRO PLAN" — ALL CAPS sections
    val settingsSectionLabel: TextStyle,

    // "Dark Mode", "Default Labour Rate" — setting item title
    val settingsItemTitle: TextStyle,

    // "Easier on eyes outdoors", "₹450/hour" — setting item subtitle
    val settingsItemSubtitle: TextStyle,

    // "Go Pro" — Pro plan card title
    val proCardTitle: TextStyle,

    // "Unlimited clients, cloud sync, custom branding"
    val proCardSubtitle: TextStyle,

    // ── Nav bar labels ────────────────────────────────────────────────────
    // "Home", "Jobs", "Clients", "Earnings", "Settings"
    val navLabel: TextStyle,

    // ── Action links ──────────────────────────────────────────────────────
    // "See all", "Change" — teal text links
    val actionLink: TextStyle,
)

// ─────────────────────────────────────────────────────────────────────────────
// Helper: builds Material 3 Typography from KarigojobsTypography
// Called by Theme.kt → MaterialTheme(typography = ...)
// ─────────────────────────────────────────────────────────────────────────────
fun KarigojobsTypography.toMaterial3Typography() = Typography(
    // M3 roles mapped to our semantic styles
    displayLarge   = greetingName.copy(fontSize = (greetingName.fontSize.value * 1.4f).sp),
    displayMedium  = greetingName,
    displaySmall   = screenTitle,
    headlineLarge  = statAmountHighlighted,
    headlineMedium = statAmountNormal,
    headlineSmall  = screenTitle,
    titleLarge     = greetingName,
    titleMedium    = sectionHeader,
    titleSmall     = jobTitle,
    bodyLarge      = searchInput,
    bodyMedium     = jobClientName,
    bodySmall      = jobDate,
    labelLarge     = actionLink,
    labelMedium    = filterChip,
    labelSmall     = statLabel,
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