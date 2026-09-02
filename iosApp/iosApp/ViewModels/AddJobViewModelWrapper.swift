import SwiftUI
import Combine
import sharedNewKit

class AddJobViewModelWrapper: ObservableObject {
    private let viewModel: AddJobViewModel
    private var stateAdapter = FlowAdapter<AddJobState>()

    @Published var state: AddJobState

    init(jobId: String? = nil, clientId: String? = nil) {
        let vm = Helper().getAddJobViewModel(jobId: jobId, clientId: clientId)
        self.viewModel = vm
        self.state = (vm.addJobState.value as? AddJobState) ?? AddJobState(
            jobId: nil,
            isLoading: false,
            clients: [],
            contacts: [],
            selectedClient: nil,
            tradeTypes: [],
            selectedTradeType: nil,
            title: "",
            status: .pending,
            isClientPickerModalOpen: false,
            labourItems: [],
            isLabourCreateModalOpen: false,
            labourItemDraft: LabourItem(itemName: "", itemRate: 0.0, quantity: 1, workersCount: 1, unit: "Unit"),
            labourLogs: [],
            availableMaterials: [],
            selectedMaterials: [],
            isMaterialPickerOpen: false,
            materialQuery: "",
            selectedMaterialTradeType: nil,
            selectedMaterialCategory: nil,
            materialCategories: [],
            includeLabourInInvoice: true,
            selectedLabourItemId: nil,
            isLabourLogsModalOpen: false,
            selectedLabourLogs: []
        )

        stateAdapter.subscribe(flow: CFlow(origin: vm.addJobState)) { [weak self] newState in
            self?.state = newState
        }
    }

    func onTitleChanged(_ title: String) {
        viewModel.onEvent(event: Helper().addJobIntentUpdateTitle(title: title))
    }

    func onSelectClient(_ client: ClientModel) {
        let contact = DeviceContact(id: client.id, name: client.name, phoneNumber: [client.phone])
        viewModel.onEvent(event: Helper().addJobIntentSelectClient(contact: contact))
    }

    func onSelectTradeType(_ trade: TradeType) {
        viewModel.onEvent(event: Helper().addJobIntentSelectTradeType(tradeType: trade))
    }

    func onSaveJob() {
        viewModel.onEvent(event: Helper().addJobIntentSaveJob())
    }

    deinit {
        stateAdapter.cancel()
    }
}
