package com.karigo.di

import com.karigo.domain.usecase.CompleteOnboardingUseCase
import com.karigo.domain.usecase.GetOnboardingStatusUseCase
import com.karigo.domain.usecase.SeedStarterMaterialsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 

fun getDomainModule() : Module = module {
    single { CompleteOnboardingUseCase(onboardingStore = get()) }
    single { GetOnboardingStatusUseCase(onboardingStore = get()) }
    single { SeedStarterMaterialsUseCase(materialRepository = get()) }
}