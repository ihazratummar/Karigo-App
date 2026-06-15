package com.karigo.app.feature.siteEstimate.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karigo.app.feature.siteEstimate.component.EstimateCard
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.estimate.list.EstimateListEffect
import com.karigojobs.presentation.estimate.list.EstimateListEvent
import com.karigojobs.presentation.estimate.list.EstimateListState
import com.karigojobs.ui.common.DeleteDialog
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigoMiddleTextTopAppBar
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.dimens
import kotlinx.coroutines.flow.SharedFlow


/**
 * @author hazratummar
 * Created on 09/06/26
 */


@Composable
fun EstimateListScreen(
    state: EstimateListState,
    event: (EstimateListEvent) -> Unit,
    effect: SharedFlow<EstimateListEffect>?,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onEstimateClick: (String) -> Unit
) {


    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                is EstimateListEffect.ShowError -> {
                    snackBarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarState)
        },
        topBar = {
            KarigoMiddleTextTopAppBar(
                onNavigationClick = { onBackClick() },
                title = "Site Estimate",
                action = {
                    KarigoIconWIthBgCick(
                        icon = R.drawable.add,
                        iconColor = MaterialTheme.colorScheme.onPrimary,
                        iconBackGroundColor = KarigojobsIconColor,
                        onClick = onAddClick
                    )
                }
            )
        }
    ) { paddingValues ->
        if (state.isDeleting) {
            DeleteDialog(
                onCancelClick = {
                    event(
                        EstimateListEvent.ToggleDelete(
                            isOpen = false,
                            id = null
                        )
                    )
                },
                onConfirmClick = {
                    state.workingEstimateId?.let { id ->
                        event(EstimateListEvent.DeleteEstimate(id = id))
                    }
                }
            )
        }

        if (state.estimates.isEmpty()) {
            Column(
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KarigoIconWIthBg(

                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .contentHorizontalPadding()
                .padding(top = dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            if (state.estimates.size >= 10) {
                item {
                    KarigojobsSearchField(
                        query = state.estimateQuery,
                        onQueryChange = {
                            event(EstimateListEvent.SearchEstimate(it))
                        }
                    )
                }
            }

            items(state.estimates) { estimate ->
                EstimateCard(
                    estimate = estimate,
                    onDeleteClick = {
                        event(EstimateListEvent.ToggleDelete(isOpen = true, id = estimate.id))
                    },
                    onEstimateClick = { onEstimateClick(estimate.id) }
                )
            }
        }
    }
}