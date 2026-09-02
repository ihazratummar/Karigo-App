import SwiftUI
import sharedNewKit

struct JobDetailsView: View {
    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.presentationMode) private var presentationMode
    @StateObject private var wrapper: JobDetailsViewModelWrapper

    @State private var showingStatusActionSheet = false

    init(jobId: String) {
        _wrapper = StateObject(wrappedValue: JobDetailsViewModelWrapper(jobId: jobId))
    }

    var body: some View {
        ZStack {
            AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

            if let job = wrapper.state.jobModel {
                VStack(spacing: 0) {
                    ScrollView(showsIndicators: false) {
                        VStack(spacing: 20) {
                            // 1. Job Status Header Banner
                            HStack {
                                VStack(alignment: .leading, spacing: 4) {
                                    Text(job.title)
                                        .font(.system(size: 22, weight: .bold, design: .rounded))
                                        .foregroundColor(AppColors.textPrimary(for: colorScheme))

                                    Text(job.clientName)
                                        .font(.system(size: 15, weight: .medium))
                                        .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                }

                                Spacer()

                                Button(action: { showingStatusActionSheet = true }) {
                                    HStack(spacing: 4) {
                                        JobStatusBadge(status: job.status)
                                        Image(systemName: "chevron.down")
                                            .font(.system(size: 11, weight: .bold))
                                            .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                    }
                                }
                            }
                            .padding(16)
                            .background(AppColors.cardBackground(for: colorScheme))
                            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
                            .overlay(
                                RoundedRectangle(cornerRadius: 18, style: .continuous)
                                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                            )

                            // 2. Financial Summary Card
                            VStack(alignment: .leading, spacing: 12) {
                                Text("FINANCIAL BREAKDOWN")
                                    .font(.system(size: 11, weight: .bold))
                                    .foregroundColor(AppColors.textSecondary(for: colorScheme))

                                HStack {
                                    VStack(alignment: .leading, spacing: 2) {
                                        Text("Total Amount")
                                            .font(.system(size: 12, weight: .medium))
                                            .foregroundColor(AppColors.textSecondary(for: colorScheme))

                                        Text("₹\(Int(job.total))")
                                            .font(.system(size: 26, weight: .bold, design: .rounded))
                                            .foregroundColor(AppColors.primary)
                                    }

                                    Spacer()

                                    VStack(alignment: .trailing, spacing: 2) {
                                        Text("Payments Received")
                                            .font(.system(size: 12, weight: .medium))
                                            .foregroundColor(AppColors.textSecondary(for: colorScheme))

                                        Text("₹\(Int(wrapper.state.paymentsTotal))")
                                            .font(.system(size: 18, weight: .bold, design: .rounded))
                                            .foregroundColor(Color(hex: 0x34C759))
                                    }
                                }
                            }
                            .padding(16)
                            .background(AppColors.cardBackground(for: colorScheme))
                            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
                            .overlay(
                                RoundedRectangle(cornerRadius: 18, style: .continuous)
                                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                            )
                        }
                        .padding(.horizontal, 20)
                        .padding(.top, 16)
                    }

                    // Bottom Action Bar: PDF Invoice & WhatsApp Share
                    HStack(spacing: 12) {
                        Button(action: { wrapper.onGenerateInvoicePdf() }) {
                            HStack(spacing: 6) {
                                Image(systemName: "arrow.down.doc.fill")
                                    .font(.system(size: 15))
                                Text("Invoice PDF")
                                    .font(.system(size: 15, weight: .bold))
                            }
                            .foregroundColor(AppColors.textPrimary(for: colorScheme))
                            .frame(maxWidth: .infinity)
                            .frame(height: 48)
                            .background(AppColors.cardBackground(for: colorScheme))
                            .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                            .overlay(
                                RoundedRectangle(cornerRadius: 14, style: .continuous)
                                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                            )
                        }

                        Button(action: { wrapper.onShareInvoiceOnWhatsapp() }) {
                            HStack(spacing: 6) {
                                Image(systemName: "paperplane.fill")
                                    .font(.system(size: 15))
                                Text("WhatsApp")
                                    .font(.system(size: 15, weight: .bold))
                            }
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 48)
                            .background(AppColors.whatsApp)
                            .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                            .shadow(color: AppColors.whatsApp.opacity(0.35), radius: 6, x: 0, y: 3)
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 16)
                    .background(AppColors.background(for: colorScheme))
                }
            } else {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: AppColors.primary))
            }
        }
        .actionSheet(isPresented: $showingStatusActionSheet) {
            ActionSheet(
                title: Text("Change Job Status"),
                buttons: [
                    .default(Text("In Progress")) { wrapper.onChangeStatus(.inProgress) },
                    .default(Text("Completed")) { wrapper.onChangeStatus(.completed) },
                    .default(Text("Pending")) { wrapper.onChangeStatus(.pending) },
                    .default(Text("Invoiced")) { wrapper.onChangeStatus(.invoiced) },
                    .default(Text("Paid")) { wrapper.onChangeStatus(.paid) },
                    .cancel()
                ]
            )
        }
    }
}

private struct JobStatusBadge: View {
    let status: JobStatus

    var body: some View {
        Text(statusTitle)
            .font(.system(size: 11, weight: .bold))
            .foregroundColor(badgeColor)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .background(badgeColor.opacity(0.15))
            .clipShape(Capsule())
    }

    private var statusTitle: String {
        switch status {
        case .inProgress: return "IN PROGRESS"
        case .completed: return "COMPLETED"
        case .pending: return "PENDING"
        case .invoiced: return "INVOICED"
        case .paid: return "PAID"
        default: return "ACTIVE"
        }
    }

    private var badgeColor: Color {
        switch status {
        case .inProgress: return AppColors.primary
        case .completed, .paid: return Color(hex: 0x34C759)
        case .pending: return Color(hex: 0xFF9500)
        case .invoiced: return Color(hex: 0x5856D6)
        default: return AppColors.primary
        }
    }
}
