import SwiftUI
import sharedNewKit

struct OnboardingContainerView: View {
    @Environment(\.colorScheme) private var colorScheme
    @StateObject private var wrapper = OnboardingViewModelWrapper()
    @State private var activeStepIndex: Int = 0 // 0: Welcome, 1: Trade Select, 2: Profile Setup

    let onOnboardingComplete: () -> Void

    var body: some View {
        ZStack {
            AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

            VStack(spacing: 0) {
                // Mobbin-Style Top Progress Bar & Header
                HStack(spacing: 12) {
                    if activeStepIndex > 0 {
                        Button(action: {
                            withAnimation(.easeInOut(duration: 0.25)) {
                                if activeStepIndex == 2 {
                                    activeStepIndex = 1
                                    wrapper.onBackToTrades()
                                } else if activeStepIndex == 1 {
                                    activeStepIndex = 0
                                }
                            }
                        }) {
                            Image(systemName: "chevron.left")
                                .font(.system(size: 16, weight: .bold))
                                .foregroundColor(AppColors.textPrimary(for: colorScheme))
                                .frame(width: 32, height: 32)
                                .background(AppColors.cardBackground(for: colorScheme))
                                .clipShape(Circle())
                        }
                    } else {
                        Spacer().frame(width: 32)
                    }

                    // 3-Segment Progress Indicator Bar
                    HStack(spacing: 6) {
                        ForEach(0..<3, id: \.self) { index in
                            Rectangle()
                                .fill(index <= activeStepIndex ? AppColors.primary : AppColors.border(for: colorScheme))
                                .frame(height: 4)
                                .clipShape(Capsule())
                        }
                    }

                    Spacer().frame(width: 32)
                }
                .padding(.horizontal, 20)
                .padding(.top, 12)
                .padding(.bottom, 8)

                // Step Content Pages with Smooth Transition
                ZStack {
                    if activeStepIndex == 0 {
                        OnboardingWelcomeView(
                            onGetStarted: {
                                withAnimation(.easeInOut(duration: 0.25)) {
                                    wrapper.onGetStarted()
                                    activeStepIndex = 1
                                }
                            }
                        )
                        .transition(.asymmetric(insertion: .opacity, removal: .move(edge: .leading)))
                    } else if activeStepIndex == 1 {
                        TradeSelectionView(
                            selectedTrades: wrapper.state.selectedTrades,
                            canContinue: wrapper.state.canContinue,
                            onToggleTrade: { trade in
                                wrapper.onToggleTrade(trade: trade)
                            },
                            onConfirm: {
                                withAnimation(.easeInOut(duration: 0.25)) {
                                    wrapper.onConfirmTrades()
                                    activeStepIndex = 2
                                }
                            }
                        )
                        .transition(.asymmetric(insertion: .move(edge: .trailing), removal: .move(edge: .leading)))
                    } else {
                        WorkerProfileSetupView(
                            onProfileCompleted: {
                                wrapper.onLetsGo()
                                onOnboardingComplete()
                            }
                        )
                        .transition(.asymmetric(insertion: .move(edge: .trailing), removal: .opacity))
                    }
                }
            }
        }
    }
}
