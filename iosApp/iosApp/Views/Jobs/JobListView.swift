import SwiftUI
import sharedNewKit

struct JobListView: View {
    @Environment(\.colorScheme) private var colorScheme
    @StateObject private var wrapper = JobListViewModelWrapper()

    @State private var showingAddJobSheet = false
    @State private var selectedJobIdForDetails: String? = nil

    var body: some View {
        NavigationView {
            ZStack {
                AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

                VStack(spacing: 0) {
                    // Header & Search
                    VStack(spacing: 12) {
                        HStack {
                            Text("Jobs & Work Orders")
                                .font(.system(size: 28, weight: .bold, design: .rounded))
                                .foregroundColor(AppColors.textPrimary(for: colorScheme))

                            Spacer()

                            Button(action: { showingAddJobSheet = true }) {
                                Image(systemName: "plus")
                                    .font(.system(size: 16, weight: .bold))
                                    .foregroundColor(.black)
                                    .frame(width: 38, height: 38)
                                    .background(AppColors.primary)
                                    .clipShape(Circle())
                                    .shadow(color: AppColors.primary.opacity(0.35), radius: 6, x: 0, y: 3)
                            }
                        }

                        // Search Field
                        HStack(spacing: 8) {
                            Image(systemName: "magnifyingglass")
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))

                            TextField("Search jobs or client name...", text: Binding(
                                get: { wrapper.state.searchJobText },
                                set: { wrapper.onSearchTextChanged($0) }
                            ))
                            .font(.system(size: 15))
                            .foregroundColor(AppColors.textPrimary(for: colorScheme))

                            if !wrapper.state.searchJobText.isEmpty {
                                Button(action: { wrapper.onSearchTextChanged("") }) {
                                    Image(systemName: "xmark.circle.fill")
                                        .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                }
                            }
                        }
                        .padding(.horizontal, 12)
                        .padding(.vertical, 10)
                        .background(AppColors.cardBackground(for: colorScheme))
                        .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12, style: .continuous)
                                .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                        )
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 12)
                    .padding(.bottom, 10)

                    // Status Filter Horizontal Scroll Bar
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 8) {
                            FilterPill(
                                title: "All",
                                count: wrapper.state.jobs.count,
                                isSelected: wrapper.state.jobStatusFilter is JobStatusFilterAll,
                                onTap: { wrapper.onFilterSelected(JobStatusFilterAll()) }
                            )

                            FilterPill(
                                title: "Pending",
                                count: countForStatus(.pending),
                                isSelected: isSelectedStatus(.pending),
                                onTap: { wrapper.onFilterSelected(JobStatusFilterStatus(status: .pending)) }
                            )

                            FilterPill(
                                title: "In Progress",
                                count: countForStatus(.inProgress),
                                isSelected: isSelectedStatus(.inProgress),
                                onTap: { wrapper.onFilterSelected(JobStatusFilterStatus(status: .inProgress)) }
                            )

                            FilterPill(
                                title: "Completed",
                                count: countForStatus(.completed),
                                isSelected: isSelectedStatus(.completed),
                                onTap: { wrapper.onFilterSelected(JobStatusFilterStatus(status: .completed)) }
                            )

                            FilterPill(
                                title: "Paid",
                                count: countForStatus(.paid),
                                isSelected: isSelectedStatus(.paid),
                                onTap: { wrapper.onFilterSelected(JobStatusFilterStatus(status: .paid)) }
                            )
                        }
                        .padding(.horizontal, 20)
                        .padding(.vertical, 6)
                    }

                    // Jobs Card List
                    if wrapper.state.jobs.isEmpty {
                        // Empty State View
                        VStack(spacing: 14) {
                            Spacer()

                            ZStack {
                                Circle()
                                    .fill(AppColors.primaryContainer(for: colorScheme))
                                    .frame(width: 72, height: 72)

                                Image(systemName: "briefcase")
                                    .font(.system(size: 32, weight: .medium))
                                    .foregroundColor(AppColors.primary)
                            }

                            Text("No Jobs Found")
                                .font(.system(size: 18, weight: .bold))
                                .foregroundColor(AppColors.textPrimary(for: colorScheme))

                            Text("Create a work order or adjust your search filter to view jobs.")
                                .font(.system(size: 14, weight: .regular))
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                .multilineTextAlignment(.center)
                                .padding(.horizontal, 32)

                            Button(action: { showingAddJobSheet = true }) {
                                HStack(spacing: 6) {
                                    Image(systemName: "plus")
                                        .font(.system(size: 14, weight: .bold))
                                    Text("Create New Job")
                                        .font(.system(size: 15, weight: .bold))
                                }
                                .foregroundColor(.black)
                                .padding(.horizontal, 20)
                                .padding(.vertical, 12)
                                .background(AppColors.primary)
                                .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
                            }
                            .padding(.top, 8)

                            Spacer()
                        }
                    } else {
                        ScrollView(showsIndicators: false) {
                            LazyVStack(spacing: 12) {
                                ForEach(wrapper.state.jobs, id: \.id) { job in
                                    JobCardTile(
                                        job: job,
                                        onTap: { selectedJobIdForDetails = job.id }
                                    )
                                }
                            }
                            .padding(.horizontal, 20)
                            .padding(.vertical, 12)
                        }
                    }
                }
            }
            .navigationBarHidden(true)
        }
    }

    private func countForStatus(_ status: JobStatus) -> Int {
        wrapper.state.jobs.filter { $0.status == status }.count
    }

    private func isSelectedStatus(_ status: JobStatus) -> Bool {
        if let filterStatus = wrapper.state.jobStatusFilter as? JobStatusFilterStatus {
            return filterStatus.status == status
        }
        return false
    }
}

private struct FilterPill: View {
    @Environment(\.colorScheme) private var colorScheme
    let title: String
    let count: Int
    let isSelected: Bool
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack(spacing: 6) {
                Text(title)
                    .font(.system(size: 13, weight: isSelected ? .bold : .medium))

                Text("\(count)")
                    .font(.system(size: 11, weight: .bold))
                    .padding(.horizontal, 6)
                    .padding(.vertical, 2)
                    .background(isSelected ? Color.black.opacity(0.2) : AppColors.border(for: colorScheme))
                    .clipShape(Capsule())
            }
            .foregroundColor(isSelected ? .black : AppColors.textPrimary(for: colorScheme))
            .padding(.horizontal, 12)
            .padding(.vertical, 8)
            .background(isSelected ? AppColors.primary : AppColors.cardBackground(for: colorScheme))
            .clipShape(Capsule())
            .overlay(
                Capsule()
                    .stroke(isSelected ? AppColors.primary : AppColors.border(for: colorScheme), lineWidth: 1)
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}

private struct JobCardTile: View {
    @Environment(\.colorScheme) private var colorScheme
    let job: JobModel
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            VStack(alignment: .leading, spacing: 12) {
                HStack(alignment: .top) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text(job.title)
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(AppColors.textPrimary(for: colorScheme))
                            .lineLimit(1)

                        HStack(spacing: 6) {
                            Image(systemName: "person.fill")
                                .font(.system(size: 12))
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))

                            Text(job.clientName)
                                .font(.system(size: 13, weight: .medium))
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))
                        }
                    }

                    Spacer()

                    JobStatusPill(status: job.status)
                }

                Divider()
                    .background(AppColors.border(for: colorScheme))

                HStack {
                    HStack(spacing: 4) {
                        Image(systemName: "wrench.and.screwdriver.fill")
                            .font(.system(size: 12))
                            .foregroundColor(AppColors.primary)

                        Text(formattedTradeName(job.tradeType))
                            .font(.system(size: 12, weight: .semibold))
                            .foregroundColor(AppColors.primary)
                    }
                    .padding(.horizontal, 8)
                    .padding(.vertical, 4)
                    .background(AppColors.primaryContainer(for: colorScheme))
                    .clipShape(Capsule())

                    Spacer()

                    Text("₹\(Int(job.total))")
                        .font(.system(size: 18, weight: .bold, design: .rounded))
                        .foregroundColor(AppColors.textPrimary(for: colorScheme))
                }
            }
            .padding(16)
            .background(AppColors.cardBackground(for: colorScheme))
            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 18, style: .continuous)
                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
            )
            .shadow(color: Color.black.opacity(colorScheme == .dark ? 0.2 : 0.04), radius: 6, x: 0, y: 3)
        }
        .buttonStyle(PlainButtonStyle())
    }

    private func formattedTradeName(_ trade: TradeType) -> String {
        trade.name.replacingOccurrences(of: "_", with: " ").capitalized
    }
}

private struct JobStatusPill: View {
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
