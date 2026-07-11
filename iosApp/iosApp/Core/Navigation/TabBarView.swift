import SwiftUI

struct TabBarView: View {
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Image(systemName: "house.fill")
                    Text("Home")
                }
            
            Text("Settings Screen (Coming Soon)")
                .font(AppTypography.heading2)
                .tabItem {
                    Image(systemName: "gearshape.fill")
                    Text("Settings")
                }
        }
        .accentColor(AppColors.accent) // Color for the selected tab
    }
}

#Preview {
    TabBarView()
}
