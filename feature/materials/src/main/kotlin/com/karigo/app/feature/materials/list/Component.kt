package com.karigo.app.feature.materials.list

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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.materials.list.MaterialListEvent
import com.karigojobs.presentation.materials.list.MaterialListState
import com.karigojobs.presentation.materials.list.MaterialCategoryEvent
import com.karigojobs.presentation.materials.list.MaterialCategoryState
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.ui.common.KarigoButtons
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MaterialManageModal(
    modifier: Modifier = Modifier,
    isEditMode: Boolean,
    onDismiss: () -> Unit,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit
) {
    val title = if (isEditMode) "Edit Material" else "Add Material"
    val buttonText = if (isEditMode) "Save Changes" else "Add to Library"

    val name = if (isEditMode) state.editingMaterial?.name ?: "" else state.newMaterialName
    val price = if (isEditMode) state.editingMaterial?.price?.toString()?.replace("null", "")
        ?: "" else state.newMaterialPrice
    val unit = if (isEditMode) state.editingMaterial?.unit ?: "" else state.newMaterialUnit
    val tradeType = if (isEditMode) state.editingMaterial?.tradeType else state.newMaterialTradeType
    val categoryName =
        if (isEditMode) state.editingMaterial?.categoryName ?: "" else state.newMaterialCategoryName

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
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    KarigoIconWIthBgCick(
                        icon = R.drawable.close,
                        iconBackGroundColor = appColor.cardColors,
                        iconColor = appColor.secondaryText,
                        onClick = onDismiss
                    )
                }
            }
            item {
                HorizontalDivider()
            }
            item {
                Text(
                    text = "MATERIAL NAME",
                    style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText),
                    modifier = Modifier.contentHorizontalPadding()
                )
            }
            item {
                KarigojobsTextField(
                    modifier = Modifier.contentHorizontalPadding(),
                    value = name,
                    onValueChange = {
                        if (isEditMode) event(MaterialListEvent.EditMaterialName(it))
                        else event(MaterialListEvent.NewMaterialName(it))
                    },
                    placeholder = "e.g. PVC Pipe 1/2 inch"
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "TRADE",
                        style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                    ) {
                        state.selectTrades.forEach { trade ->
                            val isSelected = tradeType == trade
                            Card(
                                onClick = {
                                    if (isEditMode) event(
                                        MaterialListEvent.EditMaterialTradeType(
                                            trade
                                        )
                                    )
                                    else event(MaterialListEvent.NewMaterialTradeType(trade))

                                    // Clear category selection when trade changes, since categories are trade-specific
                                    if (isEditMode) event(
                                        MaterialListEvent.EditMaterialCategoryName(
                                            ""
                                        )
                                    )
                                    else event(MaterialListEvent.NewMaterialCategoryName(""))
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
            item {
                Column(
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "CATEGORY (OPTIONAL)",
                        style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
                    )
                    KarigojobsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = categoryName,
                        onValueChange = {
                            if (isEditMode) event(MaterialListEvent.EditMaterialCategoryName(it))
                            else event(MaterialListEvent.NewMaterialCategoryName(it))
                        },
                        placeholder = "e.g. Pipes, Fittings, Valves..."
                    )

                    if (state.materialCategory.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                        ) {
                            state.materialCategory.forEach { category ->
                                val isSelected =
                                    categoryName.equals(category.name, ignoreCase = true)
                                Card(
                                    onClick = {
                                        if (isEditMode) {
                                            event(
                                                MaterialListEvent.EditMaterialCategoryName(
                                                    category.name
                                                )
                                            )
                                        } else {
                                            event(MaterialListEvent.NewMaterialCategoryName(category.name))
                                        }
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) KarigojobsIconColor else appColor.cardColors
                                    ),
                                    shape = KarigojobsShapes.large,
                                    border = customCardBorder()
                                ) {
                                    Text(
                                        text = category.name,
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

                    Text(
                        text = "Type a new name to create a category, or pick an existing one above",
                        style = MaterialTheme.typography.labelSmall.copy(color = KarigojobsText2)
                    )
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
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Text(
                            text = "PRICE (${deviceInfo.currency})",
                            style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
                        )
                        KarigojobsTextField(
                            value = price,
                            onValueChange = {
                                if (isEditMode) event(MaterialListEvent.EditMaterialPrice(it))
                                else event(MaterialListEvent.NewMaterialRate(it))
                            },
                            placeholder = "0",
                            keyboardType = KeyboardType.Number
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Text(
                            text = "UNIT",
                            style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
                        )
                        KarigojobsTextField(
                            value = unit,
                            onValueChange = {
                                if (isEditMode) event(MaterialListEvent.EditMaterialUnit(it))
                                else event(MaterialListEvent.NewMaterialUnit(it))
                            },
                            placeholder = "ea, mtr, kg"
                        )
                    }
                }
            }
            item {
                HorizontalDivider()
                Spacer(Modifier.height(dimens.Space.md))
                Button(
                    onClick = {
                        if (isEditMode) event(MaterialListEvent.UpdateMaterials)
                        else event(MaterialListEvent.AddMaterial)
                    },
                    modifier = Modifier
                        .contentHorizontalPadding()
                        .fillMaxWidth(),
                    shape = KarigojobsShapes.medium,
                    enabled = if (isEditMode) true else state.canAddNewMaterial
                ) {
                    Text(
                        text = buttonText,
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategoriesModal(
    modifier: Modifier = Modifier,
    state: MaterialCategoryState,
    materialsList: List<MaterialsModel>,
    event: (MaterialCategoryEvent) -> Unit
) {
    if (!state.isManageCategoriesModalOpen) return

    val tradeType = state.selectedTradeType ?: return

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { event(MaterialCategoryEvent.ToggleManageCategoriesModal(false)) },
        containerColor = appColor.modalColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimens.Padding.screenH),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .contentHorizontalPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Manage Categories",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "${tradeType.displayName} — ${state.materialCategory.size} categories",
                        style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                    )
                }
                KarigoIconWIthBgCick(
                    icon = R.drawable.close,
                    iconBackGroundColor = appColor.cardColors,
                    iconColor = appColor.secondaryText,
                    onClick = { event(MaterialCategoryEvent.ToggleManageCategoriesModal(false)) }
                )
            }

            HorizontalDivider()

            // New Category Input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .contentHorizontalPadding(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                KarigojobsTextField(
                    modifier = Modifier.weight(1f),
                    value = state.manageCategoryNewName,
                    onValueChange = { event(MaterialCategoryEvent.ManageCategoryInputChanged(it)) },
                    placeholder = "New category name..."
                )
                KarigoIconWIthBgCick(
                    icon = R.drawable.add,
                    onClick = { event(MaterialCategoryEvent.CreateNewCategory) }
                )
            }

            // Categories List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .contentHorizontalPadding(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                items(state.materialCategory) { category ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                        shape = KarigojobsShapes.large,
                        border = customCardBorder()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimens.Padding.base),
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            KarigoIconWIthBg(
                                icon = R.drawable.stack, // Placeholder for tag icon
                                iconColor = KarigojobsIconColor,
                                iconBackGroundColor = appColor.accentBg
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            KarigoIconWIthBgCick(
                                icon = R.drawable.edit,
                                onClick = {
                                    event(
                                        MaterialCategoryEvent.ToggleRenameCategoryDialog(
                                            category
                                        )
                                    )
                                }
                            )
                            KarigoIconWIthBgCick(
                                icon = R.drawable.delete,
                                iconColor = MaterialTheme.colorScheme.error,
                                iconBackGroundColor = appColor.cardColors,
                                onClick = { event(MaterialCategoryEvent.DeleteCategory(category.id)) }
                            )
                        }
                    }

                }
            }
            Spacer(Modifier.height(dimens.Space._2xl))
        }
    }
}

@Composable
fun RenameCategoryDialog(
    modifier: Modifier = Modifier,
    state: MaterialCategoryState,
    materialsList: List<MaterialsModel>,
    event: (MaterialCategoryEvent) -> Unit
) {
    if (state.renameCategoryTarget == null) return

    val category = state.renameCategoryTarget
    val materialCount = materialsList.count { it.categoryId == category?.id }

    Dialog(
        onDismissRequest = { event(MaterialCategoryEvent.ToggleRenameCategoryDialog(null)) }
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
            shape = KarigojobsShapes.large,
            border = customCardBorder()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.Padding.lg),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                KarigoIconWIthBg(
                    icon = R.drawable.stack, // Placeholder for tag icon
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = appColor.accentBg,
                    size = dimens.Icon._2xl
                )

                Text(
                    text = "Rename Category",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "This will update all $materialCount materials in “${category?.name}”",
                    style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                )

                Spacer(modifier = Modifier.height(dimens.Space.sm))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = "CURRENT NAME",
                        style = MaterialTheme.typography.labelSmall.copy(color = appColor.secondaryText)
                    )
                    KarigojobsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = category?.name ?: "",
                        onValueChange = {},
                        readOnly = true
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = "NEW NAME",
                        style = MaterialTheme.typography.labelSmall.copy(color = appColor.secondaryText)
                    )
                    KarigojobsTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.manageCategoryNewName,
                        onValueChange = { event(MaterialCategoryEvent.RenameCategoryInputChanged(it)) }
                    )
                }

                Spacer(modifier = Modifier.height(dimens.Space.md))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    KarigoButtons(
                        modifier = Modifier.weight(1f),
                        onClick = { event(MaterialCategoryEvent.ToggleRenameCategoryDialog(null)) },
                        label = "Cancel",
                        buttonColor = Color.Transparent
                    )
                    KarigoButtons(
                        modifier = Modifier.weight(1f),
                        onClick = { event(MaterialCategoryEvent.ConfirmRenameCategory) },
                        label = "Rename",
                        buttonColor = appColor.accentBg
                    )
                }
            }
        }
    }
}
