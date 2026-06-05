package com.karigojobs.di

import com.karigojobs.domain.usecase.job.ChangeJobStatusUseCase
import com.karigojobs.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.karigojobs.domain.usecase.job.DeleteJobUseCase
import com.karigojobs.domain.usecase.job.GetAllJobUseCase
import com.karigojobs.domain.usecase.material.GetAllMaterialsUseCase
import com.karigojobs.domain.usecase.job.GetJobDetailsUseCase
import com.karigojobs.domain.usecase.onboarding.GetOnboardingStatusUseCase
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.client.InsertClientUseCase
import com.karigojobs.domain.usecase.client.IsClientExistUseCase
import com.karigojobs.domain.usecase.job.GetJobLabourItemUseCase
import com.karigojobs.domain.usecase.job.GetJobMaterialItemsUseCase
import com.karigojobs.domain.usecase.job.SaveFullJobTransactionUseCase
import com.karigojobs.domain.usecase.job.SearchJobUseCase
import com.karigojobs.domain.usecase.material.AddMaterialUseCase
import com.karigojobs.domain.usecase.material.DeleteMaterialUseCase
import com.karigojobs.domain.usecase.material.GetMaterialByIdUseCase
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import com.karigojobs.domain.usecase.material.SeedStarterMaterialsUseCase
import com.karigojobs.domain.usecase.material.UpdateMaterialUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 

fun getDomainModule() : Module = module {
    single { CompleteOnboardingUseCase(onboardingStore = get()) }
    single { GetOnboardingStatusUseCase(onboardingStore = get()) }
    single { GetSelectedTradeTypeUseCase(onboardingStore = get(), ioDispatcher = get()) }

    single { SeedStarterMaterialsUseCase(materialRepository = get()) }
    single { GetAllMaterialsUseCase(materialRepository = get()) }
    single { SearchMaterialsUseCase(materialRepository = get()) }
    single { DeleteMaterialUseCase(materialRepository = get()) }
    single { UpdateMaterialUseCase(materialRepository = get()) }
    single { GetMaterialByIdUseCase(materialRepository = get()) }
    single { AddMaterialUseCase(materialRepository = get()) }


    single { SaveFullJobTransactionUseCase(jobRepository = get()) }
    single { GetJobDetailsUseCase(jobRepository = get()) }
    single { GetAllJobUseCase(jobRepository = get()) }
    single { SearchJobUseCase(jobRepository = get()) }
    single { ChangeJobStatusUseCase(jobRepository = get()) }
    single { DeleteJobUseCase(jobRepository = get()) }
    single { GetJobLabourItemUseCase(jobRepository = get()) }
    single { GetJobMaterialItemsUseCase(jobRepository = get()) }

    single { InsertClientUseCase(clientRepository = get()) }
    single { IsClientExistUseCase(clientRepository = get()) }
    single { GetClientUseCase(clientRepository = get()) }
}