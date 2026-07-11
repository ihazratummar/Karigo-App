import SwiftUI
import sharedNewKit

struct ContentView: View {
    var body: some View {
        // ContentView acts as our app's main entry point.
        // We route directly to the TabBarView for navigation.
        TabBarView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
