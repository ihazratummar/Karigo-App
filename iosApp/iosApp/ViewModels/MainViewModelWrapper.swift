import SwiftUI
import Combine
import sharedNewKit

class MainViewModelWrapper: ObservableObject {
    private let viewModel: MainViewModel
    private var preferencesAdapter = FlowAdapter<AppPreferences>()

    @Published var preferences: AppPreferences?

    init(viewModel: MainViewModel = Helper().getMainViewModel()) {
        self.viewModel = viewModel
        self.preferences = viewModel.appPreferences.value as? AppPreferences
        
        preferencesAdapter.subscribe(flow: CFlow(origin: viewModel.appPreferences)) { [weak self] prefs in
            self?.preferences = prefs
        }
    }

    deinit {
        preferencesAdapter.cancel()
    }
}
