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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigo.app.feature.siteEstimate.component.AddMaterialCard
import com.karigo.app.feature.siteEstimate.component.EstimateTotalCard
import com.karigo.app.feature.siteEstimate.component.SelectMaterialModal
import com.karigo.app.feature.siteEstimate.component.SelectedMaterialSection
import com.karigojobs.presentation.estimate.add.EstimateEffect
import com.karigojobs.presentation.estimate.add.SiteEstimateEvent
import com.karigojobs.presentation.estimate.add.SiteEstimateState
import com.karigojobs.ui.common.ClientPicker
import com.karigojobs.ui.common.ContactPicker
import com.karigojobs.ui.common.KarigoDataPicker
import com.karigojobs.ui.common.KarigoDatePickerSheet
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.SectionWithTitle
import com.karigojobs.ui.common.SpringToggle
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.permission.AppPermission
import com.karigojobs.ui.permission.PermissionRationaleDialog
import com.karigojobs.ui.permission.rememberPermissionHandler
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow


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
                title = "New Estimate",
                action = {

                }
            )
        }
    ) { paddingValues ->

        if (state.isMaterialPickerOpen) {
            SelectMaterialModal(
                onDismiss = { event(SiteEstimateEvent.ToggleMaterialPicker(false)) },
                state = state,
                event = event
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
                    title = "PROJECT TITLE"
                ) {
                    KarigojobsTextField(
                        value = state.projectTitle,
                        onValueChange = { event(SiteEstimateEvent.ProjectTitleChange(it)) },
                        placeholder = "e.g. Bathroom Renovation"
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
                        title = "CLIENT"
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
                        title = "DATE"
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
                    title = "SITE NOTES"
                ) {

                    KarigojobsTextField(
                        value = state.siteNotes,
                        onValueChange = { event(SiteEstimateEvent.SiteNoteChange(it)) },
                        placeholder = "Any special instructions...",
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
                        containerColor = KarigojobsCard
                    )
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
                                text = "Show Rate",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            val labelText =
                                if (state.isRateVisible) "Client Sees rate & total" else "Material list only, no prices"

                            Text(
                                text = labelText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = KarigojobsText2
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
                    title = "MATERIALS"
                ) {

                    state.selectedMaterials.forEachIndexed { index, material ->
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
                            onMaterialMinusClick = {  event(SiteEstimateEvent.DecreaseMaterialQuantity(id = material.id)) },
                            onMaterialPlusClick = {
                                event(SiteEstimateEvent.IncreaseMaterialQuantity(id = material.id))
                            },
                            onMaterialRemoveClick = {
                                event(SiteEstimateEvent.DeleteMaterial(id = material.id))
                            },
                        )
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
                        text = "Save Estimate"
                    )
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space._8xl))
            }
        }
    }
}