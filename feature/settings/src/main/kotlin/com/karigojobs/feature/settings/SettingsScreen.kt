package com.karigojobs.feature.settings


import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.karigojobs.app.android.ui.R
import com.karigojobs.feature.settings.component.QuickAccessData
import com.karigojobs.feature.settings.component.SettingsComponent
import com.karigojobs.feature.settings.component.SettingsOptionRow
import com.karigojobs.feature.settings.component.SettingsScreenQuickAction
import com.karigojobs.feature.settings.component.SettingsSelectionModal
import com.karigojobs.feature.settings.component.SettingsTabData
import com.karigojobs.feature.settings.component.SettingsTradeCard
import com.karigojobs.feature.settings.component.SettingsTradeChangeModal
import com.karigojobs.feature.settings.component.SettingsValueRow
import com.karigojobs.feature.settings.component.WorkerProfileCard
import com.karigojobs.presentation.settings.SettingsEvent
import com.karigojobs.presentation.settings.SettingsState
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import com.karigojobs.ui.common.ActionNeedBanner
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.SpringToggle
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.common.CurrencyPickerBottomSheet
import com.karigojobs.ui.theme.isPro
import karigojobs.shared.resources.generated.resources.*
import karigojobs.shared.resources.generated.resources.settings_section_data_support
import karigojobs.shared.resources.generated.resources.settings_section_legal_info
import karigojobs.shared.resources.generated.resources.settings_tab_disclaimer
import karigojobs.shared.resources.generated.resources.settings_tab_privacy
import karigojobs.shared.resources.generated.resources.settings_tab_toc
import karigojobs.shared.resources.generated.resources.settings_tab_tos
import karigojobs.shared.resources.generated.resources.settings_theme_dark
import karigojobs.shared.resources.generated.resources.settings_theme_light
import karigojobs.shared.resources.generated.resources.settings_theme_system
import org.jetbrains.compose.resources.stringResource

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
    val context = LocalContext.current

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = stringResource(Res.string.nav_settings),
                isNavBack = false,
                isDivider = false
            )
        },
        contentWindowInsets = WindowInsets()
    ) { paddingValues ->

        val quickAccessData = listOf(
            QuickAccessData(
                icon = R.drawable.stack,
                label = stringResource(Res.string.nav_materials),
                onClick = settingsNavigation.navigateToMaterial,
            ),
            QuickAccessData(
                icon = R.drawable.estimate,
                label = stringResource(Res.string.estimate_list_title),
                onClick = settingsNavigation.navigateToEstimate,
            ),
            QuickAccessData(
                icon = R.drawable.earning,
                label = "Earnings",
                onClick = settingsNavigation.navigateToEarnings,
            )
        )

        val legalAndInfoTabs = listOf(
            SettingsTabData(
                icon = R.drawable.about,
                name = stringResource(Res.string.about_title),
                onClick = settingsNavigation.navigateToAbout
            ),
            SettingsTabData(
                icon = R.drawable.privacy_policy,
                name = stringResource(Res.string.settings_tab_privacy),
                onClick = { settingsNavigation.navigateToLegalPage("privacy") }
            ),
            SettingsTabData(
                icon = R.drawable.terms_of_service,
                name = stringResource(Res.string.settings_tab_tos),
                onClick = { settingsNavigation.navigateToLegalPage("tos") }
            ),
            SettingsTabData(
                icon = R.drawable.terms_of_condition,
                name = stringResource(Res.string.settings_tab_toc),
                onClick = { settingsNavigation.navigateToLegalPage("toc") }
            ),
            SettingsTabData(
                icon = R.drawable.alert,
                name = stringResource(Res.string.settings_tab_disclaimer),
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
                title = stringResource(Res.string.settings_option_theme),
                description = stringResource(Res.string.dialog_theme_desc),
                items = ThemePreference.entries.toList(),
                selectedItem = state.currentTheme,
                itemLabel = { theme ->
                    when (theme) {
                        ThemePreference.SYSTEM -> stringResource(Res.string.settings_theme_system)
                        ThemePreference.LIGHT -> stringResource(Res.string.settings_theme_light)
                        ThemePreference.DARK -> stringResource(Res.string.settings_theme_dark)
                    }
                },
                onItemSelected = { event(SettingsEvent.UpdateTheme(it)) },
                onDismiss = { event(SettingsEvent.ToggleThemeModal(false)) }
            )
        }

        if (state.isLanguageModalOpen) {
            SettingsSelectionModal(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(Res.string.settings_option_language),
                description = stringResource(Res.string.dialog_language_desc),
                items = AppLanguage.entries.toList(),
                selectedItem = state.currentLanguage,
                itemLabel = { it.nativeName },
                onItemSelected = { event(SettingsEvent.UpdateLanguage(it)) },
                onDismiss = { event(SettingsEvent.ToggleLanguageModal(false)) }
            )
        }

        if (state.isCurrencyModalOpen) {
            CurrencyPickerBottomSheet(
                currentCurrencySymbol = state.currentCurrency,
                onCurrencySelected = { selected ->
                    event(SettingsEvent.UpdateCurrency(selected.symbol))
                },
                onDismissRequest = { event(SettingsEvent.ToggleCurrencyModal(false)) }
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

                val sectionTitle = if (isPro) stringResource(Res.string.pro_plan_active_section) else stringResource(Res.string.pro_section_title)
                val cardTitle = if (isPro) stringResource(Res.string.pro_activated_title) else stringResource(Res.string.pro_banner_title)
                val cardDesc = if (isPro) {
                    when (state.proStatus.planTier) {
                        com.karigojobs.share.model.PlanTier.PRO_YEARLY -> stringResource(Res.string.pro_yearly_active)
                        com.karigojobs.share.model.PlanTier.PRO_LIFETIME -> stringResource(Res.string.pro_lifetime_active)
                        else -> stringResource(Res.string.pro_monthly_active)
                    }
                } else stringResource(Res.string.pro_banner_desc)

                SettingsComponent(
                    title = sectionTitle
                ) {
                    SettingsOptionRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.crown_fill,
                        tabName = cardTitle,
                        description = cardDesc,
                        onClick = settingsNavigation.navigateToProOverView
                    )
                }
            }

            item {
                SettingsComponent (
                    title = stringResource(Res.string.settings_section_data_support)
                ){
                    SettingsOptionRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.server,
                        tabName = stringResource(Res.string.settings_option_backup),
                        description = stringResource(Res.string.settings_option_backup_desc),
                        onClick = settingsNavigation.navigateToDataBackUp,
                    )


                }
            }

            item {
                SettingsComponent(
                    title = stringResource(Res.string.settings_section_app_settings)
                ) {
                    SettingsValueRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.map_point, // map_point as a fallback for language
                        label = stringResource(Res.string.settings_option_language),
                        value = state.currentLanguage.nativeName,
                        onClick = { event(SettingsEvent.ToggleLanguageModal(true)) }
                    )
                    SettingsValueRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.settings_line,
                        label = stringResource(Res.string.settings_option_theme),
                        value = when (state.currentTheme) {
                            ThemePreference.SYSTEM -> stringResource(Res.string.settings_theme_system)
                            ThemePreference.LIGHT -> stringResource(Res.string.settings_theme_light)
                            ThemePreference.DARK -> stringResource(Res.string.settings_theme_dark)
                        },
                        onClick = { event(SettingsEvent.ToggleThemeModal(true)) }
                    )
                    SettingsValueRow(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.about,
                        label = stringResource(Res.string.settings_currency_title),
                        value = state.currentCurrency,
                        onClick = { event(SettingsEvent.ToggleCurrencyModal(true)) }
                    )
                }
            }


            item {
                SettingsComponent(
                    title = stringResource(Res.string.settings_section_legal_info)
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

            item {
                Spacer(Modifier.height(dimens.Space._8xl))
            }
        }
    }
}

