package com.karigojobs.feature.settings.paywall

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import com.karigojobs.app.android.ui.R
import com.karigojobs.feature.settings.paywall.component.ActiveSubscriptionView
import com.karigojobs.feature.settings.paywall.component.CancelSubscriptionModal
import com.karigojobs.feature.settings.paywall.component.EarlyAdopterBanner
import com.karigojobs.feature.settings.paywall.component.OfflineBanner
import com.karigojobs.feature.settings.paywall.component.PaywallBottomBar
import com.karigojobs.feature.settings.paywall.component.PaywallFeaturesComparison
import com.karigojobs.feature.settings.paywall.component.PaywallTabSelector
import com.karigojobs.feature.settings.paywall.component.TrustIndicators
import com.karigojobs.presentation.monetization.MonetizationEffect
import com.karigojobs.presentation.monetization.MonetizationEvent
import com.karigojobs.presentation.monetization.MonetizationViewModel
import com.karigojobs.share.model.PlanTier
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.theme.isPro
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_btn_restore
import karigojobs.shared.resources.generated.resources.paywall_subtagline
import karigojobs.shared.resources.generated.resources.paywall_tagline
import karigojobs.shared.resources.generated.resources.pro_banner_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaywallScreen(
    viewModel: MonetizationViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MonetizationEffect.ShowError -> snackbarHostState.showSnackbar(effect.message)
                is MonetizationEffect.ShowSuccess -> snackbarHostState.showSnackbar(effect.message)
                is MonetizationEffect.PurchaseSuccess -> { }
                is MonetizationEffect.LaunchGooglePlaySubscriptions -> {
                    runCatching {
                        val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    if (isPro) {
        ActiveSubscriptionView(
            state = state,
            viewModel = viewModel,
            onBackClick = onBackClick,
            modifier = modifier
        )

        if (state.isCancelDialogOpen) {
            CancelSubscriptionModal(
                onDismiss = { viewModel.onEvent(MonetizationEvent.ToggleCancelDialog(false)) },
                onConfirmCancel = {
                    viewModel.onEvent(MonetizationEvent.ToggleCancelDialog(false))
                    viewModel.onEvent(MonetizationEvent.OpenGooglePlaySubscriptions)
                }
            )
        }
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = appColor.background,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = appColor.cardColors,
                        contentColor = appColor.primaryText,
                        actionColor = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(dimens.Radius.md)
                    )
                }
            },
            bottomBar = {
                PaywallBottomBar(
                    selectedTab = state.selectedTab,
                    priceLabel = when (state.selectedTab) {
                        PlanTier.PRO_MONTHLY -> state.monthlyPackage?.formattedPrice
                        PlanTier.PRO_YEARLY -> state.yearlyPackage?.formattedPrice
                        PlanTier.PRO_LIFETIME -> state.lifetimePackage?.formattedPrice
                        else -> null
                    },
                    isLoadingPrices = state.isLoadingPrices,
                    isPurchaseLoading = state.isPurchaseLoading,
                    onSubscribeClick = { viewModel.onEvent(MonetizationEvent.PurchaseSelectedPlan) }
                )
            },
            contentWindowInsets = WindowInsets()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedVisibility(
                    visible = state.isOffline,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    OfflineBanner()
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .contentHorizontalPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    // Top Row: Back + Restore
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimens.Padding.md),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onBackClick,
                                modifier = Modifier
                                    .size(dimens.Height.minTouch / 1.2f)
                                    .clip(CircleShape)
                                    .background(appColor.cardColors)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_left),
                                    contentDescription = "Back",
                                    tint = appColor.primaryText,
                                    modifier = Modifier.size(dimens.Icon.sm)
                                )
                            }

                            Text(
                                text = stringResource(Res.string.paywall_btn_restore),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(dimens.Radius.sm))
                                    .clickable { viewModel.onEvent(MonetizationEvent.RestorePurchases) }
                                    .padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding.xs)
                            )
                        }
                    }

                    // Crown Icon Header & Titles
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(dimens.Icon._6xl)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                    .border(dimens.Border.thin, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.crown_fill),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(dimens.Icon._2xl)
                                )
                            }

                            Spacer(Modifier.height(dimens.Space.sm))

                            Text(
                                text = stringResource(Res.string.pro_banner_title),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = appColor.primaryText,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(dimens.Space._2xs))

                            Text(
                                text = stringResource(Res.string.paywall_tagline),
                                style = MaterialTheme.typography.bodyMedium,
                                color = appColor.secondaryText,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = stringResource(Res.string.paywall_subtagline),
                                style = MaterialTheme.typography.labelSmall,
                                color = appColor.tertiaryText,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Tab selector
                    item {
                        PaywallTabSelector(
                            selectedTab = state.selectedTab,
                            monthlyPrice = state.monthlyPackage?.formattedPrice,
                            yearlyPrice = state.yearlyPackage?.formattedPrice,
                            lifetimePrice = state.lifetimePackage?.formattedPrice,
                            onTabSelected = { viewModel.onEvent(MonetizationEvent.SelectTab(it)) }
                        )
                    }

                    // Early adopter banner
                    item {
                        EarlyAdopterBanner()
                    }

                    // Features comparison grid
                    item {
                        PaywallFeaturesComparison()
                    }

                    // Trust indicators
                    item {
                        TrustIndicators()
                    }

                    item {
                        Spacer(Modifier.height(dimens.Space.base))
                    }
                }
            }
        }
    }
}
