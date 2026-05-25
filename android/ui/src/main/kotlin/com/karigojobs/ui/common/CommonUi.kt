package com.karigojobs.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsBorder
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.NavInactive
import com.karigojobs.ui.theme.OnStatusDone
import com.karigojobs.ui.theme.OnStatusInProgress
import com.karigojobs.ui.theme.OnStatusInvoiced
import com.karigojobs.ui.theme.OnStatusPaid
import com.karigojobs.ui.theme.OnStatusPending
import com.karigojobs.ui.theme.StatusDone
import com.karigojobs.ui.theme.StatusDoneSurface
import com.karigojobs.ui.theme.StatusInProgress
import com.karigojobs.ui.theme.StatusInProgressSurface
import com.karigojobs.ui.theme.StatusInvoiced
import com.karigojobs.ui.theme.StatusInvoicedSurface
import com.karigojobs.ui.theme.StatusPaid
import com.karigojobs.ui.theme.StatusPaidSurface
import com.karigojobs.ui.theme.StatusPending
import com.karigojobs.ui.theme.StatusPendingSurface
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */


@Composable
fun KarigojobsSearchField(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    placeholder: String = "Search Client..."
) {

    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = KarigojobsText
        ),
        cursorBrush = SolidColor(KarigojobsAccent),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = dimens.Border.thin,
                color = if (isFocused) KarigojobsAccent else KarigojobsBorder,
                shape = KarigojobsShapes.medium
            )
            .background(
                color = KarigojobsCard,
                shape = KarigojobsShapes.medium
            )
            .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.md),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "Search",
                    modifier = Modifier.size(dimens.Icon.sm),
                    tint = KarigojobsText2
                )
                Spacer(Modifier.width(dimens.Space.base))
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = KarigojobsText2,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                }
            }
        }
    )

}


@Composable
fun KarigojobsTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "Search Client...",
    keyboardType: KeyboardType = KeyboardType.Text
) {

    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = KarigojobsText
        ),
        cursorBrush = SolidColor(KarigojobsAccent),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = dimens.Border.thin,
                color = if (isFocused) KarigojobsAccent else KarigojobsBorder,
                shape = KarigojobsShapes.medium
            )
            .background(
                color = KarigojobsCard,
                shape = KarigojobsShapes.medium
            )
            .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.md),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = KarigojobsText2,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )

}


// ─────────────────────────────────────────────
//  Dashed border modifier
// ─────────────────────────────────────────────
fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp = 12.dp,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 6.dp,
    gapLength: Dp = 4.dp,
): Modifier = this.drawWithContent {
    drawContent()
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            phase = 0f,
        ),
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
    )
}



fun Modifier.bounceClickable(
    onClick: () -> Unit
): Modifier = composed {

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    try {
                        awaitRelease()
                    } finally {
                        isPressed = false
                    }
                },
                onTap = { onClick() }
            )
        }
}

@Composable
fun CounterControl(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.add,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    iconBackGroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(dimens.Height.minTouch)
            .bounceClickable(onClick = onClick)
            .padding(dimens.Padding.sm)
            .clip(KarigojobsShapes.small)
            .background(
                color = iconBackGroundColor
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Add",
            modifier = Modifier.size(dimens.Icon.xs),
            tint =iconColor
        )
    }
}



@Composable
fun KarigoIconWIthBgCick(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.arrow_left,
    iconColor: Color = NavInactive,
    size: Dp = dimens.Height.minTouch,
    iconBackGroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit = {}
) {
    val iconSize = size * 0.4f

    Box(
        modifier = modifier
            .size(size)
            .padding(size * 0.15f)
            .clip(KarigojobsShapes.medium)
            .background(color = iconBackGroundColor)
            .clickable(onClick = onClick)
        ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Back",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

@Composable
fun KarigoIconWIthBg(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.arrow_left,
    iconColor: Color = NavInactive,
    size: Dp = dimens.Height.minTouch,
    iconBackGroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val iconSize = size * 0.4f

    Box(
        modifier = modifier
            .size(size)
            .clip(KarigojobsShapes.medium)
            .background(color = iconBackGroundColor)
        ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Back",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}




fun JobStatus.color(): ColorPalate {
    return when (this) {
        JobStatus.PENDING -> {
            ColorPalate(
                accent = StatusPending,
                surface = StatusPendingSurface,
                onSurface = OnStatusPending
            )
        }
        JobStatus.IN_PROGRESS -> {
            ColorPalate(
                accent = StatusInProgress,
                surface = StatusInProgressSurface,
                onSurface = OnStatusInProgress
            )
        }
        JobStatus.COMPLETED -> {
            ColorPalate(
                accent = StatusDone,
                surface = StatusDoneSurface,
                onSurface = OnStatusDone
            )
        }
        JobStatus.INVOICED -> {
            ColorPalate(
                accent = StatusInvoiced,
                surface = StatusInvoicedSurface,
                onSurface = OnStatusInvoiced
            )
        }
        JobStatus.PAID -> {
            ColorPalate(
                accent = StatusPaid,
                surface = StatusPaidSurface,
                onSurface = OnStatusPaid
            )
        }
    }
}

data class ColorPalate(
    val accent: Color,
    val surface: Color,
    val onSurface : Color
)



@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    job: JobModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = KarigojobsShapes.large,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.md)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                KarigoIconWIthBg(
                    icon = job.tradeType.icon(),
                    iconColor = KarigojobsAccent,
                    iconBackGroundColor = SurfaceOverlay,
                    size = dimens.Height.minTouch / 1.1f
                )
                Column(
                    modifier = Modifier.weight(1f).padding(horizontal = dimens.Padding.md),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                ) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                    Text(
                        text = job.clientName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(KarigojobsShapes.small)
                        .background(
                            color = job.status.color().surface
                        )
                        .border(
                            width = dimens.Border.thin,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = KarigojobsShapes.small
                        )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = job.status.toString(),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding._2xs
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = job.status.color().accent
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.createdAt.toReadableDate(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${job.total}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}
