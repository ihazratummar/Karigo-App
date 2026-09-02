import SwiftUI
import Combine
import sharedNewKit

class OnboardingViewModelWrapper: ObservableObject {
    private let viewModel: OnboardingViewModel
    private var stateAdapter = FlowAdapter<OnboardingState>()
    private var completedAdapter = FlowAdapter<OnboardingCompleteState>()

    @Published var state: OnboardingState
    @Published var completedState: OnboardingCompleteState

    init(viewModel: OnboardingViewModel = Helper().getOnboardingViewModel()) {
        self.viewModel = viewModel
        self.state = (viewModel.state.value as? OnboardingState) ?? OnboardingState(currentStep: .welcome, selectedTrades: [], isLoading: false, seededMaterialCount: 0, error: nil, totalMaterialCount: 0)
        self.completedState = (viewModel.completedState.value as? OnboardingCompleteState) ?? OnboardingCompleteStateLoading()

        stateAdapter.subscribe(flow: CFlow(origin: viewModel.state)) { [weak self] newState in
            self?.state = newState
        }

        completedAdapter.subscribe(flow: CFlow(origin: viewModel.completedState)) { [weak self] newCompletedState in
            self?.completedState = newCompletedState
        }
    }

    func onGetStarted() {
        viewModel.onIntent(intent: Helper().onboardingIntentGetStarted())
    }

    func onToggleTrade(trade: TradeType) {
        viewModel.onIntent(intent: Helper().onboardingIntentToggleTrade(trade: trade))
    }

    func onConfirmTrades() {
        viewModel.onIntent(intent: Helper().onboardingIntentConfirmTrades())
    }

    func onLetsGo() {
        viewModel.onIntent(intent: Helper().onboardingIntentLetsGo())
    }

    func onBackToTrades() {
        viewModel.onIntent(intent: Helper().onboardingIntentBackToTrades())
    }

    func onDismissError() {
        viewModel.onIntent(intent: Helper().onboardingIntentDismissError())
    }

    deinit {
        stateAdapter.cancel()
        completedAdapter.cancel()
    }
}
