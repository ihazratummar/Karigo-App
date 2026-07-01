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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.karigojobs.app.android.services.UpdateManager
import com.karigojobs.app.navigation.AppNavigation
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.presentation.onboarding.OnboardingCompleteState
import com.karigojobs.presentation.onboarding.OnboardingViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        updateManager.checkForAppUpdates(this)

        onboardingViewModel = getViewModel()

        splashscreen.setKeepOnScreenCondition {
            onboardingViewModel.completedState.value == OnboardingCompleteState.Loading
        }

        setContent {
            val appPreferencesState = getAppPreferencesUseCase().collectAsStateWithLifecycle(
                initialValue = null
            )
            
            val appPreferences = appPreferencesState.value ?: GetAppPreferencesUseCase.AppPreferences(
                theme = ThemePreference.SYSTEM,
                language = AppLanguage.ENGLISH
            )

            LaunchedEffect(appPreferencesState.value?.language) {
                appPreferencesState.value?.language?.let { language ->
                    if (LocaleManager.getAppLocale() != language.code) {
                        LocaleManager.setAppLocale(language.code)
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