package com.karigojobs.di

import com.karigojobs.domain.usecase.job.ChangeJobStatusUseCase
import com.karigojobs.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.karigojobs.domain.usecase.job.DeleteJobUseCase
import com.karigojobs.domain.usecase.job.GetAllJobUseCase
import com.karigojobs.domain.usecase.material.GetAllMaterialsUseCase
import com.karigojobs.domain.usecase.job.GetJobDetailsUseCase
import com.karigojobs.domain.usecase.onboarding.GetOnboardingStatusUseCase
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.client.DeleteClientUseCase
import com.karigojobs.domain.usecase.client.GetClientFlowUseCase
import com.karigojobs.domain.usecase.client.GetClientListUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.client.InsertClientUseCase
import com.karigojobs.domain.usecase.client.IsClientExistUseCase
import com.karigojobs.domain.usecase.estimate.AddNewSiteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.DeleteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetAllEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateByIdUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateMaterialsUseCase
import com.karigojobs.domain.usecase.estimate.UpdateSiteEstimateUseCase
import com.karigojobs.domain.usecase.job.GetJobByClientUseCase
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
import com.karigojobs.domain.usecase.materialCategory.DeleteMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.GetMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.InsertMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.UpdateMaterialCategoryUseCase
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.domain.usecase.settings.SaveWorkerProfileUseCase
import com.karigojobs.domain.usecase.trade.SaveTradesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */


fun getDomainModule(): Module = module {
    single { CompleteOnboardingUseCase(onboardingStore = get()) }
    single { GetOnboardingStatusUseCase(onboardingStore = get()) }
    single { GetSelectedTradeTypeUseCase(onboardingStore = get(), ioDispatcher = get()) }
    single { SaveTradesUseCase(onboardingStore = get()) }


    single { SeedStarterMaterialsUseCase(materialRepository = get()) }
    single { GetAllMaterialsUseCase(materialRepository = get()) }
    single { SearchMaterialsUseCase(materialRepository = get()) }
    single { DeleteMaterialUseCase(materialRepository = get()) }
    single { UpdateMaterialUseCase(materialRepository = get()) }
    single { GetMaterialByIdUseCase(materialRepository = get()) }
    single { AddMaterialUseCase(materialRepository = get()) }

    single { GetMaterialCategoryUseCase(materialCategoryRepository = get()) }
    single { InsertMaterialCategoryUseCase(repository = get()) }
    single { UpdateMaterialCategoryUseCase(repository = get()) }
    single { DeleteMaterialCategoryUseCase(repository = get()) }


    single { SaveFullJobTransactionUseCase(jobRepository = get()) }
    single { GetJobDetailsUseCase(jobRepository = get()) }
    single { GetAllJobUseCase(jobRepository = get()) }
    single { SearchJobUseCase(jobRepository = get()) }
    single { ChangeJobStatusUseCase(jobRepository = get()) }
    single { DeleteJobUseCase(jobRepository = get()) }
    single { GetJobLabourItemUseCase(jobRepository = get()) }
    single { GetJobMaterialItemsUseCase(jobRepository = get()) }
    single { GetJobByClientUseCase(jobRepository = get()) }

    single { InsertClientUseCase(clientRepository = get()) }
    single { IsClientExistUseCase(clientRepository = get()) }
    single { GetClientUseCase(clientRepository = get()) }
    single { GetClientListUseCase(clientRepository = get()) }
    single { GetClientFlowUseCase(clientRepository = get()) }
    single { DeleteClientUseCase(clientRepository = get()) }

    single { AddNewSiteEstimateUseCase(estimateRepository = get()) }
    single { GetAllEstimateUseCase(estimateRepository = get()) }
    single { GetEstimateByIdUseCase(estimateRepository = get()) }
    single { GetEstimateMaterialsUseCase(estimateRepository = get()) }
    single { DeleteEstimateUseCase(estimateRepository = get()) }
    single { UpdateSiteEstimateUseCase(repository = get()) }

    single { GetWorkerProfileUseCase(workerRepository = get()) }
    single { SaveWorkerProfileUseCase(workerRepository = get()) }

}