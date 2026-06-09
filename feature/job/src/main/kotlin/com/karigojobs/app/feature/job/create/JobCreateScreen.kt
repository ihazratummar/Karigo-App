package com.karigojobs.app.feature.job.create

import androidx.compose.foundation.layout.Arrangement
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
import com.karigojobs.app.feature.job.component.MaterialLibraryModal
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
import kotlinx.coroutines.flow.SharedFlow


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
                action = {
                    CanSaveButton(
                        onAction = {
                            onIntent(AddJobIntent.SaveJob)
                        },
                        canSave = addJobState.canContinue
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

        if (addJobState.isMaterialLibraryModalOpen) {
            MaterialLibraryModal(
                onDismiss = {
                    onIntent(AddJobIntent.ToggleMaterialLibrary(false))
                },
                addJobState = addJobState,
                onIntent = onIntent
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
                        text = "CLIENT",
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
                )
            }

            item {
                AddMaterialItemSection(
                    onClick = {
                        onIntent(AddJobIntent.ToggleMaterialLibrary(true))
                    },
                    materialsItems = addJobState.selectedMaterials,
                    onRemoveMaterialItemClick = { onIntent(AddJobIntent.RemoveMaterial(it)) }
                )
            }

            item {
                TotalScreenCard(addJobState = addJobState)
            }

        }
    }

}