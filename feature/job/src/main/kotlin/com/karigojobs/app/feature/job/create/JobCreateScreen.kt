package com.karigojobs.app.feature.job.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigojobs.app.feature.job.component.AddJobLabourItemSection
import com.karigojobs.app.feature.job.component.AddMaterialItemSection
import com.karigojobs.app.feature.job.component.CanSaveButton
import com.karigojobs.app.feature.job.component.CreateLabourItemModal
import com.karigojobs.app.feature.job.component.JobTitleSection
import com.karigojobs.ui.common.SelectMaterialModal
import com.karigojobs.app.feature.job.component.SelectTradeSection
import com.karigojobs.app.feature.job.component.TotalScreenCard
import com.karigojobs.presentation.job.create.AddJobEffect
import com.karigojobs.presentation.job.create.AddJobIntent
import com.karigojobs.presentation.job.create.AddJobState
import com.karigojobs.ui.common.ClientPicker
import com.karigojobs.ui.common.ContactPicker
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.permission.AppPermission
import com.karigojobs.ui.permission.PermissionRationaleDialog
import com.karigojobs.ui.permission.rememberPermissionHandler
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_btn_save
import karigojobs.shared.resources.generated.resources.common_btn_update
import karigojobs.shared.resources.generated.resources.job_edit_job
import karigojobs.shared.resources.generated.resources.job_new_job
import karigojobs.shared.resources.generated.resources.nav_clients
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 23/05/26
 */


@Composable
fun JobCreateScreen(
    modifier: Modifier = Modifier,
    addJobState: AddJobState = AddJobState(),
    onIntent: (AddJobIntent) -> Unit = {},
    onBackClick: () -> Unit = {},
    addJobEffect: SharedFlow<AddJobEffect>?
) {

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        addJobEffect?.collect { effect ->
            when (effect){
                AddJobEffect.NavigateBack -> {
                    onBackClick()
                }
                is AddJobEffect.ShowError -> {
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
        onGranted = { onIntent(AddJobIntent.ToggleClientPicker(true)) },
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
        topBar = {
            KarigoMiddleTextTopAppBar(
                onNavigationClick = onBackClick,
                title = if (addJobState.jobId == null) stringResource(Res.string.job_new_job) else stringResource(Res.string.job_edit_job),
                action = {
                    CanSaveButton(
                        onAction = {
                            onIntent(AddJobIntent.SaveJob)
                        },
                        canSave = addJobState.canContinue,
                        text = if (addJobState.jobId == null) stringResource(Res.string.common_btn_save) else stringResource(Res.string.common_btn_update)
                    )
                }
            )
        }
    ) { paddingValues ->

        if (addJobState.isClientPickerModalOpen) {
            ContactPicker(
                modifier = Modifier,
                onContactClick = { onIntent(AddJobIntent.SelectClient(it)) },
                contacts = addJobState.contacts,
                isLoading = addJobState.isLoading,
                onDismiss = {
                    onIntent(AddJobIntent.ToggleClientPicker(false))
                }
            )
        }

        if (addJobState.isLabourCreateModalOpen){
            CreateLabourItemModal(
                onDismiss = {
                    onIntent(AddJobIntent.LabourItemModalOpen(false))
                },
                onAddClick = {
                    onIntent(
                        AddJobIntent.AddLabourItem(it)
                    )
                }
            )
        }

        if (addJobState.isMaterialPickerOpen) {
            SelectMaterialModal(
                onDismiss = { onIntent(AddJobIntent.ToggleMaterialPicker(false)) },
                availableMaterials = addJobState.availableMaterials,
                tradeTypes = addJobState.tradeTypes,
                selectedTradeType = addJobState.selectedMaterialTradeType,
                onTradeTypeSelected = { onIntent(AddJobIntent.SelectMaterialTradeType(it)) },
                materialCategories = addJobState.materialCategories,
                selectedCategory = addJobState.selectedMaterialCategory,
                onCategorySelected = { onIntent(AddJobIntent.SelectMaterialCategory(it)) },
                searchQuery = addJobState.materialQuery,
                onSearchQueryChanged = { onIntent(AddJobIntent.SearchMaterials(it)) },
                selectedMaterialIds = addJobState.selectedMaterials.mapNotNull { it.materialId }.toSet(),
                onConfirmClick = { onIntent(AddJobIntent.AddMaterials(it)) }
            )
        }


        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = dimens.Padding.base)
                .padding(top = dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space._2base)
        ) {


            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(Res.string.nav_clients).uppercase(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(Modifier.height(dimens.Space.base))

                    ClientPicker(
                        onCardClick = {
                            if (contactPermission.isGranted) {
                                onIntent(AddJobIntent.ToggleClientPicker(true))
                            } else {
                                showDialog = true
                            }
                        },
                        selectedClient = addJobState.selectedClient
                    )
                }
            }

            item {
                JobTitleSection(
                    title = addJobState.title,
                    onTitleChange = {
                        onIntent(AddJobIntent.UpdateTitle(it))
                    }
                )
            }

            item {
                SelectTradeSection(
                    savedTrades = addJobState.tradeTypes,
                    selectedTradeType = addJobState.selectedTradeType,
                    onTradeTypeSelect = {
                        onIntent(AddJobIntent.SelectTradeType(it))
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = dimens.Padding.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Include Labour in Invoice",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    com.karigojobs.ui.common.SpringToggle(
                        checked = addJobState.includeLabourInInvoice,
                        onCheckedChange = {
                            onIntent(AddJobIntent.ToggleIncludeLabourInInvoice(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(dimens.Space.sm))
                AddJobLabourItemSection(
                    onClick = {
                        onIntent(AddJobIntent.LabourItemModalOpen(true))
                    },
                    labourItems = addJobState.labourItems,
                    onRemoveLabourItemClick = {
                        onIntent(AddJobIntent.RemoveLabourItem(it))
                    },
                    onQuantityAddClick = {
                        onIntent(AddJobIntent.IncreaseLabourItemQuantity(itemId = it))
                    },
                    onQuantityMinusClick = {  onIntent(AddJobIntent.MinusLabourItemQuantity(itemId = it)) },
                    onWorkersAddClick = { onIntent(AddJobIntent.IncreaseLabourItemWorkersCount(itemId = it)) },
                    onWorkersMinusClick = { onIntent(AddJobIntent.MinusLabourItemWorkersCount(itemId = it)) },
                    onViewLogsClick = { onIntent(AddJobIntent.ViewLabourLogs(itemId = it)) }
                )
            }

            item {
                AddMaterialItemSection(
                    onClick = {
                        onIntent(AddJobIntent.ToggleMaterialPicker(true))
                    },
                    materialsItems = addJobState.selectedMaterials,
                    onMaterialQuantityChange = { id, qty ->
                        onIntent(AddJobIntent.ChangeMaterialQuantity(id, qty))
                    },
                    onMaterialRateChange = { id, rate ->
                        onIntent(AddJobIntent.ChangeMaterialRate(id, rate))
                    },
                    onMaterialMinusClick = { onIntent(AddJobIntent.MinusMaterialQuantity(it)) },
                    onMaterialPlusClick = { onIntent(AddJobIntent.IncreaseMaterialQuantity(it)) },
                    onRemoveMaterialItemClick = { onIntent(AddJobIntent.RemoveMaterial(it)) }
                )
            }

            item {
                TotalScreenCard(addJobState = addJobState)
            }

        }
    }

    if (addJobState.isLabourLogsModalOpen) {
        val selectedUnit = addJobState.labourItems.find { it.id == addJobState.selectedLabourItemId }?.unit ?: "point"
        com.karigojobs.app.feature.job.component.LabourLogsModal(
            logs = addJobState.selectedLabourLogs,
            itemUnit = selectedUnit,
            onDismiss = { onIntent(AddJobIntent.CloseLabourLogsModal) }
        )
    }
}