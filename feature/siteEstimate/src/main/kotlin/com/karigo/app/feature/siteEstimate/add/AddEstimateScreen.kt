package com.karigo.app.feature.siteEstimate.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigo.app.feature.siteEstimate.component.AddMaterialCard
import com.karigo.app.feature.siteEstimate.component.EstimateTotalCard
import com.karigojobs.ui.common.SelectMaterialModal
import com.karigo.app.feature.siteEstimate.component.SelectedMaterialSection
import com.karigojobs.presentation.estimate.add.EstimateEffect
import com.karigojobs.presentation.estimate.add.SiteEstimateEvent
import com.karigojobs.presentation.estimate.add.SiteEstimateState
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.common.ClientPicker
import com.karigojobs.ui.common.ContactPicker
import com.karigojobs.ui.common.CreateMaterialModal
import com.karigojobs.ui.common.KarigoDataPicker
import com.karigojobs.ui.common.KarigoDatePickerSheet
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.SectionWithTitle
import com.karigojobs.ui.common.SpringToggle
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.permission.AppPermission
import com.karigojobs.ui.permission.PermissionRationaleDialog
import com.karigojobs.ui.permission.rememberPermissionHandler
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource
import karigojobs.shared.resources.generated.resources.*


/**
 * @author hazratummar
 * Created on 06/06/26
 */


@Composable
fun AddEstimateScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    event: (SiteEstimateEvent) -> Unit,
    state: SiteEstimateState,
    effect: SharedFlow<EstimateEffect>?
) {
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                EstimateEffect.NavigationBack -> {
                    onBackClick()
                    val msg = org.jetbrains.compose.resources.getString(Res.string.estimate_add_toast_added)
                    snackbarState.showSnackbar(
                        message = msg,
                        withDismissAction = true
                    )
                }

                is EstimateEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    val contactPermission = rememberPermissionHandler(
        permission = AppPermission.Contact,
        onGranted = { event(SiteEstimateEvent.ToggleContactPicker(true)) },
        onDenied = { },
        onPermanentlyDenied = { }
    )

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        PermissionRationaleDialog(
            handlerState = contactPermission,
            onDismiss = { showDialog = false }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        modifier = modifier,
        topBar = {
            KarigoMiddleTextTopAppBar(
                onNavigationClick = onBackClick,
                title = if (state.estimateId == null) stringResource(Res.string.estimate_add_title_new) else stringResource(Res.string.estimate_add_title_edit),
                action = {

                }
            )
        }
    ) { paddingValues ->

        if (state.isMaterialPickerOpen) {
            SelectMaterialModal(
                onDismiss = { event(SiteEstimateEvent.ToggleMaterialPicker(false)) },
                availableMaterials = state.availableMaterials,
                tradeTypes = state.tradeTypes?.toList(),
                selectedTradeType = state.selectedTradeType,
                onTradeTypeSelected = { event(SiteEstimateEvent.SelectTradeType(it)) },
                materialCategories = state.materialCategories,
                selectedCategory = state.selectedCategory,
                onCategorySelected = { event(SiteEstimateEvent.SelectCategory(it)) },
                searchQuery = state.materialQuery,
                onSearchQueryChanged = { event(SiteEstimateEvent.SearchMaterials(it)) },
                selectedMaterialIds = state.selectedMaterials.map { it.materialId }.toSet(),
                onConfirmClick = { event(SiteEstimateEvent.AddMaterials(it)) },
                onNewMaterialClick = {
                    event(SiteEstimateEvent.ToggleCreateMaterialModal(true))
                }
            )
        }

        if (state.isCreateMaterialModalOpen) {
            CreateMaterialModal(
                onDismiss = { event(SiteEstimateEvent.ToggleCreateMaterialModal(false)) },
                initialTradeType = state.selectedTradeType ?: state.tradeTypes?.firstOrNull(),
                availableTrades = state.tradeTypes?.toList() ?: TradeType.entries,
                existingCategories = state.materialCategories,
                onSaveAndAdd = { name, trade, category, price, unit ->
                    event(
                        SiteEstimateEvent.CreateAndAddMaterial(
                            name = name,
                            tradeType = trade,
                            categoryName = category,
                            price = price,
                            unit = unit
                        )
                    )
                }
            )
        }

        if (state.isDatePickerOpen) {
            KarigoDatePickerSheet(
                selectedDateMillis = state.selectedDate,
                onDateSelected = { event(SiteEstimateEvent.SelectDate(it)) },
                onDismiss = {
                    event(SiteEstimateEvent.ToggleDatePicker(false))
                }
            )
        }

        if (state.isContactPickerOpen) {
            ContactPicker(
                modifier = Modifier,
                onContactClick = { event(SiteEstimateEvent.SelectClient(it)) },
                contacts = state.contacts,
                isLoading = state.isLoading,
                onDismiss = {
                    event(SiteEstimateEvent.ToggleContactPicker(false))
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = dimens.Padding.base)
                .contentHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item {
                SectionWithTitle(
                    title = stringResource(Res.string.estimate_add_section_title)
                ) {
                    KarigojobsTextField(
                        value = state.projectTitle,
                        onValueChange = { event(SiteEstimateEvent.ProjectTitleChange(it)) },
                        placeholder = stringResource(Res.string.estimate_add_placeholder_title)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    SectionWithTitle(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        title = stringResource(Res.string.estimate_add_section_client)
                    ) {
                        ClientPicker(
                            modifier = Modifier.fillMaxHeight(),
                            onCardClick = {
                                if (contactPermission.isGranted) {
                                    event(SiteEstimateEvent.ToggleContactPicker(true))
                                } else {
                                    showDialog = true
                                }
                            },
                            selectedClient = state.selectedClient
                        )
                    }
                    SectionWithTitle(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        title = stringResource(Res.string.estimate_add_section_date)
                    ) {
                        KarigoDataPicker(
                            modifier = Modifier.fillMaxHeight(),
                            selectedDateMillis = state.selectedDate,
                            onClick = { event(SiteEstimateEvent.ToggleDatePicker(true)) }
                        )
                    }
                }
            }

            item {
                SectionWithTitle(
                    modifier = Modifier
                        .fillMaxHeight(),
                    title = stringResource(Res.string.estimate_add_section_notes)
                ) {

                    KarigojobsTextField(
                        value = state.siteNotes,
                        onValueChange = { event(SiteEstimateEvent.SiteNoteChange(it)) },
                        placeholder = stringResource(Res.string.estimate_add_placeholder_notes),
                        singleLine = false,
                        maxLines = 3
                    )

                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = dimens.Space._5xl),
                    shape = KarigojobsShapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = appColor.cardColors
                    ),
                    border = customCardBorder()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(dimens.Padding.base)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(Res.string.estimate_add_label_show_rate),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            val labelText =
                                if (state.isRateVisible) stringResource(Res.string.estimate_add_desc_rate_visible) else stringResource(Res.string.estimate_add_desc_rate_hidden)

                            Text(
                                text = labelText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = appColor.secondaryText
                                )
                            )
                        }

                        SpringToggle(
                            checked = state.isRateVisible,
                            onCheckedChange = { event(SiteEstimateEvent.ToggleRateVisibility(it)) }
                        )
                    }
                }
            }

            item {
                SectionWithTitle(
                    modifier = Modifier
                        .fillMaxHeight(),
                    title = stringResource(Res.string.estimate_add_section_materials)
                ) {

                    state.selectedMaterials.forEachIndexed { index, material ->
                        key(material.id) {
                            SelectedMaterialSection(
                                selectedMaterial = material,
                                isRateVisible = state.isRateVisible,
                                number = index + 1,
                                onMaterialQuantityChange = { quantity ->
                                    event(
                                        SiteEstimateEvent.ChangeMaterialQuantity(
                                            id = material.id,
                                            quantity = quantity
                                        )
                                    )
                                },
                                onMaterialRateChange = { rate ->
                                    event(
                                        SiteEstimateEvent.ChangeMaterialRate(
                                            id = material.id,
                                            rate = rate
                                        )
                                    )
                                },
                                onMaterialMinusClick = {  event(SiteEstimateEvent.DecreaseMaterialQuantity(id = material.id)) },
                                onMaterialPlusClick = {
                                    event(SiteEstimateEvent.IncreaseMaterialQuantity(id = material.id))
                                },
                                onMaterialRemoveClick = {
                                    event(SiteEstimateEvent.DeleteMaterial(id = material.id))
                                },
                            )
                        }
                    }

                    AddMaterialCard(
                        onClick = {
                            event(SiteEstimateEvent.ToggleMaterialPicker(true))
                        }
                    )
                }
            }

            if (state.isRateVisible) {
                item {

                    EstimateTotalCard(
                        totalItemSize = state.selectedMaterials.size,
                        estimateTotal = state.materialsTotal
                    )
                }
            }

            item {
                Button(
                    onClick = { event(SiteEstimateEvent.SaveEstimate) },
                    enabled = state.canSave,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = KarigojobsShapes.medium
                ) {
                    Text(
                        text = if (state.estimateId == null) stringResource(Res.string.estimate_add_btn_save) else stringResource(Res.string.estimate_add_btn_update)
                    )
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space._8xl))
            }
        }
    }
}