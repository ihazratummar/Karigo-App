package com.karigo.di

import com.karigo.presentation.dashboard.HomeViewModel
import com.karigo.presentation.job.create.AddJobViewModel
import com.karigo.presentation.onboarding.OnboardingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */

fun getPresentationModule(): Module = module {
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AddJobViewModel)
}