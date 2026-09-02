import SwiftUI

struct TabBarView: View {
    @Environment(\.colorScheme) private var colorScheme
    @State private var selectedTab = 0

    var body: some View {
        TabView(selection: $selectedTab) {
            HomeView()
                .tabItem {
                    Label("Home", systemImage: "house.fill")
                }
                .tag(0)

            JobListView()
                .tabItem {
                    Label("Jobs", systemImage: "briefcase.fill")
                }
                .tag(1)

            Text("Clients Screen (Coming in Next Step)")
                .tabItem {
                    Label("Clients", systemImage: "person.2.fill")
                }
                .tag(2)

            Text("Materials Screen (Coming in Next Step)")
                .tabItem {
                    Label("Materials", systemImage: "tray.full.fill")
                }
                .tag(3)

            SettingView()
                .tabItem {
                    Label("Settings", systemImage: "gearshape.fill")
                }
                .tag(4)
        }
        .accentColor(AppColors.primary)
    }
}
