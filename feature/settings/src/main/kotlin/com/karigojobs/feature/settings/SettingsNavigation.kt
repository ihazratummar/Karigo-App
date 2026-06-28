package com.karigojobs.feature.settings

data class SettingsNavigation(
    val navigateToMaterial: () -> Unit,
    val navigateToEstimate : () -> Unit,
    val navigateToEarnings : () -> Unit,
    val navigateToProOverView : () -> Unit,
    val navigateToNotification : () -> Unit,
    val navigateToDataBackUp : () -> Unit,
    val navigateToHelpAndSupport : () -> Unit,
    val navigateToAbout : () -> Unit,
    val navigateToLegalPage : (String) -> Unit
)
