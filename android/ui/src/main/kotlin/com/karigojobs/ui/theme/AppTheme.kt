package com.karigojobs.ui.theme

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.window.core.layout.WindowSizeClass


/**
 * @author hazratummar
 * Created on 17/05/26
 */


// ─────────────────────────────────────────────────────────────────────────────
// Color schemes
// ─────────────────────────────────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = StatusInvoiced,
    onSecondary = OnStatusInvoiced,
    secondaryContainer = StatusInvoicedSurface,
    onSecondaryContainer = OnStatusInvoiced,
    tertiary = StatusPending,
    onTertiary = OnStatusPending,
    tertiaryContainer = StatusPendingSurface,
    onTertiaryContainer = OnStatusPending,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = Outline,
    outlineVariant = OutlineVariant,
    inverseSurface = TextPrimary,
    inverseOnSurface = Surface,
    inversePrimary = PrimaryDim,
    surfaceTint = Primary,
    scrim = Scrim,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDim,
    onPrimary = OnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = Error,
    background = LightBackground,
    onBackground = OnBackground,
    surface = LightSurface,
    onSurface = OnBackground,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    scrim = Scrim,
)


@Composable
fun KarigojobsTheme(
    windowSizeClass: WindowSizeClass,
    // Dark-first: the entire design was built for dark mode.
    // Outdoor field workers — plumbers on rooftops, electricians in engine rooms.
    // Only pass darkTheme = false for explicit light-mode testing or previews.
    darkTheme: Boolean = true,
    appCurrencySymbol: String? = null,
    isPro: Boolean = false,
    content: @Composable () -> Unit,
) {
    // ── Resolve adaptive tokens from window size ───────────────────────────
    val dimens = remember(windowSizeClass) {
        when {
            windowSizeClass.isWidthAtLeastBreakpoint(840) -> ExpandedAppDimens
            windowSizeClass.isWidthAtLeastBreakpoint(600) -> MediumAppDimens
            else -> CompactAppDimens
        }
    }

    val typography = remember(windowSizeClass) {
        when {
            windowSizeClass.isWidthAtLeastBreakpoint(840) -> ExpandedTypography
            windowSizeClass.isWidthAtLeastBreakpoint(600) -> MediumTypography
            else -> CompactTypography
        }
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val karigoColors = if (darkTheme) KarigoDarkColor else KarigoLightColor
    val deviceInfo = remember(appCurrencySymbol) { getDeviceInfo(appCurrencySymbol) }

    // ── Provide tokens + apply MaterialTheme ──────────────────────────────
    CompositionLocalProvider(
        LocalDimens provides dimens,
        LocalAppTypography provides typography,
        LocalKarigoColors provides karigoColors,
        LocalDeviceInfo provides deviceInfo,
        LocalIsPro provides isPro
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = KarigojobsShapes,
            content = content
        )
    }
}


@Composable
fun KarigojobsThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    KarigojobsTheme(
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
        darkTheme = darkTheme,
        content = content
    )
}


val LocalDimens = compositionLocalOf { CompactAppDimens }
val LocalAppTypography = compositionLocalOf { CompactTypography }
val LocalIsPro = compositionLocalOf { false }

val dimens
    @Composable
    get() = LocalDimens.current

val deviceInfo
    @Composable
    get() = LocalDeviceInfo.current

val appColor
    @Composable
    get() = LocalKarigoColors.current

val isPro
    @Composable
    get() = LocalIsPro.current