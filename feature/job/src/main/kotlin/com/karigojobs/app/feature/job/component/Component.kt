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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextOverflow
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.job.create.AddJobIntent
import com.karigojobs.presentation.job.create.AddJobState
import com.karigojobs.presentation.job.create.LabourItem
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.CrossButton
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.MinusButton
import com.karigojobs.ui.common.PlusButton
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.common.color
import com.karigojobs.ui.common.dashedBorder
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigoSelectedCardColor
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens

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
                    text = "Material",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.materialTotal}",
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
                    text = "Labour (${addJobState.labourItems.size} items)",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.labourTotal}",
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
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${addJobState.grandTotal}",
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
            text = "JOB TITLE",
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
                placeholder = "e.g. Kitchen faucet repair"
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
            text = "SELECT TRADE",
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
                            text = tradeType.displayName,
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
            text = "LABOUR",
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

        AddItemCard(onClick = onClick)
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
        colors = CardDefaults.cardColors(containerColor = KarigojobsCard),
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
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.basicMarquee(
                        iterations = 200,
                        animationMode = MarqueeAnimationMode.Immediately,
                        repeatDelayMillis = 3000
                    ),
                    overflow = TextOverflow.Visible
                )
                Text(
                    text = "${deviceInfo.currency}${labourItem.rate} / ${labourItem.unit}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            MinusButton(onClick = onMinusClick)
            Text(
                text = labourItem.quantity.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
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
    onRemoveMaterialItemClick: (String) -> Unit,
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
                text = "MATERIALS",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Row(
                modifier = Modifier.clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Manage Library >",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
        Spacer(Modifier.height(dimens.Space.base))

        if (materialsItems.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                materialsItems.forEach { item ->
                    MaterialSelectedChip(
                        item = item,
                        onRemoveClick = { item.materialId?.let { onRemoveMaterialItemClick(it) } }
                    )
                }
            }
            Spacer(Modifier.height(dimens.Space.base))
        }

        AddItemCard(
            text = "Add Materials",
            onClick = onClick
        )

    }
}

@Composable
fun MaterialSelectedChip(
    item: JobMaterialItemModel,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(KarigojobsShapes.large)
            .border(
                BorderStroke(
                    width = dimens.Border.thin,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                shape = KarigojobsShapes.large
            )
            .background(
                shape = KarigojobsShapes.large,
                color = MaterialTheme.colorScheme.primaryContainer,
            )

    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = dimens.Padding.sm,
                vertical = dimens.Padding.sm
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(dimens.Space.xs))
            Text(
                text = "x${item.quantity}",
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(Modifier.width(dimens.Space.sm))
            Box(
                modifier = Modifier
                    .bounceClickable(onRemoveClick)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.1f
                        ),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(dimens.Icon._2xs)
                )
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
            containerColor = KarigojobsCard
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
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(dimens.Space.md))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
        containerColor = ModalBackGround,

        ) {
        Column(
            modifier = modifier
                .padding(horizontal = dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Text(
                text = "Add Labour Item",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            KarigojobsTextField(
                value = itemName,
                onValueChange = { itemName = it },
                placeholder = "Work name, e.g. Wiring per point"
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
                        text = "RATE (${deviceInfo.currency})",
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
                        text = "UNIT",
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
                    text = "Add Item"
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialLibraryModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    addJobState: AddJobState,
    onIntent: (AddJobIntent) -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = ModalBackGround
    ) {
        Box(
            modifier = Modifier.padding(horizontal = dimens.Padding.base)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Text(
                    text = "Materials Library",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Tap items to add, adjust quantities",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                HorizontalDivider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    items(addJobState.availableMaterials) { material ->
                        val selectedItem =
                            addJobState.selectedMaterials.find { it.materialId == material.id }
                        val isSelected = selectedItem != null

                        MaterialItemCard(
                            materialItemModel = material,
                            isSelected = isSelected,
                            selectedQuantity = selectedItem?.quantity ?: 0,
                            onAddClick = { onIntent(AddJobIntent.IncreaseMaterialQuantity(material.id)) },
                            onMinusClick = { onIntent(AddJobIntent.MinusMaterialQuantity(material.id)) }
                        )
                    }
                }

                // Sticky Confirm Button
                if (addJobState.selectedMaterials.isNotEmpty()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimens.Padding.base),
                        onClick = onDismiss,
                        shape = KarigojobsShapes.medium
                    ) {
                        Text(text = "Confirm — ${deviceInfo.currency}${addJobState.materialTotal}")
                    }
                }
            }
        }
    }
}

@Composable
fun MaterialItemCard(
    modifier: Modifier = Modifier,
    materialItemModel: MaterialsModel,
    isSelected: Boolean = false,
    selectedQuantity: Int = 0,
    onAddClick: () -> Unit,
    onMinusClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) KarigoSelectedCardColor else Color.Transparent
        ),
        border = if (isSelected) BorderStroke(
            width = dimens.Border.thin,
            color = MaterialTheme.colorScheme.onBackground
        ) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
            ) {
                Text(
                    text = materialItemModel.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${materialItemModel.price} / ${materialItemModel.unit}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(Modifier.width(dimens.Space.md))

            if (isSelected) {
                MinusButton(
                    onClick = onMinusClick
                )
                Spacer(Modifier.width(dimens.Space.sm))
                Text(
                    text = selectedQuantity.toString(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.width(dimens.Space.sm))
                PlusButton(
                    onClick = onAddClick
                )
            } else {
                PlusButton(
                    onClick = onAddClick
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
            containerColor = KarigojobsCard
        )
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
                        iconBackGroundColor = SurfaceOverlay,
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
                                color = MaterialTheme.colorScheme.onBackground,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = job.createdAt.toReadableDate(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
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
            containerColor = KarigojobsCard
        )
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
                    text = "Status",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(KarigojobsShapes.medium)
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .border(
                            width = dimens.Border.thin,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = KarigojobsShapes.medium
                        )
                        .bounceClickable(
                            onClick = onChangeClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Change",
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
                            text = status.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = KarigojobsText2
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
            containerColor = KarigojobsCard
        )
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
                    iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = clientModel.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = clientModel.phone,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Text(
                            text = clientModel.address,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
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
            containerColor = KarigojobsCard
        )
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
                    iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)
                )

                Text(
                    text = "Labour",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(0.4f),
                            shape = KarigojobsShapes.large
                        )
                ) {
                    Text(
                        text = "Per Item",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsIconColor
                        ),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding._2xs
                        )
                    )
                }
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
                title = "LABOUR TOTAL"
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
            containerColor = KarigojobsCard
        )
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
                    iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer.copy(0.4f)
                )

                Text(
                    text = "Materials",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
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
                title = "MATERIAL TOTAL"
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
                color = KarigojobsText2
            )
        )
        Text(
            text = "$total",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
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
        modifier = modifier.padding(vertical = dimens.Padding.sm).fillMaxWidth(),
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
                    color = KarigojobsText3
                )
            )
            Text(
                text = "$quantity x ${deviceInfo.currency}$itemRate / $unit",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = KarigojobsText2
                )
            )
        }

        Text(
            text = "${deviceInfo.currency}${total}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}