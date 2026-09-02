import SwiftUI
import sharedNewKit

@main
struct iOSApp: App {
    
    init() {
        Helper().doInitKoinIos()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
