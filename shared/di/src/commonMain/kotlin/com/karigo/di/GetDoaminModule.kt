package com.karigo.di

import com.karigo.domain.usecase.CompleteOnboardingUseCase
import com.karigo.domain.usecase.GetOnboardingStatusUseCase
import com.karigo.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigo.domain.usecase.InsertClientUseCase
import com.karigo.domain.usecase.IsClientExistUseCase
import com.karigo.domain.usecase.SaveFullJobTransactionUseCase
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
    single { GetSelectedTradeTypeUseCase(onboardingStore = get()) }
    single { SaveFullJobTransactionUseCase(jobRepository = get()) }
    single { InsertClientUseCase(clientRepository = get()) }
    single { IsClientExistUseCase(clientRepository = get()) }
}