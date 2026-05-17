package com.karigo.ui.theme

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


/**
 * @author hazratummar
 * Created on 17/05/26
 */


// ─────────────────────────────────────────────────────────────────────────────
// Color schemes
// ─────────────────────────────────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    primary              = Primary,
    onPrimary            = OnPrimary,
    primaryContainer     = PrimaryContainer,
    onPrimaryContainer   = OnPrimaryContainer,
    secondary            = StatusInvoiced,
    onSecondary          = OnStatusInvoiced,
    secondaryContainer   = StatusInvoicedSurface,
    onSecondaryContainer = OnStatusInvoiced,
    tertiary             = StatusPending,
    onTertiary           = OnStatusPending,
    tertiaryContainer    = StatusPendingSurface,
    onTertiaryContainer  = OnStatusPending,
    error                = Error,
    onError              = OnError,
    errorContainer       = ErrorContainer,
    onErrorContainer     = OnErrorContainer,
    background           = Background,
    onBackground         = TextPrimary,
    surface              = Surface,
    onSurface            = TextPrimary,
    surfaceVariant       = SurfaceVariant,
    onSurfaceVariant     = TextSecondary,
    outline              = Outline,
    outlineVariant       = OutlineVariant,
    inverseSurface       = TextPrimary,
    inverseOnSurface     = Surface,
    inversePrimary       = PrimaryDim,
    surfaceTint          = Primary,
    scrim                = Scrim,
)

private val LightColorScheme = lightColorScheme(
    primary              = PrimaryDim,
    onPrimary            = OnPrimary,
    primaryContainer     = LightPrimaryContainer,
    onPrimaryContainer   = LightOnPrimaryContainer,
    error                = Error,
    onError              = OnError,
    errorContainer       = LightErrorContainer,
    onErrorContainer     = Error,
    background           = LightBackground,
    onBackground         = OnBackground,
    surface              = LightSurface,
    onSurface            = OnBackground,
    surfaceVariant       = LightSurfaceVariant,
    onSurfaceVariant     = LightOnSurfaceVariant,
    outline              = LightOutline,
    outlineVariant       = LightOutlineVariant,
    scrim                = Scrim,
)


@Composable
fun KarigoTheme(
    windowSizeClass: WindowSizeClass,
    // Dark-first: the entire design was built for dark mode.
    // Outdoor field workers — plumbers on rooftops, electricians in engine rooms.
    // Only pass darkTheme = false for explicit light-mode testing or previews.
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    // ── Resolve adaptive tokens from window size ───────────────────────────
    val dimens = remember(windowSizeClass) {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact  -> CompactDimens
            WindowWidthSizeClass.Medium   -> MediumDimens
            WindowWidthSizeClass.Expanded -> ExpandedDimens
            else                          -> CompactDimens
        }
    }

    val typography = remember(windowSizeClass) {
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact  -> CompactTypography
            WindowWidthSizeClass.Medium   -> MediumTypography
            WindowWidthSizeClass.Expanded -> ExpandedTypography
            else                          -> CompactTypography
        }
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // ── Edge-to-edge system bars ──────────────────────────────────────────
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Draw content behind both status and nav bars
            WindowCompat.setDecorFitsSystemWindows(window, false)
            // Transparent bars — our background shows through
            window.statusBarColor     = Background.toArgb()
            window.navigationBarColor = NavBackground.toArgb()
            // Dark theme = light icons not needed
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars     = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    // ── Provide tokens + apply MaterialTheme ──────────────────────────────
    CompositionLocalProvider(
        LocalDimens  provides dimens,
        LocalAppTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = typography.toMaterial3Typography(),
            shapes      = KarigoShapes,
            content     = content
        )
    }
}

val LocalDimens = compositionLocalOf { CompactDimens }
val LocalAppTypography = compositionLocalOf { CompactTypography }