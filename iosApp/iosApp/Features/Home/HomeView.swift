import SwiftUI
import sharedNewKit

struct HomeView: View {
    @StateObject private var viewModelWrapper = HomeViewModelWrapper()
    
    var body: some View {
        NavigationView {
            ZStack {
                AppColors.background.ignoresSafeArea()
                
                ScrollView {
                    VStack(alignment: .leading, spacing: AppSpacing.lg) {
                        
                        // Header (HomeTopAppBar)
                        HomeTopAppBar(profile: viewModelWrapper.state.workerProfileModel)
                        
                        // Action Banner
                        if viewModelWrapper.state.workerProfileModel == nil {
                            ActionNeedBanner()
                        }
                        
                        // Trades (ScrollableTradeView)
                        ScrollableTradeView(
                            trades: Array(viewModelWrapper.state.selectedTrades),
                            onAddTrade: {
                                // Add trade action
                            }
                        )
                        
                        // Estimates
                        SiteEstimatesCard()
                        
                        // Recent Jobs Header
                        HStack {
                            Text("Recent Jobs")
                                .font(AppTypography.heading3)
                                .foregroundColor(AppColors.textPrimary)
                            Spacer()
                            Button(action: {
                                // See All Jobs action
                            }) {
                                Text("See All")
                                    .font(AppTypography.button)
                                    .foregroundColor(AppColors.accent)
                            }
                        }
                        .padding(.horizontal, AppSpacing.md)
                        
                        // Job List
                        if let jobs = viewModelWrapper.state.jobs as? [JobModel] {
                            ForEach(jobs, id: \.id) { job in
                                JobCard(job: job)
                            }
                        } else if viewModelWrapper.state.isLoading {
                            ProgressView()
                                .progressViewStyle(CircularProgressViewStyle(tint: AppColors.accent))
                                .frame(maxWidth: .infinity)
                        }
                        
                        Spacer(minLength: 80) // Space for FAB and TabBar
                    }
                    .padding(.top, AppSpacing.md)
                }
            }
            .navigationBarHidden(true)
            .overlay(
                // Floating Action Button
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        Button(action: {
                            // FAB Action
                        }) {
                            Image(systemName: "plus")
                                .font(.system(size: 24, weight: .bold))
                                .foregroundColor(.black)
                                .frame(width: 56, height: 56)
                                .background(AppColors.accent)
                                .clipShape(Circle())
                                .shadow(color: AppColors.accent.opacity(0.4), radius: 8, x: 0, y: 4)
                        }
                        .padding(.trailing, AppSpacing.lg)
                        .padding(.bottom, AppSpacing.lg)
                    }
                }
            )
        }
    }
}

// MARK: - Subcomponents

struct HomeTopAppBar: View {
    let profile: WorkerProfileModel?
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: AppSpacing.xxs) {
                if let profile = profile {
                    Text("Hi \(profile.ownerName)")
                        .font(AppTypography.heading2)
                        .foregroundColor(AppColors.textPrimary)
                    Text("Let's get to work")
                        .font(AppTypography.bodyRegular)
                        .foregroundColor(AppColors.textSecondary)
                }
            }
            Spacer()
            
            Button(action: {
                // Notification Action
            }) {
                Image(systemName: "bell.fill")
                    .foregroundColor(AppColors.textPrimary)
                    .padding(AppSpacing.sm)
                    .background(AppColors.iconBg)
                    .clipShape(Circle())
            }
        }
        .padding(.horizontal, AppSpacing.md)
    }
}

struct ActionNeedBanner: View {
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: AppSpacing.xxs) {
                Text("Action Needed")
                    .font(AppTypography.bodyLarge.bold())
                    .foregroundColor(AppColors.textPrimary)
                Text("Complete your profile to get started.")
                    .font(AppTypography.bodySmall)
                    .foregroundColor(AppColors.textSecondary)
            }
            Spacer()
            Image(systemName: "chevron.right")
                .foregroundColor(AppColors.accent)
        }
        .padding(AppSpacing.md)
        .background(AppColors.card)
        .cornerRadius(12)
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(AppColors.border, lineWidth: 1)
        )
        .padding(.horizontal, AppSpacing.md)
    }
}

struct ScrollableTradeView: View {
    let trades: [TradeType]
    let onAddTrade: () -> Void
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: AppSpacing.sm) {
                // Mocking trades for now since we don't have the explicit swift property names yet
                ForEach(0..<3) { _ in
                    Text("Trade Name")
                        .font(AppTypography.bodySmall)
                        .foregroundColor(AppColors.accent)
                        .padding(.horizontal, AppSpacing.md)
                        .padding(.vertical, AppSpacing.xs)
                        .background(AppColors.accentBg)
                        .cornerRadius(16)
                        .overlay(
                            RoundedRectangle(cornerRadius: 16)
                                .stroke(AppColors.accent.opacity(0.3), lineWidth: 1)
                        )
                }
            }
            .padding(.horizontal, AppSpacing.md)
        }
    }
}

struct SiteEstimatesCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: AppSpacing.md) {
            HStack {
                Text("Site Estimates")
                    .font(AppTypography.bodyLarge.bold())
                    .foregroundColor(AppColors.textPrimary)
                Spacer()
                Button(action: {}) {
                    Image(systemName: "plus.circle.fill")
                        .foregroundColor(AppColors.accent)
                        .font(.system(size: 24))
                }
            }
            
            Text("No recent estimates")
                .font(AppTypography.bodyRegular)
                .foregroundColor(AppColors.textSecondary)
                .frame(maxWidth: .infinity, alignment: .center)
                .padding(.vertical, AppSpacing.lg)
        }
        .padding(AppSpacing.md)
        .background(AppColors.card)
        .cornerRadius(16)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(AppColors.border, lineWidth: 1)
        )
        .padding(.horizontal, AppSpacing.md)
    }
}

struct JobCard: View {
    let job: JobModel // Will map to KMP Job model
    
    var body: some View {
        VStack(alignment: .leading, spacing: AppSpacing.xs) {
            HStack {
                Text(job.title)
                    .font(AppTypography.bodyLarge.bold())
                    .foregroundColor(AppColors.textPrimary)
                Spacer()
                Text("Active")
                    .font(AppTypography.caption)
                    .foregroundColor(AppColors.accent)
                    .padding(.horizontal, 8)
                    .padding(.vertical, 4)
                    .background(AppColors.accentBg)
                    .cornerRadius(8)
            }
            Text(job.clientName)
                .font(AppTypography.bodySmall)
                .foregroundColor(AppColors.textSecondary)
        }
        .padding(AppSpacing.md)
        .background(AppColors.card)
        .cornerRadius(12)
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(AppColors.border, lineWidth: 1)
        )
        .padding(.horizontal, AppSpacing.md)
    }
}
