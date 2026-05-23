package com.karigo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.karigo.app.navigation.AppNavigation
import com.karigo.presentation.onboarding.OnboardingCompleteState
import com.karigo.presentation.onboarding.OnboardingViewModel
import com.karigo.ui.theme.KarigoTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private lateinit var onboardingViewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        onboardingViewModel = getViewModel()

        splashscreen.setKeepOnScreenCondition {
            onboardingViewModel.completedState.value == OnboardingCompleteState.Loading
        }

        setContent {
            val windowsSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            KarigoTheme(
                windowSizeClass = windowsSizeClass,
            ) {
                AppNavigation(
                    modifier = Modifier.fillMaxSize(),
                    onboardingViewModel = onboardingViewModel
                )
            }
        }
    }
}