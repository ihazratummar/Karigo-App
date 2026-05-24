package com.karigo.app.feature.job.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.karigo.app.android.ui.R
import com.karigo.presentation.job.create.AddJobState
import com.karigo.presentation.job.create.LabourItem
import com.karigo.share.model.JobLabourItemModel
import com.karigo.ui.common.CounterControl
import com.karigo.ui.common.KarigoTextField
import com.karigo.ui.common.dashedBorder
import com.karigo.ui.theme.KarigoCard
import com.karigo.ui.theme.KarigoShapes
import com.karigo.ui.theme.ModalBackGround
import com.karigo.ui.theme.SurfaceOverlay
import com.karigo.ui.theme.deviceInfo
import com.karigo.ui.theme.dimens

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
                containerColor = KarigoCard
            )
        ) {
            KarigoTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = "e.g. Kitchen faucet repair"
            )
        }
    }
}


@Composable
fun AddJobLabourItemSection(
    modifier: Modifier = Modifier,
    title: String = "LABOUR",
    onClick: () -> Unit = {},
    buttonLabel: String = "Add Labour Item",
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
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(Modifier.height(dimens.Space.base))

        labourItems.forEach { item ->
            LabourItemCard(
                labourItem = item,
                onRemoveClick = { onRemoveLabourItemClick(item.id) },
                onAddClick = {onQuantityAddClick(item.id)},
                onMinusClick = {onQuantityMinusClick(item.id)},
            )
            Spacer(modifier = Modifier.height(dimens.Space.xs))
        }

        Card(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f),
                    strokeWidth = DividerDefaults.Thickness
                ),
            colors = CardDefaults.cardColors(
                containerColor = KarigoCard
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
                    text = buttonLabel,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
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
        colors = CardDefaults.cardColors(containerColor = KarigoCard),
        shape = KarigoShapes.medium
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
            Column {
                Text(
                    text = labourItem.itemName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${labourItem.rate} / ${labourItem.unit}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Spacer(Modifier.weight(1f))

            CounterControl(
                icon = R.drawable.substract,
                iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                iconBackGroundColor = SurfaceOverlay,
                onClick = onMinusClick
            )
            Text(
                text = labourItem.quantity.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            CounterControl(
                icon = R.drawable.add,
                iconColor = MaterialTheme.colorScheme.primary,
                iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer,
                onClick = onAddClick
            )

            CounterControl(
                icon = R.drawable.close,
                iconColor = MaterialTheme.colorScheme.error,
                iconBackGroundColor = Color.Transparent,
                onClick = onRemoveClick
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
            KarigoTextField(
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
                    KarigoTextField(
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
                    KarigoTextField(
                        value = unitInput,
                        onValueChange = { unitInput = it },
                        placeholder = "unit, point, sq foot",
                        keyboardType = KeyboardType.Number
                    )
                }

            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAddClick(
                        LabourItem(
                            itemName = itemName,
                            itemRate = rateInput.toDouble(),
                            quantity = 1,
                            unit = unitInput
                        )
                    )
                    onDismiss()
                },
                shape = KarigoShapes.medium,
                enabled = itemName.isNotEmpty()
            ) {
                Text(
                    text = "Add Item"
                )
            }
        }
    }

}

