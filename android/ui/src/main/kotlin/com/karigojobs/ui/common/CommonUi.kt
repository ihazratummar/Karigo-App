package com.karigojobs.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.app.android.ui.R
import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsBorder
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.KarigojobsWarning
import com.karigojobs.ui.theme.ModalBackGround
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


/**
 * @author hazratummar
 * Created on 23/05/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarigoTopAppBar(
    onNavigationClick: () -> Unit = {},
    title: String = "Estimate",
    action: @Composable () -> Unit = {},
    isNavBack: Boolean = true,
    isDivider : Boolean = true
) {
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            title = {
                TopBarTitle(title = title)
            },
            navigationIcon = {
                if (isNavBack) {
                    KarigoIconWIthBgCick(onClick = onNavigationClick)
                }
            },
            actions = {
                action()
            },
            windowInsets = WindowInsets(),
        )
        if (isDivider){
            HorizontalDivider()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarigoMiddleTextTopAppBar(
    onNavigationClick: () -> Unit = {},
    title: String = "New Job",
    action: @Composable () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            title = {
                TopBarTitle(title = title)
            },
            navigationIcon = {
                KarigoIconWIthBgCick(onClick = onNavigationClick)
            },
            actions = {
                action()
            },
            windowInsets = WindowInsets(),
        )
        HorizontalDivider()
    }
}


@Composable
fun SectionWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        content()
    }

}


@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}


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
        cursorBrush = SolidColor(KarigojobsIconColor),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = dimens.Border.thin,
                color = if (isFocused) KarigojobsIconColor else KarigojobsBorder,
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
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1
) {

    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = KarigojobsText
        ),
        cursorBrush = SolidColor(KarigojobsIconColor),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = dimens.Border.thin,
                color = if (isFocused) KarigojobsIconColor else KarigojobsBorder,
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


@Composable
fun customCardBorder(): BorderStroke {
    return BorderStroke(
        width = dimens.Border.thin / 10f,
        color = KarigojobsText2,
    )
}


@Composable
fun Modifier.contentHorizontalPadding(): Modifier = composed {
    this.padding(horizontal = dimens.Padding.base)
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
    onClick: () -> Unit = {},
    size: Dp = dimens.Height.minTouch
) {
    val iconSize = size * 0.45f
    Box(
        modifier = modifier
            .size(size)
            .bounceClickable(onClick = onClick)
            .padding(size * 0.15f)
            .clip(KarigojobsShapes.small)
            .background(
                color = iconBackGroundColor
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Add",
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

@Composable
fun MinusButton(
    onClick: () -> Unit = {},
    size: Dp = dimens.Height.minTouch
) {
    CounterControl(
        icon = R.drawable.substract,
        iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        iconBackGroundColor = SurfaceOverlay,
        onClick = onClick,
        size = size
    )
}

@Composable
fun PlusButton(
    onClick: () -> Unit = {},
    size: Dp = dimens.Height.minTouch
) {
    CounterControl(
        icon = R.drawable.add,
        iconColor = MaterialTheme.colorScheme.primary,
        iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = onClick,
        size = size
    )
}


@Composable
fun CrossButton(onClick: () -> Unit) {
    CounterControl(
        icon = R.drawable.close,
        iconColor = MaterialTheme.colorScheme.error,
        iconBackGroundColor = Color.Transparent,
        onClick = onClick
    )
}


@Composable
fun KarigoIconWIthBgCick(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.arrow_left,
    iconColor: Color = NavInactive,
    size: Dp = dimens.Height.minTouch,
    iconBackGroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit = {},
    isBorder: Boolean = false
) {
    val iconSize = size * 0.4f

    Box(
        modifier = modifier
            .size(size)
            .padding(size * 0.15f)
            .clip(KarigojobsShapes.medium)
            .let {
                if (isBorder) it.border(
                    width = dimens.Border.thin,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = KarigojobsShapes.medium
                ) else it
            }
            .background(color = iconBackGroundColor)
            .clickable(onClick = onClick),
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
            .background(color = iconBackGroundColor),
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
    val onSurface: Color
)


@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    job: JobModel,
    onClick: () -> Unit,
    isClientJob: Boolean = false
) {
    Card(
        onClick = onClick,
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
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = SurfaceOverlay,
                    size = dimens.Height.minTouch / 1.1f
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimens.Padding.md),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                ) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (isClientJob) {
                        Text(
                            text = job.createdAt.toReadableDate(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    } else {
                        Text(
                            text = job.clientName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(KarigojobsShapes.medium)
                        .background(
                            color = job.status.color().surface
                        )
                        .border(
                            width = dimens.Border.thin,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = KarigojobsShapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = job.status.toString(),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding.xs
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
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                if (isClientJob) {
                    Icon(
                        painter = painterResource(R.drawable.stack),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(dimens.Icon._2xs)
                    )
                    Text(
                        text = "${job.totalItems} items",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                } else {
                    Text(
                        text = job.createdAt.toReadableDate(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Spacer(Modifier.weight(1f))
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


@Composable
fun DeleteDialog(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    dialogTitle: String = "Delete Job",
    dialogDescription: String = "This will permanently remove this job and its invoice history."
) {
    PopUpDialog(
        onCancelClick = onCancelClick,
        onConfirmClick = onConfirmClick,
        icon = R.drawable.delete,
        confirmButtonText = "Delete",
        confirmButtonColor = MaterialTheme.colorScheme.error,
        title = dialogTitle,
        description = dialogDescription
    )
}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PopUpDialog(
    onCancelClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    confirmButtonText: String = "Delete",
    icon: Int = R.drawable.delete,
    confirmButtonColor: Color = MaterialTheme.colorScheme.error,
    title: String = "",
    description: String = ""

) {
    KarigojobsThemePreview(darkTheme = true) {
        Dialog(
            onDismissRequest = onCancelClick,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = KarigojobsShapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = ModalBackGround
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(dimens.Padding.base)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.base),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.Icon.md),
                        tint = confirmButtonColor
                    )

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        DialogButton(
                            modifier = Modifier.weight(1f),
                            onClick = onCancelClick
                        )
                        DialogButton(
                            modifier = Modifier.weight(1f),
                            onClick = onConfirmClick,
                            buttonText = confirmButtonText,
                            buttonColor = confirmButtonColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButton(
    modifier: Modifier = Modifier,
    buttonColor: Color = KarigojobsCard,
    buttonText: String = "Cancel",
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = KarigojobsShapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
fun ClientPicker(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
    selectedClient: ClientModel?
) {
    Card(
        modifier = modifier.heightIn(min = dimens.Space._5xl),
        onClick = onCardClick,
        shape = KarigojobsShapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        if (selectedClient == null) {
            Row(
                modifier = Modifier
                    .padding(dimens.Padding.md)
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {

                Icon(
                    painter = painterResource(R.drawable.user_search),
                    contentDescription = "User search",
                    modifier = Modifier.size(dimens.Icon.sm),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )


                Text(
                    text = "Select Client...",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        } else {
            ClientInfo(
                modifier = Modifier
                    .padding(dimens.Padding.sm)
                    .fillMaxWidth()
                    .weight(1f),
                deviceContact = DeviceContact(
                    name = selectedClient.name,
                    phoneNumber = listOf(selectedClient.phone)
                )
            )
        }
    }
}


@Composable
fun ClientInfo(
    modifier: Modifier = Modifier,
    deviceContact: DeviceContact
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KarigoIconWIthBgCick(
            icon = R.drawable.user_line,
            iconColor = MaterialTheme.colorScheme.primary,
            iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(Modifier.width(dimens.Space.base))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = deviceContact.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = deviceContact.phoneNumber.firstOrNull() ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPicker(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onContactClick: (DeviceContact) -> Unit = {},
    contacts: List<DeviceContact> = emptyList(),
    isLoading: Boolean = false
) {
    var search by remember { mutableStateOf("") }
    val filteredContacts = remember(search, contacts) {
        if (search.isBlank()) contacts
        else contacts.filter {
            it.name.contains(search, ignoreCase = true) ||
                    it.phoneNumber.any { number -> number.contains(search) }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = ModalBackGround
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimens.Padding.xl),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Text(
                text = "Select Client",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )

            KarigojobsSearchField(
                modifier = Modifier,
                query = search,
                onQueryChange = { search = it },
                placeholder = "Search Client..."
            )
            HorizontalDivider()

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No contacts found",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    items(filteredContacts) { contact ->
                        ClientInfo(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        onContactClick(contact)
                                        onDismiss()
                                    }
                                ),
                            deviceContact = contact
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun KarigoDataPicker(
    modifier: Modifier = Modifier,
    selectedDateMillis: Long = System.currentTimeMillis(),
    onClick: () -> Unit = {}
) {
    val formattedDate = remember(selectedDateMillis) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.format(Date(selectedDateMillis))
    }
    Card(
        modifier = modifier.heightIn(min = dimens.Space._5xl),
        onClick = onClick,
        shape = KarigojobsShapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        ),

        ) {

        Row(
            modifier = Modifier
                .padding(horizontal = dimens.Padding.base)
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                painter = painterResource(R.drawable.calendar1),
                contentDescription = "Pick date",
                tint = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(dimens.Icon.sm)
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarigoDatePickerSheet(
    selectedDateMillis: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val initialMillis = remember(selectedDateMillis) {
        // normalize to midnight UTC so M3 DatePicker doesn't shift days
        val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = selectedDateMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        cal.timeInMillis
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = KarigojobsCard,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = dimens.Padding.md, bottom = dimens.Padding.sm)
                    .width(dimens.Height.minTouch)
                    .height(dimens.Space.sm)
                    .clip(RoundedCornerShape(dimens.Space.sm))
                    .background(Color.White.copy(alpha = 0.3f))
            )
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = KarigojobsCard,
                titleContentColor = Color.White.copy(alpha = 0.7f),
                headlineContentColor = Color.White,
                weekdayContentColor = Color.White.copy(alpha = 0.5f),
                subheadContentColor = Color.White.copy(alpha = 0.7f),
                navigationContentColor = Color.White,
                yearContentColor = Color.White,
                currentYearContentColor = Color.White,
                selectedYearContentColor = Color.White,
                selectedYearContainerColor = Color(0xFF4B9EFF),
                dayContentColor = Color.White,
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = Color(0xFF4B9EFF),
                todayContentColor = Color(0xFF4B9EFF),
                todayDateBorderColor = Color(0xFF4B9EFF),
                dayInSelectionRangeContentColor = Color.White,
                dayInSelectionRangeContainerColor = Color(0xFF4B9EFF).copy(alpha = 0.2f)
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.Padding.base)
                .padding(bottom = dimens.Padding.xl)
                .height(dimens.Height.minTouch),
            onClick = {
                datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4B9EFF)
            ),
            shape = RoundedCornerShape(dimens.Space.base)
        ) {
            Text(
                text = "Confirm",
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun SpringToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val trackWidth = dimens.Size.toggleTrackW
    val trackHeight = dimens.Size.toggleTrackH
    val thumbSize = dimens.Size.toggleThumb
    val gap = (trackHeight - thumbSize) / 2

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbSize - gap else gap,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "thumbOffset"
    )
    val trackColor by animateColorAsState(
        targetValue = if (checked) Color(0xFF1AAA80) else Color(0xFF3A4A52),
        animationSpec = tween(200),
        label = "trackColor"
    )

    Box(
        modifier = modifier
            .size(width = trackWidth, height = trackHeight)
            .clip(CircleShape)
            .background(trackColor)
            .toggleable(
                value = checked,
                role = Role.Switch,
                onValueChange = onCheckedChange
            ).let {
                if (!checked) it.border(
                    width = dimens.Border.thin,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                ) else it
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = thumbOffset)
                .size(thumbSize)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}


@Composable
fun CommunicationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: Color,
    contentColor: Color,
    icon: Int,
    buttonText: String
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = contentColor
        ),
        border = BorderStroke(
            width = dimens.Border.thin,
            color = MaterialTheme.colorScheme.onBackground
        ),
        shape = KarigojobsShapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(dimens.Icon._2xs)
            )
            Spacer(Modifier.width(dimens.Space.sm))
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = contentColor
                )
            )
        }
    }
}



@Composable
fun ActionNeedBanner(
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = KarigojobsShapes.large,
        border = BorderStroke(width = dimens.Border.thin, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(dimens.Padding.base).fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Icon(
                painter = painterResource(R.drawable.user_action),
                contentDescription = null,
                tint = KarigojobsWarning,
                modifier = Modifier.size(dimens.Icon.md)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    Text(
                        text = "ACTION NEEDED",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = KarigojobsWarning,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "2 missing",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsWarning,
                        )
                    )
                }
                Text(
                    text = "Complete your business profile",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Add Owner Name and Business Name so your estimate and invoices look professional",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}