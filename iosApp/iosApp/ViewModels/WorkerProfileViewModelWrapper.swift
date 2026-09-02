import SwiftUI
import Combine
import sharedNewKit

class WorkerProfileViewModelWrapper: ObservableObject {
    private let viewModel: WorkerProfileViewModel
    private var stateAdapter = FlowAdapter<WorkerProfileState>()

    @Published var state: WorkerProfileState

    init(viewModel: WorkerProfileViewModel = Helper().getWorkerProfileViewModel()) {
        self.viewModel = viewModel
        self.state = (viewModel.state.value as? WorkerProfileState) ?? WorkerProfileState(currentStep: .ownerName, isLoading: false, ownerName: "", businessName: "", phoneNumber: "", email: "", address: "")

        stateAdapter.subscribe(flow: CFlow(origin: viewModel.state)) { [weak self] newState in
            self?.state = newState
        }
    }

    func onOwnerNameChanged(_ name: String) {
        viewModel.onEvent(event: WorkerProfileEventOwnerNameField(name: name))
    }

    func onBusinessNameChanged(_ name: String) {
        viewModel.onEvent(event: WorkerProfileEventBusinessNameField(name: name))
    }

    func onPhoneNumberChanged(_ phone: String) {
        viewModel.onEvent(event: WorkerProfileEventPhoneNumberField(phone: phone))
    }

    func onEmailChanged(_ email: String) {
        viewModel.onEvent(event: WorkerProfileEventEmailField(email: email))
    }

    func onAddressChanged(_ address: String) {
        viewModel.onEvent(event: WorkerProfileEventAddressField(address: address))
    }

    func onOwnerNameComplete() {
        viewModel.onEvent(event: WorkerProfileEventOwnerNameComplete())
    }

    func onBusinessNameComplete() {
        viewModel.onEvent(event: WorkerProfileEventBusinessNameCompete())
    }

    func onContactDetailsComplete() {
        viewModel.onEvent(event: WorkerProfileEventContactDetailsComplete())
    }

    func onExtraInfoComplete() {
        viewModel.onEvent(event: WorkerProfileEventExtraInfoComplete())
    }

    deinit {
        stateAdapter.cancel()
    }
}
