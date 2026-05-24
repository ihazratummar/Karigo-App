package com.karigo.app.feature.job.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.karigo.app.android.ui.R
import com.karigo.app.feature.job.component.AddJobLabourItemSection
import com.karigo.app.feature.job.component.ClientInfo
import com.karigo.app.feature.job.component.ContactPicker
import com.karigo.app.feature.job.component.CreateLabourItemModal
import com.karigo.app.feature.job.component.JobTitleSection
import com.karigo.app.feature.job.component.JobTopAppBar
import com.karigo.app.feature.job.component.TotalScreenCard
import com.karigo.domain.repository.DeviceContact
import com.karigo.presentation.job.create.AddJobEffect
import com.karigo.presentation.job.create.AddJobIntent
import com.karigo.presentation.job.create.AddJobState
import com.karigo.ui.permission.AppPermission
import com.karigo.ui.permission.PermissionRationaleDialog
import com.karigo.ui.permission.rememberPermissionHandler
import com.karigo.ui.theme.KarigoCard
import com.karigo.ui.theme.dimens
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

    LaunchedEffect(Unit) {
        addJobEffect?.collect { effect ->
            when (effect){
                AddJobEffect.NavigateBack -> {
                    onBackClick()
                }
                is AddJobEffect.ShowError -> TODO()
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
        topBar = {
            JobTopAppBar(
                onAction = {
                    onIntent(AddJobIntent.SaveJob)
                },
                onNavigationClick = onBackClick,
                canSave = addJobState.canContinue
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (contactPermission.isGranted) {
                                onIntent(AddJobIntent.ToggleClientPicker(true))
                            } else {
                                showDialog = true
                            }
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = KarigoCard
                        )
                    ) {
                        if (addJobState.selectedClient == null) {
                            Row(
                                modifier = Modifier
                                    .padding(dimens.Padding.md)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.user_search),
                                    contentDescription = "User search",
                                    modifier = Modifier.size(dimens.Icon.sm),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )


                                Text(
                                    text = "Select or Search Client...",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        } else {
                            ClientInfo(
                                modifier = Modifier
                                    .padding(dimens.Padding.sm)
                                    .fillMaxWidth(),
                                deviceContact = DeviceContact(
                                    name = addJobState.selectedClient!!.name,
                                    phoneNumber = listOf(addJobState.selectedClient!!.phone)
                                )
                            )
                        }
                    }
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

            }

            item {
                TotalScreenCard(addJobState = addJobState)
            }

        }
    }

}