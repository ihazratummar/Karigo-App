import SwiftUI
import sharedNewKit

struct AddJobView: View {
    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.presentationMode) private var presentationMode
    @StateObject private var wrapper: AddJobViewModelWrapper

    init(jobId: String? = nil, clientId: String? = nil) {
        _wrapper = StateObject(wrappedValue: AddJobViewModelWrapper(jobId: jobId, clientId: clientId))
    }

    var body: some View {
        NavigationView {
            ZStack {
                AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

                VStack(spacing: 0) {
                    ScrollView(showsIndicators: false) {
                        VStack(spacing: 20) {
                            // Work Order Details Card
                            VStack(alignment: .leading, spacing: 14) {
                                Text("WORK ORDER DETAILS")
                                    .font(.system(size: 11, weight: .bold))
                                    .foregroundColor(AppColors.textSecondary(for: colorScheme))

                                // Job Title Input
                                VStack(alignment: .leading, spacing: 6) {
                                    Text("Job Title / Scope of Work")
                                        .font(.system(size: 13, weight: .semibold))
                                        .foregroundColor(AppColors.textPrimary(for: colorScheme))

                                    TextField("e.g. 3BHK Full Rewiring & DB Fitting", text: Binding(
                                        get: { wrapper.state.title },
                                        set: { wrapper.onTitleChanged($0) }
                                    ))
                                    .font(.system(size: 15, weight: .medium))
                                    .padding(.horizontal, 14)
                                    .frame(height: 48)
                                    .background(AppColors.cardBackground(for: colorScheme))
                                    .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
                                    .overlay(
                                        RoundedRectangle(cornerRadius: 12, style: .continuous)
                                            .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                                    )
                                }

                                // Client Selector Button
                                VStack(alignment: .leading, spacing: 6) {
                                    Text("Client")
                                        .font(.system(size: 13, weight: .semibold))
                                        .foregroundColor(AppColors.textPrimary(for: colorScheme))

                                    if let client = wrapper.state.selectedClient {
                                        HStack {
                                            Image(systemName: "person.fill")
                                                .foregroundColor(AppColors.primary)
                                            Text(client.name)
                                                .font(.system(size: 15, weight: .bold))
                                                .foregroundColor(AppColors.textPrimary(for: colorScheme))
                                            Spacer()
                                            Text(client.phone)
                                                .font(.system(size: 13))
                                                .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                        }
                                        .padding(14)
                                        .background(AppColors.primaryContainer(for: colorScheme))
                                        .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
                                        .overlay(
                                            RoundedRectangle(cornerRadius: 12, style: .continuous)
                                                .stroke(AppColors.primary, lineWidth: 1.5)
                                        )
                                    } else if !wrapper.state.clients.isEmpty {
                                        Menu {
                                            ForEach(wrapper.state.clients, id: \.id) { client in
                                                Button(action: { wrapper.onSelectClient(client) }) {
                                                    Label(client.name, systemImage: "person.fill")
                                                }
                                            }
                                        } label: {
                                            HStack {
                                                Image(systemName: "person.badge.plus")
                                                    .foregroundColor(AppColors.primary)
                                                Text("Select Client...")
                                                    .font(.system(size: 15, weight: .regular))
                                                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                                Spacer()
                                                Image(systemName: "chevron.up.chevron.down")
                                                    .font(.system(size: 12))
                                                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                            }
                                            .padding(14)
                                            .background(AppColors.cardBackground(for: colorScheme))
                                            .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
                                            .overlay(
                                                RoundedRectangle(cornerRadius: 12, style: .continuous)
                                                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                                            )
                                        }
                                    }
                                }
                            }

                            // Grand Total Summary Box
                            VStack(alignment: .leading, spacing: 10) {
                                HStack {
                                    Text("Est. Work Order Total")
                                        .font(.system(size: 14, weight: .semibold))
                                        .foregroundColor(AppColors.textSecondary(for: colorScheme))

                                    Spacer()

                                    Text("₹\(Int(wrapper.state.grandTotal))")
                                        .font(.system(size: 24, weight: .bold, design: .rounded))
                                        .foregroundColor(AppColors.primary)
                                }
                            }
                            .padding(16)
                            .background(AppColors.cardBackground(for: colorScheme))
                            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                            .overlay(
                                RoundedRectangle(cornerRadius: 16, style: .continuous)
                                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                            )
                        }
                        .padding(20)
                    }

                    // Save Action CTA Button
                    VStack {
                        Button(action: {
                            wrapper.onSaveJob()
                            presentationMode.wrappedValue.dismiss()
                        }) {
                            HStack(spacing: 8) {
                                Text("Create Work Order")
                                    .font(.system(size: 16, weight: .bold))

                                Image(systemName: "checkmark.circle.fill")
                                    .font(.system(size: 18, weight: .bold))
                            }
                            .foregroundColor(.black)
                            .frame(maxWidth: .infinity)
                            .frame(height: 52)
                            .background(wrapper.state.canContinue ? AppColors.primary : AppColors.surfaceVariant(for: colorScheme))
                            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                            .shadow(color: wrapper.state.canContinue ? AppColors.primary.opacity(0.35) : Color.clear, radius: 8, x: 0, y: 4)
                        }
                        .disabled(!wrapper.state.canContinue)
                        .opacity(wrapper.state.canContinue ? 1.0 : 0.5)
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 16)
                }
            }
            .navigationTitle("New Work Order")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") { presentationMode.wrappedValue.dismiss() }
                        .foregroundColor(AppColors.textPrimary(for: colorScheme))
                }
            }
        }
    }
}
