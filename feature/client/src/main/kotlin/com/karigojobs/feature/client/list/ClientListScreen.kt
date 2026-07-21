package com.karigojobs.feature.client.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.karigojob.share.utils.formatNumber
import com.karigojob.share.utils.toInitials
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.client.list.ClientListEffect
import com.karigojobs.presentation.client.list.ClientListEvent
import com.karigojobs.presentation.client.list.ClientListState
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customBorder
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsError
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_billed_amount
import karigojobs.shared.resources.generated.resources.common_clients_count
import karigojobs.shared.resources.generated.resources.common_jobs_count
import karigojobs.shared.resources.generated.resources.nav_clients
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 23/06/26
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientListScreen(
    state: ClientListState,
    event: (ClientListEvent) -> Unit,
    effect: SharedFlow<ClientListEffect>?,
    onClientClick: (String) -> Unit
) {

    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is ClientListEffect.ShowError -> {
                    snackbarHost.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
        topBar = {
            KarigoTopAppBar(
                title = stringResource(Res.string.nav_clients),
                action = {
                    Text(
                        text = stringResource(Res.string.common_clients_count, state.clients.size),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText3
                        )
                    )
                },
                isNavBack = false
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = dimens.Padding.base)
                .contentHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            items(state.clients, key = {it.id}) { client ->
                Card(
                    onClick = { onClientClick(client.id) },
                    colors = CardDefaults.cardColors(
                        containerColor = appColor.cardColors
                    ),
                    shape = KarigojobsShapes.medium,
                    border = customCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(dimens.Padding.base)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(dimens.Height.minTouch)
                                    .padding(dimens.Padding._2xs)
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),
                                        shape = CircleShape
                                    )
                            ) {
                                Text(
                                    text = client.name.toInitials(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = KarigojobsAccent
                                    )
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = client.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = appColor.primaryText,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = client.phone,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                            }
                            if (client.outStandingBalance > 0.0) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .customBorder(shape = CircleShape)
                                ) {
                                    Text(
                                        text = "${deviceInfo.currency} ${client.outStandingBalance.toLocaleString()}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = KarigojobsError
                                        ),
                                        modifier = Modifier.padding(dimens.Padding.sm)
                                    )
                                }
                            }
                        }

                        HorizontalDivider()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.job_line),
                                contentDescription = null,
                                tint = appColor.tertiaryText,
                                modifier = Modifier.size(dimens.Icon._2xs)
                            )
                            Text(
                                text = stringResource(Res.string.common_jobs_count, client.totalJob),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = appColor.tertiaryText
                                )
                            )
                            Spacer(Modifier.height(dimens.Space._2xs))
                            Icon(
                                painter = painterResource(R.drawable.alumuniam),
                                contentDescription = null,
                                tint = appColor.tertiaryText,
                                modifier = Modifier.size(dimens.Icon._2xs)
                            )
                            Text(
                                text = "${deviceInfo.currency} ${stringResource(Res.string.common_billed_amount, client.totalRevenue.toLocaleString())}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = appColor.tertiaryText
                                )
                            )
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(dimens.Padding.screenV))
            }
        }
    }
}

