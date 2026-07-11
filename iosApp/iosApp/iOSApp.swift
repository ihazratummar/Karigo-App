import SwiftUI
import sharedNewKit

@main
struct iOSApp: App {
    
    init() {
        // FirebaseApp.configure() // Requires GoogleService-Info.plist
        Helper().doInitKoinIos()
    }
    
    var body: some Scene {
        WindowGroup {
            MainTabView()
        }
    }
}

struct MainTabView: View {
    @State private var selectedTab = 0
    
    var body: some View {
        TabView(selection: $selectedTab) {
            HomeView()
                .tabItem {
                    Image(systemName: selectedTab == 0 ? "house.fill" : "house")
                    Text("Home")
                }
                .tag(0)
            
            Text("Jobs View Coming Soon")
                .tabItem {
                    Image(systemName: selectedTab == 1 ? "briefcase.fill" : "briefcase")
                    Text("Jobs")
                }
                .tag(1)
            
            Text("Clients View Coming Soon")
                .tabItem {
                    Image(systemName: selectedTab == 2 ? "person.2.fill" : "person.2")
                    Text("Clients")
                }
                .tag(2)
            
            Text("Materials View Coming Soon")
                .tabItem {
                    Image(systemName: selectedTab == 3 ? "shippingbox.fill" : "shippingbox")
                    Text("Materials")
                }
                .tag(3)
            
            Text("Settings View Coming Soon")
                .tabItem {
                    Image(systemName: selectedTab == 4 ? "gearshape.fill" : "gearshape")
                    Text("Settings")
                }
                .tag(4)
        }
        .accentColor(AppColors.accent)
    }
}