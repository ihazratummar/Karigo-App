package com.karigojobs.app.feature.job.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojob.share.utils.formatNumber
import com.karigojob.share.utils.formatToLocalizeDate
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.job.create.AddJobState
import com.karigojobs.presentation.job.create.LabourItem
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.CrossButton
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.MinusButton
import com.karigojobs.ui.common.PlusButton
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.common.color
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.common.dashedBorder
import com.karigojobs.ui.common.toDisplayName
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigoSelectedCardColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_add_labour_item
import karigojobs.shared.resources.generated.resources.common_add_material_item
import karigojobs.shared.resources.generated.resources.common_btn_add_item
import karigojobs.shared.resources.generated.resources.common_btn_change
import karigojobs.shared.resources.generated.resources.common_item_count
import karigojobs.shared.resources.generated.resources.common_labour
import karigojobs.shared.resources.generated.resources.common_manage_library
import karigojobs.shared.resources.generated.resources.common_materials
import karigojobs.shared.resources.generated.resources.common_rate
import karigojobs.shared.resources.generated.resources.common_status
import karigojobs.shared.resources.generated.resources.common_tab_job_title
import karigojobs.shared.resources.generated.resources.common_tab_job_title_placeholder
import karigojobs.shared.resources.generated.resources.common_tab_select_trade
import karigojobs.shared.resources.generated.resources.common_total
import karigojobs.shared.resources.generated.resources.common_unit
import karigojobs.shared.resources.generated.resources.job_details_labour_total
import karigojobs.shared.resources.generated.resources.job_details_materials_total
import karigojobs.shared.resources.generated.resources.job_labour_item_name_field_placeholder
import org.jetbrains.compose.resources.stringResource

/**
 * @author hazratummar
 * Created on 23/05/26
 */


@Composable
fun TotalScreenCard(
    modifier: Modifier = Modifier,
    addJobState: AddJobState = AddJobState()
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.xl)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.common_materials),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.materialTotal.toLocaleString()}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${stringResource(Res.string.common_labour)} ${
                        stringResource(
                            Res.string.common_item_count,
                            addJobState.labourItems.size.toLocaleString()
                        )
                    }",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.labourTotal.toLocaleString()}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.common_total),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.grandTotal.toLocaleString()}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@Composable
fun JobTitleSection(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.common_tab_job_title).uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(Modifier.height(dimens.Space.base))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = KarigojobsCard
            )
        ) {
            KarigojobsTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = stringResource(Res.string.common_tab_job_title_placeholder)
            )
        }
    }
}


@Composable
fun SelectTradeSection(
    modifier: Modifier = Modifier,
    savedTrades: List<TradeType> = emptyList(),
    selectedTradeType: TradeType? = null,
    onTradeTypeSelect: (TradeType) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.common_tab_select_trade),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(Modifier.height(dimens.Space.sm))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            savedTrades.forEach { tradeType ->
                val isSelected = tradeType == selectedTradeType
                Card(
                    modifier = Modifier.padding(vertical = dimens.Padding._2xs),
                    onClick = { onTradeTypeSelect(tradeType) },
                    shape = KarigojobsShapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent
                    ),
                    border = BorderStroke(
                        width = dimens.Border.thin / 2.5f,
                        color = if (isSelected) MaterialTheme.colorScheme.onBackground else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding.xs
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            painter = painterResource(tradeType.icon()),
                            contentDescription = "Trade Icon",
                            modifier = Modifier.size(dimens.Icon.xs),
                            tint = tradeType.color()
                        )
                        Text(
                            text = stringResource(tradeType.displayNameRes),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = if (isSelected) MaterialTheme.colorScheme.onBackground
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AddJobLabourItemSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    labourItems: List<JobLabourItemModel>,
    onRemoveLabourItemClick: (String) -> Unit,
    onQuantityAddClick: (String) -> Unit,
    onQuantityMinusClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.common_labour).uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(Modifier.height(dimens.Space.base))

        labourItems.forEach { item ->
            LabourItemCard(
                labourItem = item,
                onRemoveClick = { onRemoveLabourItemClick(item.id) },
                onAddClick = { onQuantityAddClick(item.id) },
                onMinusClick = { onQuantityMinusClick(item.id) },
            )
            Spacer(modifier = Modifier.height(dimens.Space.xs))
        }

        AddItemCard(
            text = stringResource(Res.string.common_add_labour_item),
            onClick = onClick
        )
    }
}


@Composable
fun LabourItemCard(
    modifier: Modifier = Modifier,
    labourItem: JobLabourItemModel,
    onRemoveClick: () -> Unit,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit
) {

    Card(
        modifier = modifier
            .padding(vertical = dimens.Padding.xs)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = KarigojobsShapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = dimens.Padding.base,
                    vertical = dimens.Padding.sm
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = labourItem.itemName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.primaryText
                    ),
                    modifier = Modifier.basicMarquee(
                        iterations = 200,
                        animationMode = MarqueeAnimationMode.Immediately,
                        repeatDelayMillis = 3000
                    ),
                    overflow = TextOverflow.Visible
                )
                Text(
                    text = "${deviceInfo.currency}${labourItem.rate.toLocaleString()} / ${labourItem.unit}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = appColor.secondaryText
                    )
                )
            }

            MinusButton(onClick = onMinusClick)
            Text(
                text = labourItem.quantity.toLocaleString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )
            PlusButton(onClick = onAddClick)

            CrossButton(
                onClick = onRemoveClick
            )
        }
    }

}


@Composable
fun AddMaterialItemSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    materialsItems: List<JobMaterialItemModel>,
    onMaterialQuantityChange: (String, String) -> Unit,
    onMaterialMinusClick: (String) -> Unit,
    onMaterialPlusClick: (String) -> Unit,
    onRemoveMaterialItemClick: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.common_materials).uppercase(),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Row(
                modifier = Modifier.clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${stringResource(Res.string.common_manage_library)} >",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
        Spacer(Modifier.height(dimens.Space.base))

        if (materialsItems.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                materialsItems.forEachIndexed { index, item ->
                    JobMaterialSelectedCard(
                        selectedMaterial = item,
                        number = index + 1,
                        onMaterialQuantityChange = {
                            onMaterialQuantityChange(
                                item.materialId ?: "",
                                it
                            )
                        },
                        onMaterialMinusClick = { onMaterialMinusClick(item.materialId ?: "") },
                        onMaterialPlusClick = { onMaterialPlusClick(item.materialId ?: "") },
                        onRemoveMaterialItemClick = {
                            item.materialId?.let {
                                onRemoveMaterialItemClick(
                                    it
                                )
                            }
                        }
                    )
                }
            }
            Spacer(Modifier.height(dimens.Space.base))
        }

        AddItemCard(
            text = stringResource(Res.string.common_add_material_item),
            onClick = onClick
        )
    }
}

@Composable
fun JobMaterialSelectedCard(
    modifier: Modifier = Modifier,
    selectedMaterial: JobMaterialItemModel,
    number: Int,
    onMaterialQuantityChange: (String) -> Unit,
    onMaterialMinusClick: () -> Unit,
    onMaterialPlusClick: () -> Unit,
    onRemoveMaterialItemClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(dimens.Padding.md)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = appColor.accentBg,
                            shape = CircleShape
                        )
                        .size(dimens.Icon.base),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toLocaleString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = KarigojobsIconColor
                        )
                    )
                }
                Spacer(Modifier.width(dimens.Space.sm))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = selectedMaterial.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "${deviceInfo.currency}${selectedMaterial.unitPrice.toLocaleString()} / ${selectedMaterial.unit}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        )
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimens.Icon.xs)
                        .clickable(
                            onClick = onRemoveMaterialItemClick
                        ),
                    tint = MaterialTheme.colorScheme.error,
                )
            }

            Row(
                modifier = Modifier
                    .padding(dimens.Padding.sm)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = appColor.iconBgColor,
                            shape = KarigojobsShapes.medium
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = dimens.Padding.sm),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                    ) {
                        MinusButton(
                            size = dimens.Icon.lg,
                            onClick = onMaterialMinusClick
                        )
                        BasicTextField(
                            value = selectedMaterial.quantityInput,
                            onValueChange = onMaterialQuantityChange,
                            modifier = Modifier.width(dimens.Icon._3xl),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        PlusButton(
                            size = dimens.Icon.lg,
                            onClick = onMaterialPlusClick
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = SurfaceOverlay,
                            shape = KarigojobsShapes.small
                        )
                ) {
                    Text(
                        text = "${deviceInfo.currency}${selectedMaterial.mainTotal.toLocaleString()}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding.xs
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun AddItemCard(
    text: String = "Add Labour Item",
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .dashedBorder(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f),
                strokeWidth = DividerDefaults.Thickness
            ),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "Add",
                modifier = Modifier.size(dimens.Icon.xs),
                tint = appColor.secondaryText
            )
            Spacer(Modifier.width(dimens.Space.md))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = appColor.secondaryText
                )
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLabourItemModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onAddClick: (LabourItem) -> Unit = {}
) {

    var itemName by remember { mutableStateOf("") }
    var rateInput by remember { mutableStateOf("0") }
    var unitInput by remember { mutableStateOf("point") }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = appColor.modalColor,

        ) {
        Column(
            modifier = modifier
                .padding(horizontal = dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Text(
                text = stringResource(Res.string.common_add_labour_item),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            KarigojobsTextField(
                value = itemName,
                onValueChange = { itemName = it },
                placeholder = stringResource(Res.string.job_labour_item_name_field_placeholder)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "${stringResource(Res.string.common_rate).uppercase()} (${deviceInfo.currency})",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    KarigojobsTextField(
                        value = rateInput,
                        onValueChange = { rateInput = it },
                        placeholder = "Rate, e.g. 100",
                        keyboardType = KeyboardType.Number
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = stringResource(Res.string.common_unit).uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    KarigojobsTextField(
                        value = unitInput,
                        onValueChange = { unitInput = it },
                        placeholder = "unit, point, sq foot",
                        keyboardType = KeyboardType.Text
                    )
                }

            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAddClick(
                        LabourItem(
                            itemName = itemName,
                            itemRate = rateInput.toDoubleOrNull() ?: 0.0,
                            quantity = 1,
                            unit = unitInput
                        )
                    )
                    onDismiss()
                },
                shape = KarigojobsShapes.medium,
                enabled = itemName.isNotEmpty()
            ) {
                Text(
                    text = stringResource(Res.string.common_btn_add_item)
                )
            }
        }
    }
}


@Composable
fun JobDetailsCard(
    modifier: Modifier = Modifier,
    job: JobModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = KarigojobsShapes.large,
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {

        Row(
            modifier = Modifier
                .padding(dimens.Padding.md)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KarigoIconWIthBg(
                        icon = job.tradeType.icon(),
                        iconColor = KarigojobsIconColor,
                        size = dimens.Height.minTouch
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
                                color = appColor.primaryText,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = job.createdAt.formatToLocalizeDate(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = appColor.tertiaryText
                            )
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .clip(KarigojobsShapes.medium)
                    .border(
                        width = dimens.Border.thin / 10f,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = KarigojobsShapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(job.status.toDisplayName()),
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
    }
}


@Composable
fun JobDetailsStatusCard(
    modifier: Modifier = Modifier,
    job: JobModel,
    onChangeClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = KarigojobsShapes.large,
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.md)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.common_status),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = appColor.primaryText
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(KarigojobsShapes.medium)
                        .border(
                            width = dimens.Border.thin / 10f,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = KarigojobsShapes.medium
                        )
                        .bounceClickable(
                            onClick = onChangeClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.common_btn_change),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding.xs
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsIconColor
                        )
                    )
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                JobStatus.entries.forEach { status ->
                    val selectedStatus = job.status == status
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(dimens.Space.sm)
                                .background(
                                    color = if (selectedStatus) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    shape = KarigojobsShapes.extraLarge
                                )
                        )
                        Text(
                            text = stringResource(status.toDisplayName()),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = appColor.secondaryText
                            ),
                            modifier = Modifier
                                .basicMarquee(
                                    initialDelayMillis = 2000,
                                    animationMode = MarqueeAnimationMode.Immediately,
                                    repeatDelayMillis = 5000
                                ),
                            overflow = TextOverflow.Visible,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobStatusChangeModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onStatusClick: (JobStatus) -> Unit,
    jobStatus: JobStatus
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = ModalBackGround,
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = "Update Status",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            JobStatus.entries.forEach { status ->
                val isSelected = jobStatus == status
                Card(
                    onClick = { onStatusClick(status) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) KarigoSelectedCardColor else KarigojobsCard
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {},
                        )

                        Text(
                            text = status.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun JobDetailsClientInfo(
    modifier: Modifier = Modifier,
    clientModel: ClientModel
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                KarigoIconWIthBg(
                    icon = R.drawable.user_line,
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = appColor.accentBg
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = clientModel.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = appColor.primaryText
                        )
                    )
                    Text(
                        text = clientModel.phone,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = appColor.secondaryText
                        )
                    )
                }
            }
            if (clientModel.address.isNotBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.map_point),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.Icon._2xs),
                        tint = appColor.secondaryText
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Text(
                            text = clientModel.address,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = appColor.secondaryText
                            )
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LabourItemList(
    modifier: Modifier = Modifier,
    labourItems: List<JobLabourItemModel>,
    total: Double
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                KarigoIconWIthBg(
                    icon = R.drawable.labour,
                    size = dimens.Icon.lg,
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = appColor.accentBg
                )

                Text(
                    text = stringResource(Res.string.common_labour),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.primaryText
                    )
                )
            }
            labourItems.forEach { item ->
                ItemList(
                    itemName = item.itemName,
                    quantity = item.quantity.toInt(),
                    itemRate = item.rate,
                    unit = item.unit,
                    total = item.total
                )
            }
            HorizontalDivider()
            TotalItemCost(
                total = total,
                title = stringResource(Res.string.job_details_labour_total)
            )
        }
    }
}

@Composable
fun MaterialItemList(
    modifier: Modifier = Modifier,
    materialItems: List<JobMaterialItemModel>,
    total: Double
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                KarigoIconWIthBg(
                    icon = R.drawable.stack,
                    size = dimens.Icon.lg,
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = appColor.accentBg
                )

                Text(
                    text = stringResource(Res.string.common_materials),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.primaryText
                    )
                )
            }
            materialItems.forEach { item ->
                ItemList(
                    itemName = item.name,
                    quantity = item.quantity,
                    itemRate = item.unitPrice,
                    unit = item.unit,
                    total = item.total
                )
            }
            HorizontalDivider()
            TotalItemCost(
                total = total,
                title = stringResource(Res.string.job_details_materials_total)
            )
        }
    }
}


@Composable
fun TotalItemCost(
    modifier: Modifier = Modifier,
    total: Double,
    title: String
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                color = appColor.secondaryText
            )
        )
        Text(
            text = total.toLocaleString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }
}


@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    itemName: String,
    itemRate: Double,
    quantity: Int,
    unit: String,
    total: Double
) {

    Row(
        modifier = modifier
            .padding(vertical = dimens.Padding.sm)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
        ) {
            Text(
                text = itemName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = appColor.tertiaryText
                )
            )
            Text(
                text = "${quantity.toLocaleString()} x ${deviceInfo.currency}${itemRate.toLocaleString()} / $unit",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = appColor.secondaryText
                )
            )
        }

        Text(
            text = "${deviceInfo.currency}${total.toLocaleString()}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = appColor.primaryText,
                fontWeight = FontWeight.Bold
            )
        )
    }
}