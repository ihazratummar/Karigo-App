package com.karigojobs.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class KarigoColors(
    val background: Color,
    val cardColors: Color,
    val iconBgColor: Color,
    val modalColor: Color,
    val accentBg : Color,
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText : Color
)


val KarigoDarkColor = KarigoColors(
    background = Background,
    cardColors = Color(0xFF1e1e1e),
    iconBgColor = Color(0xFF2A2A2A),
    modalColor = Color(0xFF141414),
    accentBg = Color(0xFF1a3733),
    primaryText = Color(0xFFFFFFFF),
    secondaryText = Color(0xFFA1A1AA),
    tertiaryText = Color(0xFF71717A),
)

val KarigoLightColor = KarigoColors(
    background = LightBackground,
    cardColors = Color(0xFFffffff),
    iconBgColor = Color(0xFFf5f5f5),
    modalColor = Color.White,
    accentBg = Color(0xFFe8f9f6),
    primaryText = Color(0xFF111111),
    secondaryText = Color(0xFF555555),
    tertiaryText = Color(0xFF999999),
)


val LocalKarigoColors = staticCompositionLocalOf<KarigoColors> {
    error("No KarigoColors provided")
}