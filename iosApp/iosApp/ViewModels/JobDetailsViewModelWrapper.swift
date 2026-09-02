import SwiftUI
import Combine
import sharedNewKit

class JobDetailsViewModelWrapper: ObservableObject {
    private let viewModel: JobDetailsViewModel
    private var stateAdapter = FlowAdapter<JobDetailsState>()

    @Published var state: JobDetailsState

    init(jobId: String) {
        let vm = Helper().getJobDetailsViewModel(jobId: jobId)
        self.viewModel = vm
        self.state = (vm.state.value as? JobDetailsState) ?? JobDetailsState(
            jobModel: nil,
            clientModel: nil,
            workerProfileModel: nil,
            isLoading: false,
            isDeleting: false,
            isJobStatusModalOpen: false,
            jobLabourItems: [],
            jobMaterialItems: [],
            isDeletePopUpOpen: false,
            isPro: false,
            monthlyJobLimit: MonthlyJobLimit(yearMonth: "", maxFreeJobs: 5, usedJobsCount: 0, maxFreeEstimates: 5, usedEstimatesCount: 0, maxFreePdfExports: 5, usedPdfCount: 0),
            jobPayments: [],
            showProDialog: false,
            proDialogFeatureName: ""
        )

        stateAdapter.subscribe(flow: CFlow(origin: vm.state)) { [weak self] newState in
            self?.state = newState
        }
    }

    func onChangeStatus(_ status: JobStatus) {
        if let jobId = state.jobModel?.id {
            viewModel.onEvent(event: Helper().jobDetailsIntentChangeJobStatus(id: jobId, jobStatus: status))
        }
    }

    func onDeleteJob() {
        if let jobId = state.jobModel?.id {
            viewModel.onEvent(event: Helper().jobDetailsIntentDeleteJob(jobId: jobId))
        }
    }

    func onGenerateInvoicePdf(currencySymbol: String = "₹") {
        viewModel.onEvent(event: Helper().jobDetailsIntentGenerateInvoicePdf(currencySymbol: currencySymbol))
    }

    func onShareInvoiceOnWhatsapp(currencySymbol: String = "₹") {
        viewModel.onEvent(event: Helper().jobDetailsIntentShareInvoiceOnWhatsapp(currencySymbol: currencySymbol))
    }

    deinit {
        stateAdapter.cancel()
    }
}
