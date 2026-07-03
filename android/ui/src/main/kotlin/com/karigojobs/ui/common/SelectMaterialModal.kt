package com.karigojobs.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMaterialModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String = "Select Materials",
    availableMaterials: List<MaterialsModel>,
    tradeTypes: List<TradeType>?,
    selectedTradeType: TradeType?,
    onTradeTypeSelected: (TradeType?) -> Unit,
    materialCategories: List<MaterialCategoryModel>,
    selectedCategory: MaterialCategoryModel?,
    onCategorySelected: (MaterialCategoryModel?) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    selectedMaterialIds: Set<String>,
    onConfirmClick: (List<String>) -> Unit
) {
    val selectedMaterials = remember(selectedMaterialIds) {
        mutableStateOf(selectedMaterialIds.toList())
    }
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {newValue ->
            newValue != SheetValue.Hidden
        }
    )

    ModalBottomSheet(
        sheetState = modalSheetState,
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = appColor.modalColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimens.Height.minTouch * 1.5f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.contentHorizontalPadding()
                    )

                    KarigoIconWIthBgCick(
                        icon = R.drawable.close,
                        iconColor = KarigojobsText3,
                        onClick = onDismiss
                    )
                }

                HorizontalDivider()

                KarigojobsSearchField(
                    modifier = Modifier.contentHorizontalPadding(),
                    query = searchQuery,
                    onQueryChange = onSearchQueryChanged,
                    placeholder = "Search Materials"
                )

                LazyRow(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    if (tradeTypes != null) {
                        item {
                            val isSelected = selectedTradeType == null
                            Card(
                                onClick = {
                                    onTradeTypeSelected(null)
                                    onCategorySelected(null)
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = "All",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else appColor.secondaryText
                                    ),
                                    modifier = Modifier.padding(
                                        horizontal = dimens.Padding.base,
                                        vertical = dimens.Padding.sm
                                    )
                                )
                            }
                        }

                        items(tradeTypes.toList(), key = {it.name}) { trade ->
                            val isSelected = trade == selectedTradeType
                            Card(
                                onClick = {
                                    onTradeTypeSelected(trade)
                                    onCategorySelected(null)
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = trade.displayName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else appColor.secondaryText
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

                if (selectedTradeType != null) {
                    LazyRow(
                        modifier = Modifier
                            .contentHorizontalPadding()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        item {
                            val isSelected = selectedCategory == null
                            Card(
                                onClick = { onCategorySelected(null) },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large
                            ) {
                                Text(
                                    text = "All",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else appColor.secondaryText
                                    ),
                                    modifier = Modifier.padding(
                                        horizontal = dimens.Padding.base,
                                        vertical = dimens.Padding.sm
                                    )
                                )
                            }
                        }

                        item {
                            val isSelected = selectedCategory?.id == "uncategorized"
                            Card(
                                onClick = {
                                    onCategorySelected(
                                        MaterialCategoryModel(
                                            id = "uncategorized",
                                            name = "Uncategorized",
                                            tradeType = selectedTradeType
                                        )
                                    )
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = "Uncategorized",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else appColor.secondaryText
                                    ),
                                    modifier = Modifier.padding(
                                        horizontal = dimens.Padding.base,
                                        vertical = dimens.Padding.sm
                                    )
                                )
                            }
                        }

                        items(materialCategories , key = {it.id}) { category ->
                            val isSelected = category.id == selectedCategory?.id
                            Card(
                                onClick = { onCategorySelected(category) },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else appColor.secondaryText
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

                LazyColumn(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    items(availableMaterials) { material ->
                        val checked = material.id in selectedMaterials.value
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (!checked) appColor.iconBgColor else appColor.accentBg
                            ),
                            border = if (checked) customCardBorder() else null,
                            onClick = {
                                if (checked) {
                                    selectedMaterials.value = selectedMaterials.value - material.id
                                } else {
                                    selectedMaterials.value = selectedMaterials.value + material.id
                                }
                            }
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
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            selectedMaterials.value = selectedMaterials.value + material.id
                                        } else {
                                            selectedMaterials.value = selectedMaterials.value - material.id
                                        }
                                    }
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(dimens.Padding.base)
            ) {
                Button(
                    onClick = {
                        onConfirmClick(selectedMaterials.value)
                        onDismiss()
                    },
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
