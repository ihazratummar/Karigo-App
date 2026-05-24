//
//  NotificationPermissionHandler.swift
//  iosApp
//
//  Created by Hazrat Ummar Shaikh on 23/05/26.
//


class NotificationPermissionHandler {
    func request() async -> Bool  {
        let center = UNUserNotificationCenter.current()
        return (try? await center.requestAuthorization(
            options: [.alert, .sound , .badge]
        )) ?? false
    }
}
