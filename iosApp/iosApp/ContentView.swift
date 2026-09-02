import SwiftUI
import sharedNewKit

struct ContentView: View {
    @StateObject private var onboardingWrapper = OnboardingViewModelWrapper()

    var body: some View {
        Group {
            switch onboardingWrapper.completedState {
            case is OnboardingCompleteStateCompleted:
                TabBarView()
            case is OnboardingCompleteStateNotCompleted:
                OnboardingContainerView(
                    onOnboardingComplete: {
                        // Handled by KMP completedState update
                    }
                )
            default:
                ZStack {
                    AppColors.background(for: .dark).edgesIgnoringSafeArea(.all)
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: AppColors.primary))
                }
            }
        }
    }
}

