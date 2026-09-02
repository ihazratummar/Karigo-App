import SwiftUI
import UIKit

struct AppColors {
    // Dynamic Colors adapting to System HIG / Selected Theme
    static func background(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x0A0A0A) : Color(UIColor.systemGroupedBackground)
    }
    
    static func cardBackground(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x1C1C1E) : Color(UIColor.secondarySystemGroupedBackground)
    }
    
    static func surfaceVariant(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x2C2C2E) : Color(UIColor.tertiarySystemFill)
    }
    
    static func border(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x3A3A3C) : Color(UIColor.separator)
    }
    
    static func textPrimary(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color.white : Color(UIColor.label)
    }
    
    static func textSecondary(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x8E8E93) : Color(UIColor.secondaryLabel)
    }
    
    static func textTertiary(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? Color(hex: 0x636366) : Color(UIColor.tertiaryLabel)
    }

    // Brand Accents
    static let primary = Color(hex: 0x00D4AA)
    static let primaryDim = Color(hex: 0x00A884)
    static let primaryContainerLight = Color(hex: 0xE6FBF7)
    static let primaryContainerDark = Color(hex: 0x0D2E28)
    static let whatsApp = Color(hex: 0x25D366)

    static func primaryContainer(for colorScheme: ColorScheme) -> Color {
        colorScheme == .dark ? primaryContainerDark : primaryContainerLight
    }

    static let statusInProgress = Color(hex: 0x00D4AA)
    static let statusPending = Color(hex: 0xFF9500)
    static let error = Color(hex: 0xFF3B30)
}

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255.0,
            green: Double((hex >> 8) & 0xff) / 255.0,
            blue: Double(hex & 0xff) / 255.0,
            opacity: alpha
        )
    }
}
