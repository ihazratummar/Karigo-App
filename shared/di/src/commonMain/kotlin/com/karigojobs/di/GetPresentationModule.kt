package com.karigojobs.di

import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.estimate.add.AddEstimateViewModel
import com.karigojobs.presentation.estimate.details.EstimateDetailsViewModel
import com.karigojobs.presentation.estimate.list.EstimateListViewModel
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.details.JobDetailsViewModel
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.materials.list.MaterialListViewModel
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
    viewModelOf(::MaterialListViewModel)
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
            updateSiteEstimateUseCase = get()
        )
    }
    viewModelOf(::EstimateListViewModel)


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

}