import SwiftUI
import sharedNewKit

struct HomeView: View {
    @Environment(\.colorScheme) private var colorScheme
    @StateObject private var viewmodel = HomeViewModelWrapper()



    @State private var showingAddJobSheet = false
    @State private var showingAddEstimateSheet = false

    var body: some View {
        NavigationView {
            ZStack {
                AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

                ScrollView(showsIndicators: false) {
                    VStack(spacing: 20) {
                        // 1. Top Greeting Header
                        HeaderGreetingSection(workerProfile: viewmodel.state.workerProfileModel)

                        // 2. Business Overview Summary Cards
                        BusinessMetricsOverview(
                            jobs: viewmodel.state.jobs ?? [],
                            trades: Array(viewmodel.state.selectedTrades)
                        )

                        // 3. Quick Action Buttons
                        QuickActionsGrid(
                            onAddJob: { showingAddJobSheet = true },
                            onNewEstimate: { showingAddEstimateSheet = true }
                        )

                        // 4. Recent / Active Jobs List Section
                        RecentJobsSection(jobs: viewmodel.state.jobs ?? [])
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 12)
                    .padding(.bottom, 24)
                }
            }
            .navigationBarHidden(true)
            .sheet(isPresented: $showingAddJobSheet) {
                AddJobView()
            }
            .sheet(isPresented: $showingAddEstimateSheet) {
                Text("Add Estimate Screen (Coming Soon)")
                    .font(.system(size: 16, weight: .bold))
            }
        }
    }
}


// MARK: - 1. Greeting Header
private struct HeaderGreetingSection: View {
    @Environment(\.colorScheme) private var colorScheme
    let workerProfile: WorkerProfileModel?

    var body: some View {
        HStack(alignment: .center, spacing: 14) {
            // Contractor Avatar Icon
            ZStack {
                Circle()
                    .fill(AppColors.primaryContainer(for: colorScheme))
                    .frame(width: 48, height: 48)

                Image(systemName: "person.crop.square.fill")
                    .font(.system(size: 22, weight: .bold))
                    .foregroundColor(AppColors.primary)
            }

            VStack(alignment: .leading, spacing: 2) {
                Text(greetingTimeText)
                    .font(.system(size: 12, weight: .semibold))
                    .foregroundColor(AppColors.textSecondary(for: colorScheme))

                Text(displayName)
                    .font(.system(size: 20, weight: .bold, design: .rounded))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))
                    .lineLimit(1)
            }

            Spacer()

            // Pro Status / App Badge
            HStack(spacing: 4) {
                Image(systemName: "shield.fill")
                    .font(.system(size: 11, weight: .bold))
                Text("PRO")
                    .font(.system(size: 11, weight: .bold))
            }
            .foregroundColor(.black)
            .padding(.horizontal, 10)
            .padding(.vertical, 5)
            .background(AppColors.primary)
            .clipShape(Capsule())
        }
        .padding(.top, 4)
    }

    private var displayName: String {
        if let name = workerProfile?.ownerName, !name.trimmingCharacters(in: .whitespaces).isEmpty {
            return name
        } else if let business = workerProfile?.businessName, !business.trimmingCharacters(in: .whitespaces).isEmpty {
            return business
        } else {
            return "Contractor"
        }
    }

    private var greetingTimeText: String {
        let hour = Calendar.current.component(.hour, from: Date())
        if hour < 12 { return "GOOD MORNING" }
        else if hour < 17 { return "GOOD AFTERNOON" }
        else { return "GOOD EVENING" }
    }
}

// MARK: - 2. Metrics Summary Overview
private struct BusinessMetricsOverview: View {
    @Environment(\.colorScheme) private var colorScheme
    let jobs: [JobModel]
    let trades: [TradeType]

    private var activeJobsCount: Int {
        jobs.filter { $0.status != .completed }.count
    }

    var body: some View {
        HStack(spacing: 12) {
            // Active Jobs Metric
            MetricCard(
                title: "Active Jobs",
                value: "\(activeJobsCount)",
                iconSystemName: "briefcase.fill",
                iconColor: AppColors.primary
            )

            // Total Work Orders Metric
            MetricCard(
                title: "Total Orders",
                value: "\(jobs.count)",
                iconSystemName: "doc.text.fill",
                iconColor: Color(hex: 0xFF9500)
            )
        }
    }
}

private struct MetricCard: View {
    @Environment(\.colorScheme) private var colorScheme
    let title: String
    let value: String
    let iconSystemName: String
    let iconColor: Color

    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .fill(iconColor.opacity(colorScheme == .dark ? 0.2 : 0.12))
                    .frame(width: 40, height: 40)

                Image(systemName: iconSystemName)
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(iconColor)
            }

            VStack(alignment: .leading, spacing: 2) {
                Text(value)
                    .font(.system(size: 22, weight: .bold, design: .rounded))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))

                Text(title)
                    .font(.system(size: 12, weight: .medium))
                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
            }

            Spacer(minLength: 0)
        }
        .padding(14)
        .background(AppColors.cardBackground(for: colorScheme))
        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
        )
        .shadow(color: Color.black.opacity(colorScheme == .dark ? 0.2 : 0.03), radius: 4, x: 0, y: 2)
    }
}

// MARK: - 3. Quick Actions Grid
private struct QuickActionsGrid: View {
    @Environment(\.colorScheme) private var colorScheme
    let onAddJob: () -> Void
    let onNewEstimate: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("QUICK ACTIONS")
                .font(.system(size: 11, weight: .bold))
                .foregroundColor(AppColors.textSecondary(for: colorScheme))

            HStack(spacing: 12) {
                QuickActionButton(
                    title: "New Job",
                    iconSystemName: "plus.circle.fill",
                    accentColor: AppColors.primary,
                    action: onAddJob
                )

                QuickActionButton(
                    title: "Estimate",
                    iconSystemName: "doc.badge.plus",
                    accentColor: Color(hex: 0xFF9500),
                    action: onNewEstimate
                )
            }
        }
    }
}

private struct QuickActionButton: View {
    @Environment(\.colorScheme) private var colorScheme
    let title: String
    let iconSystemName: String
    let accentColor: Color
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            VStack(spacing: 8) {
                ZStack {
                    Circle()
                        .fill(accentColor.opacity(colorScheme == .dark ? 0.2 : 0.12))
                        .frame(width: 44, height: 44)

                    Image(systemName: iconSystemName)
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(accentColor)
                }

                Text(title)
                    .font(.system(size: 13, weight: .semibold))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))
            }
            .frame(maxWidth: .infinity)
            .frame(height: 90)
            .background(AppColors.cardBackground(for: colorScheme))
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 16, style: .continuous)
                    .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
            )
            .shadow(color: Color.black.opacity(colorScheme == .dark ? 0.2 : 0.03), radius: 4, x: 0, y: 2)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

// MARK: - 4. Recent Jobs List Section
private struct RecentJobsSection: View {
    @Environment(\.colorScheme) private var colorScheme
    let jobs: [JobModel]

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Text("RECENT JOBS")
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(AppColors.textSecondary(for: colorScheme))

                Spacer()

                if !jobs.isEmpty {
                    Text("View All")
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(AppColors.primary)
                }
            }

            if jobs.isEmpty {
                // Clean Empty State
                VStack(spacing: 12) {
                    ZStack {
                        Circle()
                            .fill(AppColors.primaryContainer(for: colorScheme))
                            .frame(width: 60, height: 60)

                        Image(systemName: "wrench.and.screwdriver")
                            .font(.system(size: 26, weight: .medium))
                            .foregroundColor(AppColors.primary)
                    }

                    Text("No Active Jobs Yet")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(AppColors.textPrimary(for: colorScheme))

                    Text("Tap '+ New Job' to create your first contractor work order.")
                        .font(.system(size: 13, weight: .regular))
                        .foregroundColor(AppColors.textSecondary(for: colorScheme))
                        .multilineTextAlignment(.center)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 32)
                .padding(.horizontal, 16)
                .background(AppColors.cardBackground(for: colorScheme))
                .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
                .overlay(
                    RoundedRectangle(cornerRadius: 18, style: .continuous)
                        .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                )
            } else {
                VStack(spacing: 10) {
                    ForEach(jobs.prefix(5), id: \.id) { job in
                        JobRowCard(job: job)
                    }
                }
            }
        }
    }
}

private struct JobRowCard: View {
    @Environment(\.colorScheme) private var colorScheme
    let job: JobModel

    var body: some View {
        HStack(spacing: 12) {
            ZStack {
                Circle()
                    .fill(AppColors.primaryContainer(for: colorScheme))
                    .frame(width: 42, height: 42)

                Image(systemName: "briefcase.fill")
                    .font(.system(size: 18, weight: .semibold))
                    .foregroundColor(AppColors.primary)
            }

            VStack(alignment: .leading, spacing: 3) {
                Text(job.title)
                    .font(.system(size: 15, weight: .bold))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))
                    .lineLimit(1)

                Text(job.clientName)
                    .font(.system(size: 13, weight: .regular))
                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
                    .lineLimit(1)
            }

            Spacer()

            VStack(alignment: .trailing, spacing: 4) {
                Text("₹\(Int(job.total))")
                    .font(.system(size: 15, weight: .bold, design: .rounded))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))

                StatusBadge(status: job.status)
            }
        }
        .padding(14)
        .background(AppColors.cardBackground(for: colorScheme))
        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
        )
        .shadow(color: Color.black.opacity(colorScheme == .dark ? 0.2 : 0.03), radius: 4, x: 0, y: 2)
    }
}

private struct StatusBadge: View {
    let status: JobStatus

    var body: some View {
        Text(statusText)
            .font(.system(size: 11, weight: .bold))
            .foregroundColor(badgeColor)
            .padding(.horizontal, 8)
            .padding(.vertical, 3)
            .background(badgeColor.opacity(0.15))
            .clipShape(Capsule())
    }

    private var statusText: String {
        switch status {
        case .inProgress: return "IN PROGRESS"
        case .completed: return "COMPLETED"
        case .pending: return "PENDING"
        default: return "ACTIVE"
        }
    }

    private var badgeColor: Color {
        switch status {
        case .inProgress: return AppColors.primary
        case .completed: return Color(hex: 0x34C759)
        case .pending: return Color(hex: 0xFF9500)
        default: return AppColors.primary
        }
    }
}

