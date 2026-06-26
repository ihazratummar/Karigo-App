package com.karigojobs.di

import com.karigojobs.presentation.client.details.ClientDetailsViewModel
import com.karigojobs.presentation.client.list.ClientListViewModel
import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.estimate.add.AddEstimateViewModel
import com.karigojobs.presentation.estimate.details.EstimateDetailsViewModel
import com.karigojobs.presentation.estimate.list.EstimateListViewModel
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.details.JobDetailsViewModel
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.materials.list.MaterialCategoryViewModel
import com.karigojobs.presentation.materials.list.MaterialListViewModel
import com.karigojobs.presentation.onboarding.OnboardingViewModel
import com.karigojobs.presentation.settings.SettingsViewModel
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
    viewModelOf(::JobListViewModel)
    viewModelOf(::MaterialListViewModel)
    viewModelOf(::MaterialCategoryViewModel)
    viewModelOf(::EstimateListViewModel)
    viewModelOf(::ClientListViewModel)
    viewModelOf(::SettingsViewModel)

    viewModel { (jobId: String?, clientId: String?) ->
        AddJobViewModel(
            jobId = jobId,
            clientId = clientId,
            deviceContactProvider = get(),
            saveFullJobTransactionUseCase = get(),
            isClientExistUseCase = get(),
            insertClientUseCase = get(),
            getSelectedTradeTypeUseCase = get(),
            searchMaterialsUseCase = get(),
            getMaterialCategoryUseCase = get(),
            getJobDetailsUseCase = get(),
            getJobLabourItemUseCase = get(),
            getJobMaterialItemsUseCase = get(),
            getClientUseCase = get()
        )
    }
    viewModel { (estimateId: String?) ->
        AddEstimateViewModel(
            estimateId = estimateId,
            deviceContactProvider = get(),
            isClientExistUseCase = get(),
            getClientUseCase = get(),
            searchMaterialsUseCase = get(),
            getSelectedTradeTypeUseCase = get(),
            insertClientUseCase = get(),
            addNewSiteEstimateUseCase = get(),
            getEstimateByIdUseCase = get(),
            getEstimateMaterialsUseCase = get(),
            updateSiteEstimateUseCase = get(),
            getMaterialCategoryUseCase = get()
        )
    }
    viewModel { (jobId: String) ->
        JobDetailsViewModel(
            jobId = jobId,
            getJobDetailsUseCase = get(),
            changeJobStatusUseCase = get(),
            deleteJobUseCase = get(),
            getClientUseCase = get(),
            getJobLabourItemUseCase = get(),
            getJobMaterialItemsUseCase = get()
        )
    }

    viewModel { (estimateId: String) ->
        EstimateDetailsViewModel(
            estimateId = estimateId,
            getEstimateByIdUseCase = get(),
            getEstimateMaterialsUseCase = get(),
            deleteEstimateUseCase = get(),
        )
    }

    viewModel { (clientId: String) ->
        ClientDetailsViewModel(
            clientId = clientId,
            getClientUseCase = get(),
            getJobByClientUseCase = get(),
            deleteClientUseCase = get()
        )
    }

}