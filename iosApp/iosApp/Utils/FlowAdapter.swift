import Foundation
import Combine
import sharedNewKit

class FlowAdapter<T: AnyObject> {
    private var closeable: Closeable?

    func subscribe(flow: CFlow<T>, onValue: @escaping (T) -> Void) {
        closeable = flow.watch { value in
            DispatchQueue.main.async {
                onValue(value)
            }
        }
    }

    func cancel() {
        closeable?.close()
        closeable = nil
    }

    deinit {
        cancel()
    }
}
