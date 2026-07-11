//
//  AppTypography.swift
//  iosApp
//
//  Created by Hazrat Ummar Shaikh on 08/07/26.
//

import SwiftUI

enum AppTypography {
    static let heading1 = Font.system(size: 34, weight: .bold, design: .rounded)
    static let heading2 = Font.system(size: 28, weight: .bold, design: .rounded)
    static let heading3 = Font.system(size: 24, weight: .semibold, design: .rounded)
    
    static let bodyLarge = Font.system(size: 18, weight: .regular, design: .default)
    static let bodyRegular = Font.system(size: 16, weight: .regular, design: .default)
    static let bodySmall = Font.system(size: 14, weight: .regular, design: .default)
    
    static let button = Font.system(size: 16, weight: .semibold, design: .rounded)
    static let caption = Font.system(size: 12, weight: .medium, design: .default)
}
