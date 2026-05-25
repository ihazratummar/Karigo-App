package com.karigojobs.app.feature.onboarding

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.karigojobs.app.feature.onboarding.component.ReadyContent
import com.karigojobs.app.feature.onboarding.component.TradeSelectContent
import com.karigojobs.app.feature.onboarding.component.WelcomeContent
import com.karigojobs.presentation.onboarding.OnboardingEffect
import com.karigojobs.presentation.onboarding.OnboardingIntent
import com.karigojobs.presentation.onboarding.OnboardingState
import com.karigojobs.presentation.onboarding.OnboardingStep
import kotlinx.coroutines.flow.SharedFlow


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    state: OnboardingState,
    event: (OnboardingIntent) -> Unit,
    effect: SharedFlow<OnboardingEffect>?,
    navigateToDashboard: () -> Unit
){

    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when(effect){
                OnboardingEffect.NavigationToDashboard -> {
                    navigateToDashboard()
                }
                is OnboardingEffect.ShowError -> {
                    snackbarState.showSnackbar(
                        message = effect.message,
                        withDismissAction = true
                    )
                    Log.e("OnboardingScreen", "Error -> ${effect.message}")
                }
            }
        }
    }

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        }
    ){paddingValues ->

        AnimatedContent(
            targetState = state.currentStep,
            label = "onboarding_step_animation",
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { it }
                ) + fadeIn() togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { -it }
                        ) + fadeOut()
            }
        ) {step ->

            when(step){
                OnboardingStep.WELCOME -> {
                    WelcomeContent(
                        modifier = Modifier.padding(paddingValues),
                        onGetStartedClick = {
                            event(OnboardingIntent.GetStarted)
                        }
                    )
                }
                OnboardingStep.TRADE_SELECT -> {
                    TradeSelectContent(
                        onboardingState = state,
                        event = event
                    )
                }
                OnboardingStep.READY -> {
                    ReadyContent(
                        modifier = Modifier.padding(paddingValues),
                        onboardingState = state,
                        event = event
                    )
                }
            }

        }

    }

}