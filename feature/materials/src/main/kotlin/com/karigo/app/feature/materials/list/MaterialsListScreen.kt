package com.karigo.app.feature.materials.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.materials.list.MaterialListEvent
import com.karigojobs.presentation.materials.list.MaterialListState
import com.karigojobs.presentation.materials.list.MaterialScreenEffect
import com.karigojobs.presentation.materials.list.MaterialCategoryState
import com.karigojobs.presentation.materials.list.MaterialCategoryEvent
import com.karigojobs.presentation.materials.list.MaterialCategoryEffect
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.TopBarTitle
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow


/**
 * @author hazratummar
 * Created on 04/06/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialsListScreen(
    modifier: Modifier = Modifier,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit,
    effect: SharedFlow<MaterialScreenEffect>?,
    categoryState: MaterialCategoryState,
    categoryEvent: (MaterialCategoryEvent) -> Unit,
    categoryEffect: SharedFlow<MaterialCategoryEffect>?
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is MaterialScreenEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        categoryEffect?.collect { effect ->
            when (effect) {
                is MaterialCategoryEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    TopBarTitle(title = "Materials")
                },
                actions = {
                    KarigoIconWIthBgCick(
                        icon = R.drawable.add,
                        iconBackGroundColor = KarigojobsIconColor,
                        iconColor = MaterialTheme.colorScheme.onPrimary,
                        size = dimens.Icon._2xl,
                        onClick = { event(MaterialListEvent.ToggleAddMaterialModal(isOpen = true)) }
                    )
                },
                windowInsets = WindowInsets(),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->


        if (state.isNewMaterialAddingModalOpen || state.isEditMaterialModalOpen) {
            MaterialManageModal(
                isEditMode = state.isEditMaterialModalOpen,
                state = state,
                event = event,
                onDismiss = {
                    if (state.isEditMaterialModalOpen) {
                        event(
                            MaterialListEvent.ToggleEditMaterialModal(
                                materialId = null,
                                isEditing = false
                            )
                        )
                    } else {
                        event(MaterialListEvent.ToggleAddMaterialModal(isOpen = false))
                    }
                }
            )
        }

        if (state.isDeleting) {
            DeleteDialog(
                onCancelClick = {
                    event(
                        MaterialListEvent.ToggleDeleteMaterialClick(
                            isDeleting = false,
                            materialId = null
                        )
                    )
                },
                onConfirmClick = {
                    state.workingMaterialId?.let {
                        event(MaterialListEvent.DeleteMaterial(materialId = it))
                    }
                },
                dialogTitle = "Delete Material",
                dialogDescription = "This action is permanent. Are you sure you want to delete this material."
            )
        }

        ManageCategoriesModal(
            state = categoryState,
            materialsList = state.materialsList,
            event = categoryEvent
        )

        RenameCategoryDialog(
            state = categoryState,
            materialsList = state.materialsList,
            event = categoryEvent
        )

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
            overscrollEffect = rememberOverscrollEffect()
        ) {
            item {
                SearchAndFilter(
                    state = state,
                    event = event,
                    categoryEvent = categoryEvent
                )
            }

            item {
                Text(
                    text = "${state.materialsList.size} items",
                    style = MaterialTheme.typography.labelSmall.copy(color = KarigojobsText2)
                )
            }

            if (state.selectedTradeType != null) {
                val groupedMaterials =
                    state.materialsList.groupBy { it.categoryName?.uppercase() ?: "UNCATEGORIZED" }

                groupedMaterials.forEach { (groupName, materials) ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = dimens.Padding.sm),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                border = BorderStroke(
                                    dimens.Border.thin,
                                    KarigojobsIconColor
                                ),
                                shape = KarigojobsShapes.small
                            ) {
                                Text(
                                    text = groupName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = KarigojobsIconColor,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(
                                        horizontal = dimens.Padding.sm,
                                        vertical = dimens.Padding._2xs
                                    )
                                )
                            }
                            Text(
                                text = materials.size.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(color = KarigojobsText2)
                            )
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                            )
                        }
                    }

                    items(materials) { material ->
                        MaterialItemRow(material = material, event = event)
                    }
                }
            } else {
                items(state.materialsList) { material ->
                    MaterialItemRow(material = material, event = event)
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space._7xl))
            }
        }

    }
}

@Composable
fun MaterialItemRow(
    material: MaterialsModel,
    event: (MaterialListEvent) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        ),
        shape = KarigojobsShapes.large,
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {

            KarigoIconWIthBg(
                icon = R.drawable.stack,
                iconColor = KarigojobsIconColor,
                iconBackGroundColor = SurfaceOverlay,
                size = dimens.Icon._2xl
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
            ) {
                Text(
                    text = material.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    if (material.categoryName != null) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = KarigojobsIconColor.copy(
                                    alpha = 0.2f
                                )
                            ),
                            border = BorderStroke(
                                dimens.Border.thin,
                                KarigojobsIconColor
                            ),
                            shape = KarigojobsShapes.small
                        ) {
                            Text(
                                text = material.categoryName!!,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = KarigojobsIconColor,
                                    fontSize = dimens.Text.xs
                                ),
                                modifier = Modifier.padding(
                                    horizontal = dimens.Padding.xs,
                                    vertical = dimens.Space._2xs
                                )
                            )
                        }
                    }
                    Text(
                        text = "${material.tradeType.displayName} · ${deviceInfo.currency}${material.price} / ${material.unit}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        )
                    )
                }
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(-dimens.Space.base)
            ) {
                KarigoIconWIthBgCick(
                    icon = R.drawable.edit,
                    iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        event(
                            MaterialListEvent.ToggleEditMaterialModal(
                                isEditing = true,
                                materialId = material.id
                            )
                        )
                    }
                )
                KarigoIconWIthBgCick(
                    icon = R.drawable.delete,
                    iconColor = MaterialTheme.colorScheme.error,
                    iconBackGroundColor = Color.Transparent,
                    onClick = {
                        event(
                            MaterialListEvent.ToggleDeleteMaterialClick(
                                isDeleting = true,
                                materialId = material.id
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SearchAndFilter(
    modifier: Modifier = Modifier,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit,
    categoryEvent: (MaterialCategoryEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
    ) {
        KarigojobsSearchField(
            modifier = Modifier,
            query = state.materialQuery,
            onQueryChange = { event(MaterialListEvent.SearchMaterial(it)) },
            placeholder = "Search Materials"
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            if (state.selectTrades.isNotEmpty()) {
                item {
                    val isSelected = state.selectedTradeType == null
                    Card(
                        onClick = {
                            event(MaterialListEvent.SelectTradeType(tradeType = null))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) KarigojobsIconColor else KarigojobsCard
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

                items(state.selectTrades.toList()) { trade ->
                    val isSelected = trade == state.selectedTradeType
                    Card(
                        onClick = {
                            event(MaterialListEvent.SelectTradeType(tradeType = trade))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) KarigojobsIconColor else KarigojobsCard
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

        if (state.selectedTradeType != null) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    val isSelected = state.selectedCategory == null
                    Card(
                        onClick = {
                            event(MaterialListEvent.SelectCategory(null))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) KarigojobsIconColor else KarigojobsCard
                        ),
                        shape = KarigojobsShapes.large
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

                item {
                    val isSelected = state.selectedCategory?.id == "uncategorized"
                    Card(
                        onClick = {
                            event(
                                MaterialListEvent.SelectCategory(
                                    MaterialCategoryModel(
                                        id = "uncategorized",
                                        name = "Uncategorized",
                                        tradeType = state.selectedTradeType!!
                                    )
                                )
                            )
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) KarigojobsIconColor else KarigojobsCard
                        ),
                        shape = KarigojobsShapes.large
                    ) {
                        Text(
                            text = "Uncategorized",
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

                items(state.materialCategory) { category ->
                    val isSelected = category.id == state.selectedCategory?.id
                    Card(
                        onClick = {
                            event(MaterialListEvent.SelectCategory(category))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) KarigojobsIconColor else KarigojobsCard
                        ),
                        shape = KarigojobsShapes.large
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

                item {
                    Card(
                        onClick = {
                            state.selectedTradeType?.let { tradeType ->
                                categoryEvent(MaterialCategoryEvent.LoadCategories(tradeType))
                                categoryEvent(MaterialCategoryEvent.ToggleManageCategoriesModal(true))
                            }
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            dimens.Border.thin,
                            KarigojobsText2
                        ),
                        shape = KarigojobsShapes.large
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                            modifier = Modifier.padding(
                                horizontal = dimens.Padding.base,
                                vertical = dimens.Padding.sm
                            )
                        ) {
                            Icon(
                                painter = androidx.compose.ui.res.painterResource(id = R.drawable.settings_line), // Replace with settings icon
                                contentDescription = "Manage",
                                modifier = Modifier.size(
                                    dimens.Icon._2xs
                                ),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Manage",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}