package com.karigojobs.di

import com.karigojobs.domain.usecase.CompleteOnboardingUseCase
import com.karigojobs.domain.usecase.GetAllJobUseCase
import com.karigojobs.domain.usecase.GetOnboardingStatusUseCase
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.InsertClientUseCase
import com.karigojobs.domain.usecase.IsClientExistUseCase
import com.karigojobs.domain.usecase.SaveFullJobTransactionUseCase
import com.karigojobs.domain.usecase.SeedStarterMaterialsUseCase
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
    single { GetAllJobUseCase(jobRepository = get()) }
    single { InsertClientUseCase(clientRepository = get()) }
    single { IsClientExistUseCase(clientRepository = get()) }
}