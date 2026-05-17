package com.karigo.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.karigo.app.android.ui.R


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
// KarigoTypography — data class
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
data class KarigoTypography(

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
// Helper: builds Material 3 Typography from KarigoTypography
// Called by Theme.kt → MaterialTheme(typography = ...)
// ─────────────────────────────────────────────────────────────────────────────
fun KarigoTypography.toMaterial3Typography() = Typography(
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
// Shared line height / letter spacing constants
// These do NOT scale — only fontSize scales between breakpoints
// ─────────────────────────────────────────────────────────────────────────────
private val LH_TIGHT    = 1.15f   // headings, large amounts
private val LH_NORMAL   = 1.50f   // body text, subtitles
private val LH_RELAXED  = 1.60f   // long descriptions, settings subtitles

private val LS_NORMAL   = 0.sp
private val LS_WIDE     = 0.5.sp
private val LS_CAPS     = 1.2.sp  // ALL CAPS tracked labels: BILLED, MY TRADES

// ─────────────────────────────────────────────────────────────────────────────
// Builder — creates a KarigoTypography for a given base scale factor
// compactFactor = 1.0, mediumFactor = 1.1, expandedFactor = 1.2
// This ensures every sp value scales proportionally, not hardcoded per size.
// ─────────────────────────────────────────────────────────────────────────────
private fun buildTypography(scale: Float): KarigoTypography {

    fun sp(base: Float): TextUnit = (base * scale).sp

    return KarigoTypography(

        // ── Screen title — "All Jobs", "Clients", "Earnings", "Settings"
        // Measured: 20sp, Bold, tight line height
        screenTitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(20f),
            lineHeight    = sp(20f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Greeting name — "Hazrat Ummar" (Home screen large name)
        // Measured: 24sp, Bold
        greetingName = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(24f),
            lineHeight    = sp(24f * LH_TIGHT),
            letterSpacing = (-0.5).sp,
        ),

        // ── Greeting subtitle — "Good afternoon, Hazrat Ummar"
        // Measured: 12sp, Regular, TextSecondary colour
        greetingSubtitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(12f),
            lineHeight    = sp(12f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Section header — "Recent Jobs", "Monthly Revenue", "Top Clients"
        // Measured: 16sp, SemiBold
        sectionHeader = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(16f),
            lineHeight    = sp(16f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Section count — "6 total", "5 months"
        // Measured: 13sp, Regular, TextTertiary
        sectionCount = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextTertiary,
        ),

        // ── Stat card label — "BILLED", "UNPAID", "DONE"
        // Measured: 10sp, SemiBold, ALL CAPS, wide tracking, TextTertiary
        statLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(10f),
            lineHeight    = sp(14f),
            letterSpacing = LS_CAPS,
            color         = TextTertiary,
        ),

        // ── Stat amount normal — "₹9,010" (Billed, white)
        // Measured: 24sp, Bold
        statAmountNormal = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(24f),
            lineHeight    = sp(24f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Stat amount highlighted — "₹4,045" (Unpaid, red, slightly larger)
        // Measured: 26sp, Bold — visually larger than the billed card
        statAmountHighlighted = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(26f),
            lineHeight    = sp(26f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
            color         = AmountUnpaid,
        ),

        // ── Job title — "Bathroom leak repair"
        // Measured: 15sp, SemiBold
        jobTitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(15f),
            lineHeight    = sp(15f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
        ),

        // ── Job client name — "Rajesh Kumar" (small, below job title)
        // Measured: 13sp, Regular, TextSecondary
        jobClientName = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Job date — "14 May · 2:00 pm"
        // Measured: 12sp, Regular, TextSecondary
        jobDate = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(12f),
            lineHeight    = sp(12f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Job amount — "₹1,505", "₹4,215"
        // Measured: 14sp, SemiBold, white
        jobAmount = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(14f),
            lineHeight    = sp(14f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Status badge — "In Progress", "Pending", "Done"
        // Measured: 11sp, Medium — fits inside the pill badges
        statusBadge = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Medium,
            fontSize      = sp(11f),
            lineHeight    = sp(14f),
            letterSpacing = LS_NORMAL,
        ),

        // ── Search input — placeholder and typed text
        // Measured: 14sp, Regular
        searchInput = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(14f),
            lineHeight    = sp(14f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
        ),

        // ── Filter chip — "All", "Active", "Pending"
        // Measured: 13sp, Medium
        filterChip = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Medium,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Client name — "Amit Patel" (larger context, own screen)
        // Measured: 15sp, SemiBold
        clientName = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(15f),
            lineHeight    = sp(15f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
        ),

        // ── Client phone — "+91 76543 21098"
        // Measured: 13sp, Regular, TextSecondary
        clientPhone = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Client meta row — "1 jobs · ₹720 billed"
        // Measured: 12sp, Regular, TextSecondary
        clientMeta = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(12f),
            lineHeight    = sp(12f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Outstanding badge — "₹4,215" pill on client row
        // Measured: 12sp, SemiBold
        clientOutstandingBadge = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(12f),
            lineHeight    = sp(14f),
            letterSpacing = LS_NORMAL,
        ),

        // ── Outstanding label — "Total Outstanding"
        // Measured: 11sp, Regular, TextSecondary
        outstandingLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(11f),
            lineHeight    = sp(14f),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Outstanding amount — "₹7,540"
        // Measured: 22sp, Bold, AmountUnpaid (red)
        outstandingAmount = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(22f),
            lineHeight    = sp(22f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
            color         = AmountUnpaid,
        ),

        // ── Earnings card label — "REVENUE", "AVG PER JOB"
        // Measured: 10sp, SemiBold, ALL CAPS, TextTertiary
        earningsCardLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(10f),
            lineHeight    = sp(14f),
            letterSpacing = LS_CAPS,
            color         = TextTertiary,
        ),

        // ── Earnings card amount — "₹2,13,000", "₹3,043"
        // Measured: 26sp, Bold
        earningsCardAmount = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(26f),
            lineHeight    = sp(26f * LH_TIGHT),
            letterSpacing = LS_NORMAL,
        ),

        // ── Earnings sublabel — "70 jobs", "this year"
        // Measured: 13sp, Regular, TextSecondary
        earningsCardSublabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Chart month label — "Jan", "Feb", "May"
        // Measured: 11sp, Regular, TextSecondary
        chartMonthLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(11f),
            lineHeight    = sp(14f),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Chart bar amount — "₹24,500" below active bar
        // Measured: 12sp, SemiBold, Primary (teal)
        chartBarAmount = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(12f),
            lineHeight    = sp(14f),
            letterSpacing = LS_NORMAL,
            color         = Primary,
        ),

        // ── Rank badge number — "1", "2", "3"
        // Measured: 13sp, Bold
        rankBadgeNumber = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Bold,
            fontSize      = sp(13f),
            lineHeight    = sp(16f),
            letterSpacing = LS_NORMAL,
        ),

        // ── Profile name — "Hazrat Ummar" (Settings)
        // Measured: 16sp, SemiBold
        profileName = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(16f),
            lineHeight    = sp(16f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
        ),

        // ── Profile address — "42 Andheri West, Mumbai 400058"
        // Measured: 13sp, Regular, TextSecondary
        profileAddress = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(13f),
            lineHeight    = sp(13f * LH_RELAXED),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Settings section label — "MY TRADES", "APPEARANCE", "JOB DEFAULTS"
        // Measured: 10sp, SemiBold, ALL CAPS, wide tracking, TextTertiary
        settingsSectionLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(10f),
            lineHeight    = sp(14f),
            letterSpacing = LS_CAPS,
            color         = TextTertiary,
        ),

        // ── Settings item title — "Dark Mode", "Default Labour Rate"
        // Measured: 15sp, Medium
        settingsItemTitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Medium,
            fontSize      = sp(15f),
            lineHeight    = sp(15f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
        ),

        // ── Settings item subtitle — "Easier on eyes outdoors", "₹450/hour"
        // Measured: 12sp, Regular, TextSecondary
        settingsItemSubtitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(12f),
            lineHeight    = sp(12f * LH_RELAXED),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Pro card title — "Go Pro"
        // Measured: 15sp, SemiBold, Primary (teal)
        proCardTitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.SemiBold,
            fontSize      = sp(15f),
            lineHeight    = sp(15f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = Primary,
        ),

        // ── Pro card subtitle
        // Measured: 12sp, Regular, TextSecondary
        proCardSubtitle = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(12f),
            lineHeight    = sp(12f * LH_RELAXED),
            letterSpacing = LS_NORMAL,
            color         = TextSecondary,
        ),

        // ── Nav label — "Home", "Jobs", "Clients"
        // Measured: 10sp, Regular
        navLabel = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Normal,
            fontSize      = sp(10f),
            lineHeight    = sp(12f),
            letterSpacing = LS_NORMAL,
        ),

        // ── Action link — "See all", "Change"
        // Measured: 14sp, Medium, Primary (teal)
        actionLink = TextStyle(
            fontFamily    = InterFontFamily,
            fontWeight    = FontWeight.Medium,
            fontSize      = sp(14f),
            lineHeight    = sp(14f * LH_NORMAL),
            letterSpacing = LS_NORMAL,
            color         = Primary,
        ),
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