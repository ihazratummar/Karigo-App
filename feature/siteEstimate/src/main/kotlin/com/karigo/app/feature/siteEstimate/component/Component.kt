package com.karigo.app.feature.siteEstimate.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.karigojobs.ui.theme.dimens
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.estimate.SiteEstimateEvent
import com.karigojobs.presentation.estimate.SiteEstimateState
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.MinusButton
import com.karigojobs.ui.common.PlusButton
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.dashedBorder
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo


/**
 * @author hazratummar
 * Created on 06/06/26
 */


@Composable
fun AddMaterialCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                cornerRadius = dimens.Radius.md,
                gapLength = dimens.Space.xs,
            )
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "Add Materials",
                modifier = Modifier.size(dimens.Icon.xs),
                tint = KarigojobsText2
            )
            Spacer(modifier = Modifier.size(dimens.Padding.md))
            Text(
                text = "Add Material",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = KarigojobsText2
                )
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMaterialModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: SiteEstimateState,
    event: (SiteEstimateEvent) -> Unit,
) {

    val selectedMaterials = remember(state.selectedMaterials) {
        mutableStateOf(state.selectedMaterials.map { it.materialId })
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        sheetState = sheetState,
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = ModalBackGround
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Text(
                    text = "Select Materials",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.contentHorizontalPadding()
                )

                HorizontalDivider()
                KarigojobsSearchField(
                    modifier = Modifier.contentHorizontalPadding(),
                    query = state.materialQuery,
                    onQueryChange = { event(SiteEstimateEvent.SearchMaterials(it)) }
                )
                LazyRow(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    if (state.tradeTypes != null) {
                        item {
                            val isSelected = state.selectedTradeType == null
                            Card(
                                onClick = {
                                    event(SiteEstimateEvent.SelectTradeType(tradeType = null))
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsAccent else KarigojobsCard
                                ),
                                shape = KarigojobsShapes.large,

                                ) {
                                Text(
                                    text = "All",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else KarigojobsText2
                                    ),
                                    modifier = Modifier.padding(
                                        horizontal = dimens.Padding.base,
                                        vertical = dimens.Padding.sm
                                    )
                                )
                            }
                        }

                        state.tradeTypes?.let { tradeTypes ->
                            items(tradeTypes.toList()) { trade ->
                                val isSelected = trade == state.selectedTradeType
                                Card(
                                    onClick = {
                                        event(SiteEstimateEvent.SelectTradeType(tradeType = trade))
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) KarigojobsAccent else KarigojobsCard
                                    ),
                                    shape = KarigojobsShapes.large,

                                    ) {
                                    Text(
                                        text = trade.displayName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else KarigojobsText2
                                        ),
                                        modifier = Modifier.padding(
                                            horizontal = dimens.Padding.base,
                                            vertical = dimens.Padding.sm
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    items(state.availableMaterials) { material ->
                        val checked = material.id in selectedMaterials.value
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (!checked) KarigojobsCard else MaterialTheme.colorScheme.primaryContainer
                            ),
                            border = if (checked) BorderStroke(
                                width = dimens.Border.thin,
                                color = MaterialTheme.colorScheme.onBackground
                            ) else null

                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(dimens.Padding.sm)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = {
                                        if (checked) {
                                            selectedMaterials.value -= material.id
                                        } else {
                                            selectedMaterials.value += material.id
                                        }
                                    },
                                    interactionSource = remember { MutableInteractionSource() }
                                )

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = material.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    )
                                    Text(
                                        text = "${material.tradeType.displayName} · ${deviceInfo.currency}${material.price} / ${material.unit}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = KarigojobsText2
                                        )
                                    )
                                }
                            }

                        }
                    }
                }
            }
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Button(
                    onClick = {
                        event(SiteEstimateEvent.AddMaterials(selectedMaterials.value))
                        onDismiss()
                    },
                    enabled = selectedMaterials.value.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigojobsShapes.medium
                ) {
                    Text(
                        text = "Add Selected (${selectedMaterials.value.size})"
                    )
                }
            }
        }
    }

}


@Composable
fun SelectedMaterialSection(
    modifier: Modifier = Modifier,
    selectedMaterial: SiteEstimateMaterial,
    isRateVisible: Boolean = true,
    number: Int,
    onMaterialQuantityChange: (String) -> Unit,
    onMaterialMinusClick : () -> Unit,
    onMaterialPlusClick : () -> Unit,
    onMaterialRemoveClick : () -> Unit
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
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
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .size(dimens.Icon.base),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$number",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = KarigojobsAccent
                        )
                    )
                }
                Spacer(Modifier.width(dimens.Space.sm))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    Text(
                        text = selectedMaterial.materialName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    if (isRateVisible) {
                        Text(
                            text = "${deviceInfo.currency}${selectedMaterial.rate} / ${selectedMaterial.unit}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = KarigojobsText2
                            )
                        )
                    }
                }


                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.Icon.xs).clickable(
                        onClick = onMaterialRemoveClick
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
                            color = SurfaceOverlay,
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
                            onValueChange = { onMaterialQuantityChange(it) },
                            modifier = Modifier,
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,

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
                        text = selectedMaterial.unit,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        ),
                        modifier = Modifier.padding(dimens.Padding.sm)
                    )
                }
            }
        }
    }

}