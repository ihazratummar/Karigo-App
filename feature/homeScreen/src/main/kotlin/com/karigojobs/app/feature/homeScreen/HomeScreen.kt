package com.karigojobs.app.feature.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.feature.homeScreen.component.HomeFloatingActionButton
import com.karigojobs.app.feature.homeScreen.component.HomeTopAppBar
import com.karigojobs.app.feature.homeScreen.component.ScrollableTradeView
import com.karigojobs.app.feature.homeScreen.component.SiteEstimatesCard
import com.karigojobs.presentation.dashboard.HomeEffect
import com.karigojobs.presentation.dashboard.HomeState
import com.karigojobs.ui.common.ActionNeedBanner
import com.karigojobs.ui.common.JobCard
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_see_all
import karigojobs.shared.resources.generated.resources.home_recent_job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit,
    onFabClick: () -> Unit,
    onSeeAllJobClick: () -> Unit,
    homeState: HomeState,
    homeEffect: SharedFlow<HomeEffect>?,
    onJobClick : (String) -> Unit,
    navigateToAddEstimate : () -> Unit,
    onSeeAllEstimateClick : () -> Unit,
    onCompleteBannerClick : () -> Unit
) {

    val snackbarState = remember { SnackbarHostState() }
    val profile = homeState.workerProfileModel

    LaunchedEffect(Unit) {
        homeEffect?.collectLatest { effect ->
            when (effect) {
                is HomeEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        },
        modifier = modifier,
        topBar = {
            HomeTopAppBar(profile = profile)
        },
        contentWindowInsets = WindowInsets(),
        floatingActionButton = {
            HomeFloatingActionButton(
                onFabClick = onFabClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(horizontal = dimens.Padding.base)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (profile == null) {
                    ActionNeedBanner(onClick = onCompleteBannerClick)
                }
            }

            item {
                ScrollableTradeView(trades = homeState.selectedTrades)
            }

            item {
                SiteEstimatesCard(
                    onAddEstimateClick = navigateToAddEstimate,
                    onSeeAllClick = onSeeAllEstimateClick
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.home_recent_job),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Box(
                        modifier = Modifier
                            .bounceClickable(onSeeAllJobClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.common_see_all),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }

            homeState.jobs?.let { jobs ->
                items(jobs, key = {job -> job.id}) { job ->
                    JobCard(
                        job = job,
                        onClick = {onJobClick(job.id)}
                    )
                }
            }
        }
    }
}