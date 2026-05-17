package com.karigo.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author hazratummar
 * Created on 17/05/26
 */


// ─────────────────────────────────────────────────────────────────────────────
// Karigo Adaptive Dimensions
//
// Three breakpoints matching Material 3 / Jetpack WindowSizeClass:
//
//   COMPACT  < 600dp  — standard phones (portrait)
//                        e.g. Pixel 7, Samsung A-series, Redmi
//   MEDIUM   600–840dp — large phones landscape, foldables unfolded inner
//                        e.g. Galaxy Z Fold4 inner, Pixel Fold inner, tablets 7"
//   EXPANDED > 840dp  — tablets, foldables in full expanded mode
//                        e.g. Pixel Tablet, Samsung Tab S9, Galaxy Z Fold4 full
//
// Usage in screens:
//   val d = LocalDimens.current
//   Modifier.padding(horizontal = d.screenPaddingH)
//   Modifier.height(d.cardPaddingV)
// ─────────────────────────────────────────────────────────────────────────────

data class KarigoDimens(

    // ── Screen edge padding ──────────────────────────────────────────────
    // Horizontal padding from screen edge to content.
    // Compact: 16dp (standard M3 phone margin)
    // Medium:  24dp (more breathing room on larger surfaces)
    // Expanded: 32dp (M3 large screen canonical margin)
    val screenPaddingH: Dp,

    // Top padding below status bar (before first content element)
    val screenPaddingTop: Dp,

    // Bottom padding above nav bar (last content element clearance)
    val screenPaddingBottom: Dp,

    // ── Spacing scale ─────────────────────────────────────────────────────
    // Based on 4dp base grid throughout.
    val spacingXxs: Dp,    // 2dp  — icon internal padding, tight labels
    val spacingXs: Dp,     // 4dp  — between icon and badge
    val spacingSm: Dp,     // 8dp  — between title and subtitle within card
    val spacingMd: Dp,     // 12dp — between card internal rows
    val spacingLg: Dp,     // 16dp — standard content gap
    val spacingXl: Dp,     // 20dp — section gap within screen
    val spacingXxl: Dp,    // 24dp — between major sections
    val spacing32: Dp,     // 32dp — large structural gaps

    // ── Card dimensions ───────────────────────────────────────────────────
    // Job cards, client cards, earnings cards — all share these.
    val cardPaddingH: Dp,
    val cardPaddingV: Dp,
    val cardRadius: Dp,
    val cardBorderWidth: Dp,
    val cardElevation: Dp,   // always 0 — border-only design

    // Inter-card gap (LazyColumn item spacing)
    val cardSpacing: Dp,

    // ── Stat cards (Home screen 3-up row) ─────────────────────────────────
    val statCardPaddingH: Dp,
    val statCardPaddingV: Dp,
    val statCardRadius: Dp,
    val statCardSpacing: Dp,   // gap between the three cards

    // ── Search bar ────────────────────────────────────────────────────────
    val searchBarHeight: Dp,
    val searchBarRadius: Dp,
    val searchBarPaddingH: Dp,
    val searchBarPaddingV: Dp,
    val searchIconSize: Dp,
    val searchBarBottomSpacing: Dp,   // between search bar and content below

    // ── Filter chips (Jobs / Clients sort row) ────────────────────────────
    val chipPaddingH: Dp,
    val chipPaddingV: Dp,
    val chipRadius: Dp,       // 999dp pill shape — stays constant
    val chipHeight: Dp,
    val chipSpacing: Dp,      // horizontal gap between chips

    // ── Status badges ─────────────────────────────────────────────────────
    val badgePaddingH: Dp,
    val badgePaddingV: Dp,
    val badgeRadius: Dp,      // 999dp pill shape — stays constant

    // ── Job list item internal layout ─────────────────────────────────────
    val tradeIconSize: Dp,
    val tradeIconRadius: Dp,
    val tradeIconPadding: Dp,
    val jobItemIconTextGap: Dp,

    // ── Client list ───────────────────────────────────────────────────────
    val clientAvatarSize: Dp,
    val clientAvatarRadius: Dp,   // 50% of size = circle
    val clientItemIconTextGap: Dp,

    // Outstanding banner (Clients screen top)
    val outstandingBannerPaddingH: Dp,
    val outstandingBannerPaddingV: Dp,
    val outstandingBannerRadius: Dp,

    // Outstanding amount badge (red pill on client row right)
    val outstandingBadgePaddingH: Dp,
    val outstandingBadgePaddingV: Dp,

    // ── Earnings screen ───────────────────────────────────────────────────
    val earningsCardPaddingH: Dp,
    val earningsCardPaddingV: Dp,
    val earningsCardRadius: Dp,
    val earningsCardSpacing: Dp,

    val chartContainerPaddingH: Dp,
    val chartContainerPaddingV: Dp,
    val chartContainerRadius: Dp,
    val chartBarWidth: Dp,
    val chartBarRadius: Dp,
    val chartBarSpacing: Dp,
    val chartBarMaxHeight: Dp,

    val rankBadgeSize: Dp,

    // ── Settings screen ───────────────────────────────────────────────────
    val profileCardPaddingH: Dp,
    val profileCardPaddingV: Dp,
    val profileCardRadius: Dp,

    val profileIconSize: Dp,
    val profileIconRadius: Dp,
    val profileIconPadding: Dp,

    val settingsSectionPaddingH: Dp,
    val settingsSectionPaddingV: Dp,
    val settingsSectionRadius: Dp,
    val settingsSectionGap: Dp,
    val settingsRowPaddingV: Dp,
    val settingsRowIconSize: Dp,
    val settingsRowIconTextGap: Dp,
    val settingsRowDividerIndent: Dp,

    val tradePillPaddingH: Dp,
    val tradePillPaddingV: Dp,
    val tradePillSpacing: Dp,
    val tradePillIconSize: Dp,

    val proCardPaddingH: Dp,
    val proCardPaddingV: Dp,
    val proCardRadius: Dp,
    val proCardBorderWidth: Dp,
    val proCardIconSize: Dp,

    // ── Bottom Navigation Bar ─────────────────────────────────────────────
    val bottomNavHeight: Dp,
    val bottomNavIconSize: Dp,
    val bottomNavLabelGap: Dp,

    // ── FAB ───────────────────────────────────────────────────────────────
    val fabSize: Dp,
    val fabRadius: Dp,       // rounded square — NOT circle
    val fabIconSize: Dp,
    val fabMarginEnd: Dp,
    val fabMarginBottom: Dp, // above bottom nav

    // ── Notification button (Home top right) ──────────────────────────────
    val notifButtonSize: Dp,
    val notifButtonRadius: Dp,
    val notifBadgeSize: Dp,
    val notifBadgeOffset: Dp,

    // ── Icons ─────────────────────────────────────────────────────────────
    val iconSm: Dp,
    val iconMd: Dp,
    val iconLg: Dp,

    // ── Dividers ──────────────────────────────────────────────────────────
    val dividerThickness: Dp,

    // ── Top app bar ───────────────────────────────────────────────────────
    val topBarHeight: Dp,
)

// ─────────────────────────────────────────────────────────────────────────────
// COMPACT — phones < 600dp (your primary target: Pixel, Redmi, Samsung A-series)
// All values measured from the 5 UI screenshots (501px wide reference)
// ─────────────────────────────────────────────────────────────────────────────
val CompactDimens = KarigoDimens(

    screenPaddingH          = 16.dp,
    screenPaddingTop        = 16.dp,
    screenPaddingBottom     = 16.dp,

    spacingXxs              = 2.dp,
    spacingXs               = 4.dp,
    spacingSm               = 8.dp,
    spacingMd               = 12.dp,
    spacingLg               = 16.dp,
    spacingXl               = 20.dp,
    spacingXxl              = 24.dp,
    spacing32               = 32.dp,

    cardPaddingH            = 16.dp,
    cardPaddingV            = 14.dp,
    cardRadius              = 12.dp,
    cardBorderWidth         = 0.5.dp,
    cardElevation           = 0.dp,
    cardSpacing             = 8.dp,

    statCardPaddingH        = 14.dp,
    statCardPaddingV        = 14.dp,
    statCardRadius          = 12.dp,
    statCardSpacing         = 10.dp,

    searchBarHeight         = 48.dp,
    searchBarRadius         = 12.dp,
    searchBarPaddingH       = 16.dp,
    searchBarPaddingV       = 0.dp,
    searchIconSize          = 18.dp,
    searchBarBottomSpacing  = 12.dp,

    chipPaddingH            = 16.dp,
    chipPaddingV            = 8.dp,
    chipRadius              = 999.dp,
    chipHeight              = 36.dp,
    chipSpacing             = 8.dp,

    badgePaddingH           = 10.dp,
    badgePaddingV           = 4.dp,
    badgeRadius             = 999.dp,

    tradeIconSize           = 40.dp,
    tradeIconRadius         = 10.dp,
    tradeIconPadding        = 10.dp,
    jobItemIconTextGap      = 12.dp,

    clientAvatarSize        = 40.dp,
    clientAvatarRadius      = 20.dp,
    clientItemIconTextGap   = 12.dp,
    outstandingBannerPaddingH = 16.dp,
    outstandingBannerPaddingV = 14.dp,
    outstandingBannerRadius = 12.dp,
    outstandingBadgePaddingH = 10.dp,
    outstandingBadgePaddingV = 4.dp,

    earningsCardPaddingH    = 14.dp,
    earningsCardPaddingV    = 16.dp,
    earningsCardRadius      = 12.dp,
    earningsCardSpacing     = 10.dp,
    chartContainerPaddingH  = 16.dp,
    chartContainerPaddingV  = 16.dp,
    chartContainerRadius    = 12.dp,
    chartBarWidth           = 28.dp,
    chartBarRadius          = 4.dp,
    chartBarSpacing         = 20.dp,
    chartBarMaxHeight       = 80.dp,
    rankBadgeSize           = 28.dp,

    profileCardPaddingH     = 16.dp,
    profileCardPaddingV     = 16.dp,
    profileCardRadius       = 12.dp,
    profileIconSize         = 48.dp,
    profileIconRadius       = 10.dp,
    profileIconPadding      = 12.dp,
    settingsSectionPaddingH = 16.dp,
    settingsSectionPaddingV = 14.dp,
    settingsSectionRadius   = 12.dp,
    settingsSectionGap      = 12.dp,
    settingsRowPaddingV     = 14.dp,
    settingsRowIconSize     = 20.dp,
    settingsRowIconTextGap  = 12.dp,
    settingsRowDividerIndent = 48.dp,
    tradePillPaddingH       = 12.dp,
    tradePillPaddingV       = 6.dp,
    tradePillSpacing        = 8.dp,
    tradePillIconSize       = 14.dp,
    proCardPaddingH         = 16.dp,
    proCardPaddingV         = 16.dp,
    proCardRadius           = 12.dp,
    proCardBorderWidth      = 1.5.dp,
    proCardIconSize         = 24.dp,

    bottomNavHeight         = 60.dp,
    bottomNavIconSize       = 22.dp,
    bottomNavLabelGap       = 4.dp,

    fabSize                 = 56.dp,
    fabRadius               = 16.dp,
    fabIconSize             = 24.dp,
    fabMarginEnd            = 16.dp,
    fabMarginBottom         = 16.dp,

    notifButtonSize         = 44.dp,
    notifButtonRadius       = 12.dp,
    notifBadgeSize          = 18.dp,
    notifBadgeOffset        = 6.dp,

    iconSm                  = 16.dp,
    iconMd                  = 20.dp,
    iconLg                  = 24.dp,

    dividerThickness        = 0.5.dp,

    topBarHeight            = 56.dp,
)

// ─────────────────────────────────────────────────────────────────────────────
// MEDIUM — 600–840dp
// Large phones landscape, foldables inner screen, 7" tablets
// Slightly larger padding, wider cards, richer chart
// ─────────────────────────────────────────────────────────────────────────────
val MediumDimens = KarigoDimens(

    screenPaddingH          = 24.dp,
    screenPaddingTop        = 20.dp,
    screenPaddingBottom     = 20.dp,

    spacingXxs              = 2.dp,
    spacingXs               = 4.dp,
    spacingSm               = 8.dp,
    spacingMd               = 14.dp,
    spacingLg               = 20.dp,
    spacingXl               = 24.dp,
    spacingXxl              = 32.dp,
    spacing32               = 40.dp,

    cardPaddingH            = 20.dp,
    cardPaddingV            = 16.dp,
    cardRadius              = 14.dp,
    cardBorderWidth         = 0.5.dp,
    cardElevation           = 0.dp,
    cardSpacing             = 10.dp,

    statCardPaddingH        = 18.dp,
    statCardPaddingV        = 16.dp,
    statCardRadius          = 14.dp,
    statCardSpacing         = 12.dp,

    searchBarHeight         = 52.dp,
    searchBarRadius         = 14.dp,
    searchBarPaddingH       = 20.dp,
    searchBarPaddingV       = 0.dp,
    searchIconSize          = 20.dp,
    searchBarBottomSpacing  = 16.dp,

    chipPaddingH            = 18.dp,
    chipPaddingV            = 9.dp,
    chipRadius              = 999.dp,
    chipHeight              = 40.dp,
    chipSpacing             = 10.dp,

    badgePaddingH           = 12.dp,
    badgePaddingV           = 5.dp,
    badgeRadius             = 999.dp,

    tradeIconSize           = 44.dp,
    tradeIconRadius         = 11.dp,
    tradeIconPadding        = 11.dp,
    jobItemIconTextGap      = 14.dp,

    clientAvatarSize        = 44.dp,
    clientAvatarRadius      = 22.dp,
    clientItemIconTextGap   = 14.dp,
    outstandingBannerPaddingH = 20.dp,
    outstandingBannerPaddingV = 16.dp,
    outstandingBannerRadius = 14.dp,
    outstandingBadgePaddingH = 12.dp,
    outstandingBadgePaddingV = 5.dp,

    earningsCardPaddingH    = 18.dp,
    earningsCardPaddingV    = 18.dp,
    earningsCardRadius      = 14.dp,
    earningsCardSpacing     = 12.dp,
    chartContainerPaddingH  = 20.dp,
    chartContainerPaddingV  = 18.dp,
    chartContainerRadius    = 14.dp,
    chartBarWidth           = 36.dp,
    chartBarRadius          = 5.dp,
    chartBarSpacing         = 24.dp,
    chartBarMaxHeight       = 100.dp,
    rankBadgeSize           = 32.dp,

    profileCardPaddingH     = 20.dp,
    profileCardPaddingV     = 18.dp,
    profileCardRadius       = 14.dp,
    profileIconSize         = 52.dp,
    profileIconRadius       = 12.dp,
    profileIconPadding      = 13.dp,
    settingsSectionPaddingH = 20.dp,
    settingsSectionPaddingV = 16.dp,
    settingsSectionRadius   = 14.dp,
    settingsSectionGap      = 14.dp,
    settingsRowPaddingV     = 16.dp,
    settingsRowIconSize     = 22.dp,
    settingsRowIconTextGap  = 14.dp,
    settingsRowDividerIndent = 52.dp,
    tradePillPaddingH       = 14.dp,
    tradePillPaddingV       = 7.dp,
    tradePillSpacing        = 10.dp,
    tradePillIconSize       = 16.dp,
    proCardPaddingH         = 20.dp,
    proCardPaddingV         = 18.dp,
    proCardRadius           = 14.dp,
    proCardBorderWidth      = 1.5.dp,
    proCardIconSize         = 26.dp,

    bottomNavHeight         = 64.dp,
    bottomNavIconSize       = 24.dp,
    bottomNavLabelGap       = 4.dp,

    fabSize                 = 60.dp,
    fabRadius               = 18.dp,
    fabIconSize             = 26.dp,
    fabMarginEnd            = 20.dp,
    fabMarginBottom         = 20.dp,

    notifButtonSize         = 48.dp,
    notifButtonRadius       = 13.dp,
    notifBadgeSize          = 20.dp,
    notifBadgeOffset        = 7.dp,

    iconSm                  = 18.dp,
    iconMd                  = 22.dp,
    iconLg                  = 26.dp,

    dividerThickness        = 0.5.dp,

    topBarHeight            = 60.dp,
)

// ─────────────────────────────────────────────────────────────────────────────
// EXPANDED — > 840dp
// Tablets, Galaxy Z Fold4 fully open, Pixel Tablet, Samsung Tab S9
// Two-column layouts possible. Generous margins per M3 large screen guidelines.
// ─────────────────────────────────────────────────────────────────────────────
val ExpandedDimens = KarigoDimens(

    screenPaddingH          = 32.dp,   // M3 canonical large screen margin
    screenPaddingTop        = 24.dp,
    screenPaddingBottom     = 24.dp,

    spacingXxs              = 2.dp,
    spacingXs               = 4.dp,
    spacingSm               = 8.dp,
    spacingMd               = 16.dp,
    spacingLg               = 24.dp,
    spacingXl               = 32.dp,
    spacingXxl              = 40.dp,
    spacing32               = 48.dp,

    cardPaddingH            = 24.dp,
    cardPaddingV            = 20.dp,
    cardRadius              = 16.dp,
    cardBorderWidth         = 0.5.dp,
    cardElevation           = 0.dp,
    cardSpacing             = 12.dp,

    statCardPaddingH        = 24.dp,
    statCardPaddingV        = 20.dp,
    statCardRadius          = 16.dp,
    statCardSpacing         = 16.dp,

    searchBarHeight         = 56.dp,
    searchBarRadius         = 16.dp,
    searchBarPaddingH       = 24.dp,
    searchBarPaddingV       = 0.dp,
    searchIconSize          = 22.dp,
    searchBarBottomSpacing  = 20.dp,

    chipPaddingH            = 20.dp,
    chipPaddingV            = 10.dp,
    chipRadius              = 999.dp,
    chipHeight              = 44.dp,
    chipSpacing             = 12.dp,

    badgePaddingH           = 12.dp,
    badgePaddingV           = 6.dp,
    badgeRadius             = 999.dp,

    tradeIconSize           = 48.dp,
    tradeIconRadius         = 12.dp,
    tradeIconPadding        = 12.dp,
    jobItemIconTextGap      = 16.dp,

    clientAvatarSize        = 48.dp,
    clientAvatarRadius      = 24.dp,
    clientItemIconTextGap   = 16.dp,
    outstandingBannerPaddingH = 24.dp,
    outstandingBannerPaddingV = 18.dp,
    outstandingBannerRadius = 16.dp,
    outstandingBadgePaddingH = 12.dp,
    outstandingBadgePaddingV = 6.dp,

    earningsCardPaddingH    = 24.dp,
    earningsCardPaddingV    = 22.dp,
    earningsCardRadius      = 16.dp,
    earningsCardSpacing     = 16.dp,
    chartContainerPaddingH  = 24.dp,
    chartContainerPaddingV  = 22.dp,
    chartContainerRadius    = 16.dp,
    chartBarWidth           = 44.dp,
    chartBarRadius          = 6.dp,
    chartBarSpacing         = 28.dp,
    chartBarMaxHeight       = 120.dp,
    rankBadgeSize           = 36.dp,

    profileCardPaddingH     = 24.dp,
    profileCardPaddingV     = 22.dp,
    profileCardRadius       = 16.dp,
    profileIconSize         = 60.dp,
    profileIconRadius       = 14.dp,
    profileIconPadding      = 15.dp,
    settingsSectionPaddingH = 24.dp,
    settingsSectionPaddingV = 18.dp,
    settingsSectionRadius   = 16.dp,
    settingsSectionGap      = 16.dp,
    settingsRowPaddingV     = 18.dp,
    settingsRowIconSize     = 24.dp,
    settingsRowIconTextGap  = 16.dp,
    settingsRowDividerIndent = 56.dp,
    tradePillPaddingH       = 16.dp,
    tradePillPaddingV       = 8.dp,
    tradePillSpacing        = 12.dp,
    tradePillIconSize       = 18.dp,
    proCardPaddingH         = 24.dp,
    proCardPaddingV         = 22.dp,
    proCardRadius           = 16.dp,
    proCardBorderWidth      = 1.5.dp,
    proCardIconSize         = 28.dp,

    // On tablets the nav is a NavigationRail on the side, not a bottom bar.
    // These values are kept for phones and used as fallback rail icon sizing.
    bottomNavHeight         = 72.dp,
    bottomNavIconSize       = 26.dp,
    bottomNavLabelGap       = 4.dp,

    fabSize                 = 64.dp,
    fabRadius               = 20.dp,
    fabIconSize             = 28.dp,
    fabMarginEnd            = 32.dp,
    fabMarginBottom         = 32.dp,

    notifButtonSize         = 52.dp,
    notifButtonRadius       = 14.dp,
    notifBadgeSize          = 22.dp,
    notifBadgeOffset        = 8.dp,

    iconSm                  = 20.dp,
    iconMd                  = 24.dp,
    iconLg                  = 28.dp,

    dividerThickness        = 0.5.dp,

    topBarHeight            = 64.dp,
)

// ─────────────────────────────────────────────────────────────────────────────
// Material 3 Shapes
// Wired to Compact values — shape radii do NOT scale with window size.
// Shape tokens are semantic (extraSmall = badge, medium = card, etc).
// ─────────────────────────────────────────────────────────────────────────────
val KarigoShapes = Shapes(
    // extraSmall → status badges, filter chips, trade pills (full pill)
    extraSmall  = RoundedCornerShape(999.dp),
    // small      → tight inner containers, avatar badges
    small       = RoundedCornerShape(8.dp),
    // medium     → all cards: job, client, stat, earnings
    medium      = RoundedCornerShape(12.dp),
    // large      → search bar, settings section blocks, chart container
    large       = RoundedCornerShape(12.dp),
    // extraLarge → FAB (rounded square), profile icon bg, bottom sheet
    extraLarge  = RoundedCornerShape(16.dp),
)