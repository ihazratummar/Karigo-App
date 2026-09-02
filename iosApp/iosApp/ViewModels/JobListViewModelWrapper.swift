import SwiftUI
import Combine
import sharedNewKit

class JobListViewModelWrapper: ObservableObject {
    private let viewModel: JobListViewModel
    private var stateAdapter = FlowAdapter<JobListState>()

    @Published var state: JobListState

    init(viewModel: JobListViewModel = Helper().getJobListViewModel()) {
        self.viewModel = viewModel
        self.state = (viewModel.state.value as? JobListState) ?? JobListState(isLoading: false, jobStatusFilter: JobStatusFilterAll(), jobs: [], searchJobText: "")

        stateAdapter.subscribe(flow: CFlow(origin: viewModel.state)) { [weak self] newState in
            self?.state = newState
        }
    }

    func onSearchTextChanged(_ text: String) {
        viewModel.onEvent(event: Helper().jobListIntentSearchTextChanged(text: text))
    }

    func onFilterSelected(_ filter: JobStatusFilter) {
        viewModel.onEvent(event: Helper().jobListIntentJobFilterClick(filter: filter))
    }

    func onJobClick(_ jobId: String) {
        viewModel.onEvent(event: Helper().jobListIntentJobClick(jobId: jobId))
    }

    deinit {
        stateAdapter.cancel()
    }
}
