package com.karigojobs.ui.theme

import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────────────────────────────────────
// Karigojobs Color System
// All values extracted pixel-by-pixel from the 5 UI screens.
// Material 3 dark-first. Never reference these directly in screens —
// always use MaterialTheme.colorScheme.* or the semantic aliases below.
// ─────────────────────────────────────────────────────────────────────────────

// ── Primary / Accent — Teal ───────────────────────────────────────────────
val Primary              = Color(0xFF00D4AA)   // FAB, active nav, chart bar, links
val PrimaryDim           = Color(0xFF00A884)   // pressed/ripple state
val OnPrimary            = Color(0xFF000000)   // text on teal surfaces
val PrimaryContainer     = Color(0xFF0D2420)   // Go Pro card background
val OnPrimaryContainer   = Color(0xFF00D4AA)   // Go Pro card text + icon

// ── Backgrounds ───────────────────────────────────────────────────────────
val Background           = Color(0xFF0A0A0A)   // root scaffold — slightly warm black
val OnBackground         = Color(0xFF000000)   // used for light theme on-bg
val BackgroundAlt        = Color(0xFF0F0F0F)   // nav bar background
val Surface              = Color(0xFF1A1A1A)   // all cards: job, client, stat, earnings
val SurfaceVariant       = Color(0xFF1C1C1C)   // search bar fill, input fields
val SurfaceHigh          = Color(0xFF222222)   // settings rows, chart container
val SurfaceOverlay       = Color(0xFF2A2A2A)   // ripple / hover state
val SurfaceTealTint      = Color(0xFF1E3A35)   // profile  background (Settings)
val NavBackground        = Color(0xFF0F0F0F)   // bottom nav bar surface
val ModalBackGround      = Color(0xFF141414)
val Scrim                = Color(0xCC000000)   // modal scrim

// ── Borders / Dividers ────────────────────────────────────────────────────
val OutlineVariant       = Color(0xFF2C2C2C)   // card border, trade pill border
val Outline              = Color(0xFF3A3A3A)   // search bar border, stronger dividers
val Divider              = Color(0xFF2A2A2A)   // list item dividers

// ── Text hierarchy ────────────────────────────────────────────────────────
val TextPrimary          = Color(0xFFFFFFFF)   // titles, amounts, names
val TextSecondary        = Color(0xFF9E9E9E)   // client name, date, phone, subtitle
val TextTertiary         = Color(0xFF616161)   // section labels, captions, counts
val TextDisabled         = Color(0xFF424242)   // placeholder text

// ── Status badge semantic colors ──────────────────────────────────────────
val StatusInProgress        = Color(0xFF00D4AA)
val StatusInProgressSurface = Color(0xFF0D2E28)
val OnStatusInProgress      = Color(0xFF00D4AA)

val StatusPending           = Color(0xFFFF8F00)
val StatusPendingSurface    = Color(0xFF2E2000)
val OnStatusPending         = Color(0xFFFF8F00)

val StatusDone              = Color(0xFF4CAF50)
val StatusDoneSurface       = Color(0xFF0D2E10)
val OnStatusDone            = Color(0xFF4CAF50)

val StatusInvoiced          = Color(0xFF7986CB)
val StatusInvoicedSurface   = Color(0xFF1A1C3A)
val OnStatusInvoiced        = Color(0xFF7986CB)

val StatusPaid              = Color(0xFFB0BEC5)
val StatusPaidSurface       = Color(0xFF263238)
val OnStatusPaid            = Color(0xFFB0BEC5)

// ── Financial amount colors ───────────────────────────────────────────────
val AmountUnpaid         = Color(0xFFE53935)
val AmountDone           = Color(0xFF4CAF50)
val AmountBilled         = Color(0xFFFFFFFF)

// ── Error / Danger ────────────────────────────────────────────────────────
val Error                = Color(0xFFE53935)
val ErrorContainer       = Color(0xFF2E1010)
val OnError              = Color(0xFFFFFFFF)
val OnErrorContainer     = Color(0xFFE53935)

// ── Chart ─────────────────────────────────────────────────────────────────
val ChartBarInactive     = Color(0xFF2A2A2A)
val ChartBarActive       = Color(0xFF00D4AA)

// ── Earnings rank badges ──────────────────────────────────────────────────
val RankGold             = Color(0xFFFFD54F)
val RankSilver           = Color(0xFFB0BEC5)
val RankBronze           = Color(0xFFFF7043)

// ── Navigation ────────────────────────────────────────────────────────────
val NavActive            = Color(0xFF00D4AA)
val NavInactive          = Color(0xFFa1a1aa)

// ── System UI ─────────────────────────────────────────────────────────────
val NotifBadge           = Color(0xFFE53935)
val OnNotifBadge         = Color(0xFFFFFFFF)

// ── Light theme tokens (kept for completeness / preview) ──────────────────
val LightBackground          = Color(0xFFfafafa)
val LightSurface             = Color(0xFFFFFFFF)
val LightSurfaceVariant      = Color(0xFFEEEEEE)
val LightOnSurfaceVariant    = Color(0xFF424242)
val LightOutline             = Color(0xFFBDBDBD)
val LightOutlineVariant      = Color(0xFFE0E0E0)
val LightPrimaryContainer    = Color(0xFFB2F0E6)
val LightOnPrimaryContainer  = Color(0xFF003328)
val LightErrorContainer      = Color(0xFFFFDAD6)

// ── Karigojobs Brand Palette ──────────────────────────────────────────────────


val KarigojobsBg = Color(0xFF0A0A0A)
val KarigojobsCard = Color(0xFF1e1e1e)
val KarigojobsRaised = Color(0xFF181818)
val KarigojobsIconColor = Color(0xFF43e5cc)
val KarigojobsAccent = Color(0xFF00D4A0)
val KarigojobsWarning = Color(0xFFF5A623)
val whatsAppColor = Color(0xFF25D366)
val KarigojobsError = Color(0xFFE05555)
val KarigojobsText = Color(0xFFE8E6E0)
val KarigojobsText2 = Color(0xFFa1a1aa)
val KarigojobsText3 = Color(0xFF71717a)
val KarigojobsBorder = Color(0xFF242424)

val KarigoSelectedCardColor = Color(0xFF142e2b)