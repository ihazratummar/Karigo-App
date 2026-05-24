//
//  ContactsPermissionHandler.swift
//  iosApp
//
//  Created by Hazrat Ummar Shaikh on 23/05/26.
//
 import Contacts


class ContactsPermissionHandler {
    func request()async -> Bool {
        await withCheckedContinuation{continuation in
            CNContactStore().requestAccess(for: .contacts){granted, _ in
                continuation.resume(returning: granted)}
        }
    }
}
