package com.karigo.app.feature.siteEstimate.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.karigo.app.feature.siteEstimate.component.EstimateDetailsCard
import com.karigo.app.feature.siteEstimate.component.EstimateMaterialsList
import com.karigo.app.feature.siteEstimate.component.EstimateTotalCard
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.estimate.details.EstimateDetailsEffect
import com.karigojobs.presentation.estimate.details.EstimateDetailsEvent
import com.karigojobs.presentation.estimate.details.EstimateDetailsState
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow


/**
 * @author hazratummar
 * Created on 15/06/26
 */


@Composable
fun EstimateDetailsScreen(
    modifier: Modifier = Modifier,
    state: EstimateDetailsState,
    event: (EstimateDetailsEvent) -> Unit,
    effect: SharedFlow<EstimateDetailsEffect>?,
    onBackClick: () -> Unit,
    onEditClick: (String) -> Unit
) {

    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is EstimateDetailsEffect.ShowError -> {
                    snackBarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }

                is EstimateDetailsEffect.ShareToWhatsApp -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data =
                            "https://api.whatsapp.com/send?text=${Uri.encode(effect.message)}".toUri()
                    }
                    context.startActivity(intent)
                }

                EstimateDetailsEffect.NavigationBack -> {
                    onBackClick()
                }
            }
        }
    }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        },
        topBar = {
            KarigoTopAppBar(
                onNavigationClick = { onBackClick() },
                title = "Estimate",
                action = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(-dimens.Space.sm)
                    ) {

                        KarigoIconWIthBgCick(
                            icon = R.drawable.whatsapp,
                            iconColor = Color(0xFF25D366),
                            iconBackGroundColor = Color.Transparent,
                            size = dimens.Height.minTouch / 1f,
                            onClick = {
                                event(
                                    EstimateDetailsEvent.WhatsAppShare
                                )
                            },
                            isBorder = true
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.edit,
                            iconColor = KarigojobsText2,
                            size = dimens.Height.minTouch / 0.9f,
                            onClick = {
                                state.estimateDetails?.id?.let { onEditClick(it) }
                            }
                        )
                        KarigoIconWIthBgCick(
                            icon = R.drawable.delete,
                            iconColor = MaterialTheme.colorScheme.error,
                            iconBackGroundColor = Color.Transparent,
                            size = dimens.Height.minTouch / 1f,
                            onClick = {
                                event(
                                    EstimateDetailsEvent.ToggleDelete(isOpen = true)
                                )
                            },
                            isBorder = true
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (state.isDeleting) {
            DeleteDialog(
                onCancelClick = {
                    event(
                        EstimateDetailsEvent.ToggleDelete(
                            isOpen = false
                        )
                    )
                },
                onConfirmClick = {
                    state.estimateDetails?.id?.let { id ->
                        event(EstimateDetailsEvent.DeleteEstimate(estimateId = id))
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .contentHorizontalPadding()
                .padding(top = dimens.Padding.base),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item {
                EstimateDetailsCard(state = state)
            }
            item {
                EstimateMaterialsList(state = state)
            }

            item {
                state.estimateDetails?.showRate?.let {
                    if (it) {
                        EstimateTotalCard(
                            totalItemSize = state.siteEstimateMaterial.size,
                            estimateTotal = state.estimateDetails?.total ?: 0.0
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