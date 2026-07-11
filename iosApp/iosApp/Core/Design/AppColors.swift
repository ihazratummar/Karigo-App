//
//  AppColors.swift
//  iosApp
//
//  Created by Hazrat Ummar Shaikh on 08/07/26.
//

import SwiftUI

enum AppColors {
    // Core Backgrounds
    static let background = Color(hex: "0A0A0A")
    static let card = Color(hex: "1e1e1e")
    static let raised = Color(hex: "181818")
    static let modal = Color(hex: "141414")
    
    // Brand & Accents
    static let accent = Color(hex: "00D4AA") // From Primary / KarigojobsAccent
    static let accentBg = Color(hex: "1a3733")
    static let iconBg = Color(hex: "2A2A2A")
    static let selectedCard = Color(hex: "142e2b")
    
    // Status
    static let warning = Color(hex: "F5A623")
    static let error = Color(hex: "E05555")
    static let success = Color(hex: "4CAF50") // AmountDone
    
    // Typography
    static let textPrimary = Color(hex: "FFFFFF")
    static let textSecondary = Color(hex: "A1A1AA")
    static let textTertiary = Color(hex: "71717A")
    
    // Borders
    static let border = Color(hex: "242424")
    static let divider = Color(hex: "27272A")
}

extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }
        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}
