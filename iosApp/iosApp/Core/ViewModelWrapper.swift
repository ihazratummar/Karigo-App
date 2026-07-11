import Foundation
import sharedNewKit

@MainActor
class HomeViewModelWrapper: ObservableObject {
    let viewModelWrapper = IosHomeViewModelWrapper()
    
    @Published var state: HomeState
    
    private var stateWatcher: Closeable?
    private var effectWatcher: Closeable?
    
    init() {
        self.state = viewModelWrapper.initialState
        startObserving()
    }
    
    func startObserving() {
        stateWatcher = viewModelWrapper.watchState { [weak self] newState in
            self?.state = newState
        }
        
        effectWatcher = viewModelWrapper.watchEffect { effect in
            // Handle effects like showing a snackbar, for example:
            if let errorEffect = effect as? HomeEffectShowError {
                print("Error from ViewModel: \(errorEffect.message)")
            }
        }
    }
    
    deinit {
        stateWatcher?.close()
        effectWatcher?.close()
    }
}
