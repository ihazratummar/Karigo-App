import SwiftUI
import Combine
import sharedNewKit

class HomeViewModelWrapper: ObservableObject {
    private let viewModel: HomeViewModel
    private var stateAdapter = FlowAdapter<HomeState>()

    @Published var state: HomeState

    init(viewModel: HomeViewModel = Helper().getHomeViewModel()) {
        self.viewModel = viewModel
        self.state = (viewModel.state.value as? HomeState) ?? HomeState(isLoading: false, selectedTrades: [], jobs: [], workerProfileModel: nil)

        stateAdapter.subscribe(flow: CFlow(origin: viewModel.state)) { [weak self] newState in
            self?.state = newState
        }
    }

    deinit {
        stateAdapter.cancel()
    }
}
