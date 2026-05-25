package com.karigojobs.di

import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.onboarding.OnboardingViewModel
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
    viewModelOf(::JobListViewModel)
}