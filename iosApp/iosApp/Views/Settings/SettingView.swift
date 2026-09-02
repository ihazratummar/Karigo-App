import SwiftUI
import sharedNewKit

struct SettingView: View {
    @Environment(\.colorScheme) private var colorScheme

    var body: some View {
        NavigationView {
            ZStack {
                AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

                ScrollView(showsIndicators: false) {
                    VStack(spacing: 20) {
                        Text("Settings Screen")
                            .font(.system(size: 24, weight: .bold))
                            .foregroundColor(AppColors.textPrimary(for: colorScheme))
                    }
                    .padding(20)
                }
            }
            .navigationBarHidden(true)
        }
    }
}
