@file:OptIn(ExperimentalForeignApi::class)
package com.karigojobs.di

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.domain.usecase.settings.AppPreferences
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.presentation.backup.DataBackupViewModel
import com.karigojobs.presentation.client.details.ClientDetailsViewModel
import com.karigojobs.presentation.client.list.ClientListViewModel
import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.dashboard.HomeState
import com.karigojobs.presentation.dashboard.HomeEffect
import com.karigojobs.presentation.earnings.EarningsViewModel
import com.karigojobs.presentation.estimate.add.AddEstimateViewModel
import com.karigojobs.presentation.estimate.details.EstimateDetailsViewModel
import com.karigojobs.presentation.estimate.list.EstimateListViewModel
import com.karigojobs.presentation.job.create.AddJobIntent
import com.karigojobs.presentation.job.create.AddJobViewModel
import com.karigojobs.presentation.job.details.JobDetailsIntent
import com.karigojobs.presentation.job.details.JobDetailsViewModel
import com.karigojobs.presentation.job.jobList.JobListIntent
import com.karigojobs.presentation.job.jobList.JobListViewModel
import com.karigojobs.presentation.job.jobList.JobStatusFilter
import com.karigojobs.presentation.main.MainViewModel
import com.karigojobs.presentation.materials.list.MaterialListViewModel
import com.karigojobs.presentation.onboarding.OnboardingIntent
import com.karigojobs.presentation.onboarding.OnboardingViewModel
import com.karigojobs.presentation.onboarding.WorkerProfileViewModel
import com.karigojobs.presentation.settings.SettingsViewModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.TradeType
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

interface Closeable { fun close() }

class CFlow<T: Any>(private val origin: Flow<T>) {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        scope.launch {
            origin.collect { block(it) }
        }
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}

class Helper : KoinComponent {
    fun getHomeViewModel(): HomeViewModel = get()
    fun getOnboardingViewModel(): OnboardingViewModel = get()
    fun getWorkerProfileViewModel(): WorkerProfileViewModel = get()
    fun getMainViewModel(): MainViewModel = get()
    fun getJobListViewModel(): JobListViewModel = get()
    fun getMaterialListViewModel(): MaterialListViewModel = get()
    fun getEstimateListViewModel(): EstimateListViewModel = get()
    fun getClientListViewModel(): ClientListViewModel = get()
    fun getSettingsViewModel(): SettingsViewModel = get()
    fun getEarningsViewModel(): EarningsViewModel = get()
    fun getDataBackupViewModel(): DataBackupViewModel = get()

    fun getAddJobViewModel(jobId: String?, clientId: String?): AddJobViewModel =
        get { parametersOf(jobId, clientId) }

    fun getJobDetailsViewModel(jobId: String): JobDetailsViewModel =
        get { parametersOf(jobId) }

    fun getAddEstimateViewModel(estimateId: String?): AddEstimateViewModel =
        get { parametersOf(estimateId) }

    fun getEstimateDetailsViewModel(estimateId: String): EstimateDetailsViewModel =
        get { parametersOf(estimateId) }

    fun getClientDetailsViewModel(clientId: String): ClientDetailsViewModel =
        get { parametersOf(clientId) }

    // Type anchors & intent factories for Objective-C header generation
    fun getAllTradeTypes(): List<TradeType> = TradeType.entries
    fun getAppPreferencesSync(): AppPreferences = get<GetAppPreferencesUseCase>().getSync()

    fun onboardingIntentGetStarted(): OnboardingIntent = OnboardingIntent.GetStarted
    fun onboardingIntentConfirmTrades(): OnboardingIntent = OnboardingIntent.ConfirmTrades
    fun onboardingIntentLetsGo(): OnboardingIntent = OnboardingIntent.LetsGo
    fun onboardingIntentBackToTrades(): OnboardingIntent = OnboardingIntent.BackToTrades
    fun onboardingIntentDismissError(): OnboardingIntent = OnboardingIntent.DismissError
    fun onboardingIntentToggleTrade(trade: TradeType): OnboardingIntent = OnboardingIntent.ToggleTrade(trade)

    fun jobListIntentSearchTextChanged(text: String): JobListIntent = JobListIntent.SearchTextChanged(text)
    fun jobListIntentJobFilterClick(filter: JobStatusFilter): JobListIntent = JobListIntent.JobFilterClick(filter)
    fun jobListIntentJobClick(jobId: String): JobListIntent = JobListIntent.JobClick(jobId)

    fun addJobIntentUpdateTitle(title: String): AddJobIntent = AddJobIntent.UpdateTitle(title)
    fun addJobIntentSelectClient(contact: DeviceContact): AddJobIntent = AddJobIntent.SelectClient(contact)
    fun addJobIntentSelectTradeType(tradeType: TradeType): AddJobIntent = AddJobIntent.SelectTradeType(tradeType)
    fun addJobIntentSaveJob(): AddJobIntent = AddJobIntent.SaveJob

    fun jobDetailsIntentChangeJobStatus(id: String, jobStatus: JobStatus): JobDetailsIntent = JobDetailsIntent.ChangeJobStatus(id, jobStatus)
    fun jobDetailsIntentDeleteJob(jobId: String): JobDetailsIntent = JobDetailsIntent.DeleteJob(jobId)
    fun jobDetailsIntentGenerateInvoicePdf(currencySymbol: String): JobDetailsIntent = JobDetailsIntent.GenerateInvoicePdf(currencySymbol)
    fun jobDetailsIntentShareInvoiceOnWhatsapp(currencySymbol: String): JobDetailsIntent = JobDetailsIntent.ShareInvoiceOnWhatsapp(currencySymbol)
    
    fun initKoinIos() {
        if (org.koin.mp.KoinPlatform.getKoinOrNull() != null) return
        initKoin {
            modules(org.koin.dsl.module {
                single<String> {
                    val url = NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null
                    )
                    url?.path ?: ""
                }
            })
        }
    }
}


class IosHomeViewModelWrapper : KoinComponent {
    private val viewModel: HomeViewModel = get()
    
    val initialState: HomeState = viewModel.state.value
    
    fun watchState(block: (HomeState) -> Unit): Closeable {
        return CFlow(viewModel.state).watch(block)
    }
    
    fun watchEffect(block: (HomeEffect) -> Unit): Closeable {
        return CFlow(viewModel.effect).watch(block)
    }
}

