package com.karigojobs.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.materials_btn_save_and_add
import karigojobs.shared.resources.generated.resources.materials_category_text_field_placeholder
import karigojobs.shared.resources.generated.resources.materials_label_category_optional
import karigojobs.shared.resources.generated.resources.materials_label_material_name
import karigojobs.shared.resources.generated.resources.materials_label_price
import karigojobs.shared.resources.generated.resources.materials_label_trade
import karigojobs.shared.resources.generated.resources.materials_label_unit
import karigojobs.shared.resources.generated.resources.materials_material_name_text_field_placeholder
import karigojobs.shared.resources.generated.resources.materials_new_material_subtitle
import karigojobs.shared.resources.generated.resources.materials_new_material_title
import org.jetbrains.compose.resources.stringResource

private val COMMON_UNITS = listOf("ea", "mtr", "kg", "litre", "sq ft", "set", "pair", "roll", "pack", "bag", "box", "sq m")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateMaterialModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    initialTradeType: TradeType? = null,
    availableTrades: List<TradeType> = TradeType.entries,
    existingCategories: List<MaterialCategoryModel> = emptyList(),
    onSaveAndAdd: (name: String, tradeType: TradeType, categoryName: String?, price: Double, unit: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedTrade by remember(initialTradeType) {
        mutableStateOf(initialTradeType ?: availableTrades.firstOrNull() ?: TradeType.PLUMBER)
    }
    var categoryInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var unitInput by remember { mutableStateOf("ea") }

    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newValue ->
            newValue != SheetValue.Hidden
        }
    )

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = appColor.modalColor,
        sheetState = modalSheetState
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .contentHorizontalPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.materials_new_material_title),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = stringResource(Res.string.materials_new_material_subtitle),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = appColor.secondaryText
                            )
                        )
                    }
                    KarigoIconWIthBgCick(
                        icon = R.drawable.close,
                        iconBackGroundColor = appColor.cardColors,
                        iconColor = appColor.secondaryText,
                        onClick = onDismiss
                    )
                }
            }

            item {
                HorizontalDivider(color = appColor.divider)
            }

            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = stringResource(Res.string.materials_label_material_name),
                        style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                    )
                    KarigojobsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = { name = it },
                        placeholder = stringResource(Res.string.materials_material_name_text_field_placeholder)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = stringResource(Res.string.materials_label_trade),
                        style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        availableTrades.forEach { trade ->
                            val isSelected = selectedTrade == trade
                            Card(
                                onClick = {
                                    selectedTrade = trade
                                    categoryInput = ""
                                },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = stringResource(trade.displayNameRes),
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
            }

            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = stringResource(Res.string.materials_label_category_optional),
                        style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                    )
                    KarigojobsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = categoryInput,
                        onValueChange = { categoryInput = it },
                        placeholder = stringResource(Res.string.materials_category_text_field_placeholder)
                    )

                    val tradeCategories = existingCategories.filter { it.tradeType == selectedTrade }
                    if (tradeCategories.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                        ) {
                            tradeCategories.forEach { category ->
                                val isSelected = categoryInput.equals(category.name, ignoreCase = true)
                                Card(
                                    onClick = { categoryInput = category.name },
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
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.materials_label_price, deviceInfo.currency),
                            style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                        )
                        KarigojobsTextField(
                            value = priceInput,
                            onValueChange = { priceInput = it },
                            placeholder = "0",
                            keyboardType = KeyboardType.Number
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Text(
                            text = stringResource(Res.string.materials_label_unit),
                            style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                        )
                        KarigojobsTextField(
                            value = unitInput,
                            onValueChange = { unitInput = it },
                            placeholder = "ea"
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        COMMON_UNITS.forEach { unitChip ->
                            val isSelected = unitInput.equals(unitChip, ignoreCase = true)
                            Card(
                                onClick = { unitInput = unitChip },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                ),
                                shape = KarigojobsShapes.large,
                                border = customCardBorder()
                            ) {
                                Text(
                                    text = unitChip,
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
            }

            item {
                HorizontalDivider(color = appColor.divider)
                Spacer(Modifier.height(dimens.Space.md))
                Button(
                    onClick = {
                        val price = priceInput.toDoubleOrNull() ?: 0.0
                        val trimmedCategory = categoryInput.trim().ifBlank { null }
                        val trimmedUnit = unitInput.trim().ifBlank { "ea" }
                        onSaveAndAdd(name.trim(), selectedTrade, trimmedCategory, price, trimmedUnit)
                    },
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    shape = KarigojobsShapes.medium,
                    enabled = name.isNotBlank()
                ) {
                    Text(
                        text = stringResource(Res.string.materials_btn_save_and_add),
                        modifier = Modifier.padding(dimens.Padding.sm)
                    )
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space._2xl))
            }
        }
    }
}
