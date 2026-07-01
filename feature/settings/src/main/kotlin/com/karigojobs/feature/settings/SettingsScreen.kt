package com.karigojobs.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import com.karigojobs.app.android.ui.R
import com.karigojobs.feature.settings.component.QuickAccessData
import com.karigojobs.feature.settings.component.SettingsComponent
import com.karigojobs.feature.settings.component.SettingsOptionRow
import com.karigojobs.feature.settings.component.SettingsScreenQuickAction
import com.karigojobs.feature.settings.component.SettingsTabData
import com.karigojobs.feature.settings.component.SettingsTradeCard
import com.karigojobs.feature.settings.component.SettingsTradeChangeModal
import com.karigojobs.feature.settings.component.WorkerProfileCard
import com.karigojobs.presentation.settings.SettingsEvent
import com.karigojobs.presentation.settings.SettingsState
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import com.karigojobs.feature.settings.component.SettingsSelectionModal
import com.karigojobs.feature.settings.component.SettingsValueRow
import com.karigojobs.ui.common.ActionNeedBanner
import com.karigojobs.ui.common.IconPlaceholder
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.dimens

/**
 * Settings screen showing either a banner to complete setup or the worker's business profile card.
 */
@Composable
fun SettingsScreen(
    state: SettingsState,
    onCompleteBannerClick: () -> Unit = {},
    settingsNavigation: SettingsNavigation,
    event: (SettingsEvent) -> Unit
) {
    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = "Settings",
                isNavBack = false,
                isDivider = false
            )
        }
    ) { paddingValues ->

        val quickAccessData = listOf(
            QuickAccessData(
                icon = R.drawable.stack,
                label = "Materials",
                onClick = settingsNavigation.navigateToMaterial,
            ),
            QuickAccessData(
                icon = R.drawable.estimate,
                label = "Estimates",
                onClick = settingsNavigation.navigateToEstimate,
            ),
            QuickAccessData(
                icon = R.drawable.earning,
                label = "Earnings",
                onClick = settingsNavigation.navigateToEarnings,
            ),
        )

        val legalAndInfoTabs = listOf(
            SettingsTabData(
                icon = R.drawable.about,
                name = "About Karigo",
                onClick = settingsNavigation.navigateToAbout
            ),
            SettingsTabData(
                icon = R.drawable.privacy_policy,
                name = "Privacy Policy",
                onClick = { settingsNavigation.navigateToLegalPage("privacy") }
            ),
            SettingsTabData(
                icon = R.drawable.terms_of_service,
                name = "Terms of Service",
                onClick = { settingsNavigation.navigateToLegalPage("tos") }
            ),
            SettingsTabData(
                icon = R.drawable.terms_of_condition,
                name = "Terms of Condition",
                onClick = { settingsNavigation.navigateToLegalPage("toc") }
            ),
            SettingsTabData(
                icon = R.drawable.alert,
                name = "Disclaimer",
                onClick = { settingsNavigation.navigateToLegalPage("disclaimer") }
            ),
        )

        if (state.isTradeSelectModalOpen) {
            SettingsTradeChangeModal(
                modifier = Modifier.fillMaxWidth(),
                onDismiss = { event(SettingsEvent.ToggleTradeSelectModal(false)) },
                state = state,
                event = event
            )
        }

        if (state.isThemeModalOpen) {
            SettingsSelectionModal(
                modifier = Modifier.fillMaxWidth(),
                title = "App Theme",
                description = "Choose how Karigo looks for you.",
                items = ThemePreference.entries.toList(),
                selectedItem = state.currentTheme,
                itemLabel = { it.displayName },
                onItemSelected = { event(SettingsEvent.UpdateTheme(it)) },
                onDismiss = { event(SettingsEvent.ToggleThemeModal(false)) }
            )
        }

        if (state.isLanguageModalOpen) {
            SettingsSelectionModal(
                modifier = Modifier.fillMaxWidth(),
                title = "App Language",
                description = "Choose the language you prefer.",
                items = AppLanguage.entries.toList(),
                selectedItem = state.currentLanguage,
                itemLabel = { it.displayName },
                onItemSelected = { event(SettingsEvent.UpdateLanguage(it)) },
                onDismiss = { event(SettingsEvent.ToggleLanguageModal(false)) }
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .contentHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item {
                val profile = state.workerProfileModel
                if (profile == null) {
                    ActionNeedBanner(onClick = onCompleteBannerClick)
                } else {
                    WorkerProfileCard(
                        profile = profile,
                        onEditClick = onCompleteBannerClick
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    quickAccessData.forEach { data ->
                        SettingsScreenQuickAction(
                            modifier = Modifier.weight(1f),
                            icon = data.icon,
                            label = data.label,
                            onClick = data.onClick,
                        )
                    }
                }
            }
            item {
                SettingsTradeCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { event(SettingsEvent.ToggleTradeSelectModal(true)) },
                    trades = state.selectedTrades
                )
            }

            item {
                SettingsComponent(
                    title = "APP SETTINGS"
                ) {
//                    SettingsValueRow(
//                        modifier = Modifier.fillMaxWidth(),
//                        icon = R.drawable.map_point, // map_point as a fallback for language
//                        label = "App Language",
//                        value = state.currentLanguage.displayName,
//                        onClick = { event(SettingsEvent.ToggleLanguageModal(true)) }
//                    )
                    SettingsValueRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.settings_line,
                        label = "App Theme",
                        value = state.currentTheme.displayName,
                        onClick = { event(SettingsEvent.ToggleThemeModal(true)) }
                    )
                }
            }
            
            item {
                SettingsComponent(
                    title = "LEGAL & INFO"
                ){
                    legalAndInfoTabs.forEach {legal ->
                        SettingsOptionRow(
                            modifier = Modifier.fillMaxWidth(),
                            icon = legal.icon ,
                            tabName = legal.name,
                            onClick = legal.onClick
                        )
                    }
                }
            }
        }
    }
}

