package com.karigojobs.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karigojobs.app.android.services.UpdateManager
import com.karigojobs.app.navigation.AppNavigation
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.domain.usecase.backup.GetAutoBackupStatusUseCase
import com.karigojobs.feature.settings.backup.BackupScheduler
import com.karigojobs.presentation.onboarding.OnboardingCompleteState
import com.karigojobs.presentation.onboarding.OnboardingViewModel
import com.karigojobs.domain.usecase.settings.UpdateAppLanguageUseCase
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import com.karigojobs.shared.device.LocaleManager
import com.karigojobs.ui.theme.KarigojobsTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingViewModel: OnboardingViewModel
    private val updateManager : UpdateManager by inject ()
    private val getAppPreferencesUseCase: GetAppPreferencesUseCase by inject()
    private val getAutoBackupStatusUseCase: GetAutoBackupStatusUseCase by inject()
    private val updateAppLanguageUseCase: UpdateAppLanguageUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        updateManager.checkForAppUpdates(this)

        onboardingViewModel = getViewModel()

        var isPreferencesLoaded = false

        splashscreen.setKeepOnScreenCondition {
            onboardingViewModel.completedState.value == OnboardingCompleteState.Loading || !isPreferencesLoaded
        }

        setContent {
            val appPreferencesState = getAppPreferencesUseCase().collectAsStateWithLifecycle(
                initialValue = null
            )
            
            val autoBackupState = getAutoBackupStatusUseCase().collectAsStateWithLifecycle(
                initialValue = false
            )

            LaunchedEffect(appPreferencesState.value) {
                if (appPreferencesState.value != null) {
                    isPreferencesLoaded = true
                }
            }

            LaunchedEffect(autoBackupState.value) {
                if (autoBackupState.value) {
                    BackupScheduler.scheduleNightlyBackup(this@MainActivity)
                } else {
                    BackupScheduler.cancelNightlyBackup(this@MainActivity)
                }
            }
            val appPreferences = appPreferencesState.value ?: GetAppPreferencesUseCase.AppPreferences(
                theme = ThemePreference.SYSTEM,
                language = AppLanguage.ENGLISH,
                currency = "₹"
            )

            var isInitialLanguageSyncDone by remember { mutableStateOf(false) }

            LaunchedEffect(appPreferencesState.value?.language) {
                appPreferencesState.value?.language?.let { language ->
                    val systemLocaleCode = LocaleManager.getAppLocale()
                    if (!isInitialLanguageSyncDone) {
                        isInitialLanguageSyncDone = true
                        if (systemLocaleCode != language.code) {
                            // System locale was changed outside the app, sync to Datastore
                            updateAppLanguageUseCase(AppLanguage.fromCode(systemLocaleCode))
                        }
                    } else {
                        // In-app locale change, sync to System
                        if (systemLocaleCode != language.code) {
                            LocaleManager.setAppLocale(language.code)
                        }
                    }
                }
            }

            val isDarkTheme = when (appPreferences.theme) {
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
                ThemePreference.DARK -> true
                ThemePreference.LIGHT -> false
            }

            LaunchedEffect(isDarkTheme) {
                val style = SystemBarStyle.auto(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT
                ) { isDarkTheme }
                
                enableEdgeToEdge(
                    statusBarStyle = style,
                    navigationBarStyle = style
                )
            }

            val windowsSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            KarigojobsTheme(
                windowSizeClass = windowsSizeClass,
                darkTheme = isDarkTheme
            ) {
                AppNavigation(
                    modifier = Modifier.fillMaxSize(),
                    onboardingViewModel = onboardingViewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateManager.onResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        updateManager.onDestroy()
    }
}