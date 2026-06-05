package com.karigojobs.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.karigojobs.app.android.services.UpdateManager
import com.karigojobs.app.navigation.AppNavigation
import com.karigojobs.presentation.onboarding.OnboardingCompleteState
import com.karigojobs.presentation.onboarding.OnboardingViewModel
import com.karigojobs.ui.theme.KarigojobsTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private lateinit var onboardingViewModel: OnboardingViewModel
    private val updateManager : UpdateManager by inject ()

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
            val windowsSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            KarigojobsTheme(
                windowSizeClass = windowsSizeClass,
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