package com.karigojobs.app.feature.job.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.karigojob.share.utils.formatNumber
import com.karigojobs.app.android.ui.R
import com.karigojobs.app.feature.job.component.JobDetailsCard
import com.karigojobs.app.feature.job.component.JobDetailsClientInfo
import com.karigojobs.app.feature.job.component.JobDetailsStatusCard
import com.karigojobs.app.feature.job.component.JobStatusChangeModal
import com.karigojobs.app.feature.job.component.LabourItemList
import com.karigojobs.app.feature.job.component.MaterialItemList
import com.karigojobs.presentation.job.details.JobDetailsEffect
import com.karigojobs.presentation.job.details.JobDetailsIntent
import com.karigojobs.presentation.job.details.JobDetailsState
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow


/**
 * @author hazratummar
 * Created on 26/05/26
 */

@Composable
fun JobDetailsScreen(
    modifier: Modifier = Modifier,
    jobDetailsState: JobDetailsState,
    jobDetailsEffect: SharedFlow<JobDetailsEffect>?,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit,
    event: (JobDetailsIntent) -> Unit
) {

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        jobDetailsEffect?.collect { effect ->
            when (effect) {
                is JobDetailsEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                JobDetailsEffect.NavigationBack -> {
                    onBackClick()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        topBar = {
            KarigoMiddleTextTopAppBar(
                onNavigationClick = { onBackClick() },
                title = "Job Details",
                action = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(-dimens.Space.sm)
                    ) {

                        KarigoIconWIthBgCick(
                            icon = R.drawable.edit,
                            iconColor = KarigojobsText3,
                            size = dimens.Height.minTouch / 0.9f,
                            onClick = {
                                jobDetailsState.jobModel?.id?.let { onEditClick(it) }
                            }
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.delete,
                            iconColor = MaterialTheme.colorScheme.error,
                            iconBackGroundColor = Color.Transparent,
                            isBorder = true,
                            onClick = {
                                event(JobDetailsIntent.DeletePopUpOpen(isOpen = true))
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        jobDetailsState.jobModel?.let { jobModel ->

            if (jobDetailsState.isDeletePopUpOpen){
                DeleteDialog(
                    onCancelClick = {event(JobDetailsIntent.DeletePopUpOpen(isOpen = false))},
                    onConfirmClick = {
                        event(JobDetailsIntent.DeleteJob(jobId = jobModel.id))
                    }
                )
            }

            if (jobDetailsState.isJobStatusModalOpen) {
                JobStatusChangeModal(
                    onDismiss = {
                        event(JobDetailsIntent.ToggleJobStatusModal(false))
                    },
                    onStatusClick = {
                        event(JobDetailsIntent.ChangeJobStatus(id = jobModel.id, jobStatus = it))
                    },
                    jobStatus = jobModel.status
                )
            }

            LazyColumn(
                modifier = modifier
                    .padding(paddingValues)
                    .padding(horizontal = dimens.Padding.base)
                    .padding(top = dimens.Padding.base),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                item {
                    JobDetailsCard(
                        job = jobModel
                    )
                }

                item {
                    JobDetailsStatusCard(
                        job = jobModel,
                        onChangeClick = {
                            event(JobDetailsIntent.ToggleJobStatusModal(isOpen = true))
                        }
                    )
                }
                jobDetailsState.clientModel?.let { clientModel ->
                    item {
                        JobDetailsClientInfo(clientModel = clientModel)
                    }
                }

                if (jobDetailsState.jobLabourItems.isNotEmpty()) {
                    item {
                        LabourItemList(
                            labourItems = jobDetailsState.jobLabourItems,
                            total = jobDetailsState.labourTotal
                        )
                    }
                }
                if (jobDetailsState.jobMaterialItems.isNotEmpty()) {
                    item {
                        MaterialItemList(
                            materialItems = jobDetailsState.jobMaterialItems,
                            total = jobDetailsState.materialTotal
                        )
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = appColor.accentBg
                        ),
                        shape = KarigojobsShapes.medium,
                        border = customCardBorder()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(dimens.Padding.base)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Grand Total",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                                Text(
                                    text = "Inc. labour & materials",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                            }

                            Text(
                                text = "${deviceInfo.currency}${jobDetailsState.jobModel?.total?.formatNumber()}",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = KarigojobsIconColor
                                )
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
}