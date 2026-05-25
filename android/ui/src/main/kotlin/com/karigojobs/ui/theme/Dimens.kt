package com.karigojobs.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author hazratummar
 * Created on 17/05/26
 * Updated on 26/05/23 (Responsive Refactor)
 */

data class AppDimens(
    val Space: SpaceDimens,
    val Radius: RadiusDimens,
    val Icon: IconDimens,
    val Height: HeightDimens,
    val Size: SizeDimens,
    val Padding: PaddingDimens,
    val Elevation: ElevationDimens,
    val Border: BorderDimens,
    val Divider: DividerDimens,
    val Text: TextDimens,
    val Avatar: AvatarDimens
)

data class SpaceDimens(
    val none: Dp,
    val _2xs: Dp,
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val _2md: Dp,
    val base: Dp,
    val _2base: Dp,
    val lg: Dp,
    val xl: Dp,
    val _2xl: Dp,
    val _3xl: Dp,
    val _4xl: Dp,
    val _5xl: Dp,
    val _6xl: Dp,
    val _7xl: Dp,
    val _8xl: Dp
)

data class RadiusDimens(
    val none: Dp,
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,
    val full: Dp
)

data class IconDimens(
    val _2xs: Dp,
    val xs: Dp,
    val sm: Dp,
    val base: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,
    val _2xl: Dp,
    val _3xl: Dp,
    val _4xl: Dp,
    val _5xl: Dp,
    val _6xl: Dp,
    val _7xl: Dp
)

data class HeightDimens(
    val minTouch: Dp,
    val chip: Dp,
    val chipLg: Dp,
    val chipXl: Dp,
    val buttonSm: Dp,
    val buttonMd: Dp,
    val buttonBase: Dp,
    val buttonLg: Dp,
    val buttonXl: Dp,
    val button2Xl: Dp,
    val inputSm: Dp,
    val inputBase: Dp,
    val inputLg: Dp,
    val topBar: Dp,
    val bottomNav: Dp,
    val sheetHandle: Dp,
    val progressTrack: Dp,
    val progressThick: Dp
)

data class SizeDimens(
    val toggleTrackW: Dp,
    val toggleTrackH: Dp,
    val toggleThumb: Dp,
    val fab: Dp,
    val dot: Dp,
    val badgeMin: Dp,
    val dialogMax: Dp,
    val dialogIcon: Dp,
    val chartBarMaxW: Dp,
    val chartHeight: Dp,
    val chartBarMaxH: Dp,
    val stepperBtn: Dp,
    val stepperBtnSm: Dp
)

data class PaddingDimens(
    val _2xs: Dp,
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val base: Dp,
    val lg: Dp,
    val xl: Dp,
    val screenH: Dp,
    val screenV: Dp
)

data class ElevationDimens(
    val none: Dp,
    val xs: Dp,
    val sm: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp
)

data class BorderDimens(
    val none: Dp,
    val thin: Dp,
    val thick: Dp
)

data class DividerDimens(
    val thickness: Dp,
    val paddingV: Dp
)

data class TextDimens(
    val _2xs: TextUnit,
    val xs: TextUnit,
    val sm: TextUnit,
    val base: TextUnit,
    val md: TextUnit,
    val lg: TextUnit,
    val xl: TextUnit,
    val _2xl: TextUnit,
    val _3xl: TextUnit,
    val _4xl: TextUnit,
    val _5xl: TextUnit,
    val _6xl: TextUnit
)

data class AvatarDimens(
    val xs: Dp,
    val sm: Dp,
    val base: Dp,
    val md: Dp,
    val lg: Dp,
    val xl: Dp,
    val _2xl: Dp
)

val CompactAppDimens = AppDimens(
    Space = SpaceDimens(
        none = 0.dp,
        _2xs = 2.dp,
        xs = 4.dp,
        sm = 6.dp,
        md = 8.dp,
        _2md = 10.dp,
        base = 12.dp,
        _2base = 14.dp,
        lg = 16.dp,
        xl = 20.dp,
        _2xl = 24.dp,
        _3xl = 32.dp,
        _4xl = 48.dp,
        _5xl = 64.dp,
        _6xl = 80.dp,
        _7xl = 96.dp,
        _8xl = 112.dp
    ),
    Radius = RadiusDimens(
        none = 0.dp,
        xs = 4.dp,
        sm = 8.dp,
        md = 12.dp,
        lg = 16.dp,
        xl = 24.dp,
        full = 9999.dp
    ),
    Icon = IconDimens(
        _2xs = 12.dp,
        xs = 16.dp,
        sm = 20.dp,
        base = 24.dp,
        md = 28.dp,
        lg = 32.dp,
        xl = 36.dp,
        _2xl = 40.dp,
        _3xl = 48.dp,
        _4xl = 56.dp,
        _5xl = 64.dp,
        _6xl = 80.dp,
        _7xl = 96.dp
    ),
    Height = HeightDimens(
        minTouch = 48.dp,
        chip = 24.dp,
        chipLg = 32.dp,
        chipXl = 36.dp,
        buttonSm = 32.dp,
        buttonMd = 36.dp,
        buttonBase = 40.dp,
        buttonLg = 44.dp,
        buttonXl = 48.dp,
        button2Xl = 56.dp,
        inputSm = 40.dp,
        inputBase = 48.dp,
        inputLg = 56.dp,
        topBar = 56.dp,
        bottomNav = 64.dp,
        sheetHandle = 4.dp,
        progressTrack = 4.dp,
        progressThick = 6.dp
    ),
    Size = SizeDimens(
        toggleTrackW = 44.dp,
        toggleTrackH = 24.dp,
        toggleThumb = 20.dp,
        fab = 56.dp,
        dot = 8.dp,
        badgeMin = 18.dp,
        dialogMax = 384.dp,
        dialogIcon = 48.dp,
        chartBarMaxW = 28.dp,
        chartHeight = 160.dp,
        chartBarMaxH = 128.dp,
        stepperBtn = 32.dp,
        stepperBtnSm = 24.dp
    ),
    Padding = PaddingDimens(
        _2xs = 2.dp,
        xs = 4.dp,
        sm = 8.dp,
        md = 12.dp,
        base = 16.dp,
        lg = 20.dp,
        xl = 24.dp,
        screenH = 16.dp,
        screenV = 12.dp
    ),
    Elevation = ElevationDimens(
        none = 0.dp,
        xs = 2.dp,
        sm = 4.dp,
        md = 8.dp,
        lg = 12.dp,
        xl = 16.dp
    ),
    Border = BorderDimens(
        none = 0.dp,
        thin = 1.dp,
        thick = 2.dp
    ),
    Divider = DividerDimens(
        thickness = 1.dp,
        paddingV = 12.dp
    ),
    Text = TextDimens(
        _2xs = 9.sp,
        xs = 10.sp,
        sm = 11.sp,
        base = 12.sp,
        md = 14.sp,
        lg = 16.sp,
        xl = 18.sp,
        _2xl = 20.sp,
        _3xl = 22.sp,
        _4xl = 24.sp,
        _5xl = 30.sp,
        _6xl = 36.sp
    ),
    Avatar = AvatarDimens(
        xs = 28.dp,
        sm = 32.dp,
        base = 36.dp,
        md = 40.dp,
        lg = 48.dp,
        xl = 56.dp,
        _2xl = 64.dp
    )
)

// Medium scaling
val MediumAppDimens = CompactAppDimens.copy(
    Padding = CompactAppDimens.Padding.copy(
        screenH = 24.dp,
        screenV = 16.dp
    ),
    Space = CompactAppDimens.Space.copy(
        base = 14.dp,
        lg = 20.dp,
        xl = 24.dp,
        _2xl = 32.dp,
        _3xl = 40.dp
    ),
    Height = CompactAppDimens.Height.copy(
        topBar = 60.dp,
        bottomNav = 68.dp
    )
)

// Expanded scaling
val ExpandedAppDimens = CompactAppDimens.copy(
    Padding = CompactAppDimens.Padding.copy(
        screenH = 32.dp,
        screenV = 20.dp
    ),
    Space = CompactAppDimens.Space.copy(
        base = 16.dp,
        lg = 24.dp,
        xl = 32.dp,
        _2xl = 40.dp,
        _3xl = 48.dp
    ),
    Height = CompactAppDimens.Height.copy(
        topBar = 64.dp,
        bottomNav = 72.dp
    )
)

val KarigojobsShapes = Shapes(
    extraSmall = RoundedCornerShape(CompactAppDimens.Radius.full),
    small = RoundedCornerShape(CompactAppDimens.Radius.sm),
    medium = RoundedCornerShape(CompactAppDimens.Radius.md),
    large = RoundedCornerShape(CompactAppDimens.Radius.lg),
    extraLarge = RoundedCornerShape(CompactAppDimens.Radius.xl),
)
