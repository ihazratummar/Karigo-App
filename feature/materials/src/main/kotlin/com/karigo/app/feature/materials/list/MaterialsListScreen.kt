package com.karigo.app.feature.materials.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.materials.list.MaterialListEvent
import com.karigojobs.presentation.materials.list.MaterialListState
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.TopBarTitle
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 04/06/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialsListScreen(
    modifier: Modifier = Modifier,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
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
                        iconBackGroundColor = KarigojobsAccent,
                        iconColor = MaterialTheme.colorScheme.onPrimary,
                        size = dimens.Icon._2xl,
                    )
                },
                windowInsets = WindowInsets(),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
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

        if (state.isEditMaterialModalOpen){
            MaterialEditModal(
                onDismiss = { event(MaterialListEvent.ToggleEditMaterialModal(materialId = null, isEditing = false)) },
                state = state,
                event = event
            )
        }

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
                    event = event
                )
            }

            items(state.materialsList) { material ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = KarigojobsCard
                    ),
                    shape = KarigojobsShapes.large,
                    border = customCardBorder()
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
                            iconColor = KarigojobsAccent,
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
                            Text(
                                text = "${material.tradeType.displayName} · ${deviceInfo.currency}${material.price} / ${material.unit}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = KarigojobsText2
                                )
                            )
                        }

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
            item {
                Spacer(Modifier.height(dimens.Space._8xl))
            }
        }

    }

}


@Composable
fun SearchAndFilter(
    modifier: Modifier = Modifier,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit
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
                            containerColor = if (isSelected) KarigojobsAccent else KarigojobsCard
                        ),
                        shape = KarigojobsShapes.large,
                        border = customCardBorder()
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
                            containerColor = if (isSelected) KarigojobsAccent else KarigojobsCard
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
}