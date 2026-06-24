package com.karigojobs.feature.client.details

import android.content.Intent
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.feature.client.component.ClientCardWithAction
import com.karigojobs.feature.client.component.EarningCard
import com.karigojobs.feature.client.component.EarningState
import com.karigojobs.presentation.client.details.ClientDetailsEffect
import com.karigojobs.presentation.client.details.ClientDetailsEvent
import com.karigojobs.presentation.client.details.ClientDetailsState
import com.karigojobs.ui.common.JobCard
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.AmountDone
import com.karigojobs.ui.theme.AmountUnpaid
import com.karigojobs.ui.theme.StatusPending
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow
import androidx.core.net.toUri
import com.karigojob.share.utils.firstName
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.theme.KarigojobsError
import com.karigojobs.ui.theme.KarigojobsShapes


/**
 * @author hazratummar
 * Created on 23/06/26
 */


@Composable
fun ClientDetailsScreen(
    state: ClientDetailsState,
    event: (ClientDetailsEvent) -> Unit,
    effect: SharedFlow<ClientDetailsEffect>?,
    onJobClick: (String) -> Unit,
    onNewJobClick: (String) -> Unit = {},
    onBackClick : () -> Unit
) {

    val snackbarState = remember { SnackbarHostState() }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is ClientDetailsEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                is ClientDetailsEffect.CallClient -> {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${effect.number}".toUri()
                    }
                    context.startActivity(intent)
                }

                is ClientDetailsEffect.OpenWhatsApp -> {
                    val number = effect.number.filter(Char::isDigit)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://wa.me/$number".toUri()
                    )
                    context.startActivity(intent)
                }

                ClientDetailsEffect.NavBack -> {
                    onBackClick()
                }
            }
        }
    }


    state.client?.let { clientModel ->
        Scaffold(
            topBar = {
                KarigoTopAppBar(
                    title = clientModel.name.firstName(),
                    action = {
                        KarigoIconWIthBgCick(
                            icon = R.drawable.delete,
                            iconColor = KarigojobsError,
                            isBorder = true,
                            iconBackGroundColor = Color.Transparent,
                            onClick = {
                                event(ClientDetailsEvent.ToggleDelete(isOpen = true))
                            }
                        )
                    },
                    onNavigationClick = onBackClick
                )
            }
        ) { paddingValues ->

            val stats = listOf(
                EarningState(
                    title = "TOTAL JOBS",
                    number = state.client?.totalJob?.toDouble() ?: 0.0,
                    color = MaterialTheme.colorScheme.onBackground,
                    isCount = true
                ),
                EarningState(
                    title = "PENDING",
                    number = 0.0,
                    color = StatusPending
                ),
                EarningState(
                    title = "PAID",
                    number = clientModel.totalPaid,
                    color = AmountDone
                ),
                EarningState(
                    title = "UNPAID",
                    number = clientModel.outStandingBalance,
                    color = AmountUnpaid
                )
            )


            if (state.isDeleting) {
                DeleteDialog(
                    onCancelClick = { event(ClientDetailsEvent.ToggleDelete(isOpen = false)) },
                    onConfirmClick = { event(ClientDetailsEvent.DeleteClient) },
                    dialogTitle = "Delete Client",
                    dialogDescription = "This will remove ${state.client?.name} and all associated job history."
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .contentHorizontalPadding()
                    .padding(top = dimens.Padding.base),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {

                item {
                    ClientCardWithAction(
                        modifier = Modifier.fillMaxWidth(),
                        client = clientModel,
                        onCallClick = { event(ClientDetailsEvent.CallClient) },
                        onWhatAppClick = { event(ClientDetailsEvent.OpenClientWhatsApp) }
                    )
                }

                item {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.base),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.base),
                        maxItemsInEachRow = 2
                    ) {

                        stats.forEach { stat ->
                            EarningCard(
                                modifier = Modifier.weight(1f),
                                title = stat.title,
                                number = stat.number,
                                color = stat.color,
                                isCount = stat.isCount
                            )
                        }
                    }
                }

                if (state.jobHistory.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Job History",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "${state.jobHistory.size} jobs",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                    items(state.jobHistory) { job ->
                        JobCard(
                            job = job,
                            onClick = { onJobClick(job.id) },
                            isClientJob = true
                        )
                    }
                }
                item {
                    Button(
                        onClick = { onNewJobClick(clientModel.id) },
                        shape = KarigojobsShapes.medium,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = null,
                            modifier = Modifier.size(dimens.Icon._2xs)
                        )
                        Spacer(Modifier.width(dimens.Space.sm))
                        Text(
                            text = "New Job for ${clientModel.name.firstName()}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(dimens.Padding.screenV))
                }

            }
        }
    }

}