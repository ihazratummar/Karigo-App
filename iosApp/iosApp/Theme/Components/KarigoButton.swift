import SwiftUI

enum KarigoButtonStyle {
    case primary
    case secondary
    case outlined
    case danger
}

struct KarigoButton: View {
    @Environment(\.colorScheme) private var colorScheme
    let title: String
    let iconSystemName: String?
    let style: KarigoButtonStyle
    let isLoading: BooleanLiteralType
    let action: () -> Void

    init(
        title: String,
        iconSystemName: String? = nil,
        style: KarigoButtonStyle = .primary,
        isLoading: BooleanLiteralType = false,
        action: @escaping () -> Void
    ) {
        self.title = title
        self.iconSystemName = iconSystemName
        self.style = style
        self.isLoading = isLoading
        self.action = action
    }

    var body: some View {
        Button(action: action) {
            HStack(spacing: AppDimensions.spaceSm) {
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: textColor))
                } else {
                    if let iconSystemName = iconSystemName {
                        Image(systemName: iconSystemName)
                            .font(.system(size: AppDimensions.iconSm, weight: .semibold))
                    }
                    Text(title)
                        .font(.system(size: 16, weight: .bold))
                }
            }
            .frame(maxWidth: .infinity)
            .frame(height: AppDimensions.buttonHeight)
            .foregroundColor(textColor)
            .background(backgroundColor)
            .cornerRadius(AppDimensions.radiusMd)
            .overlay(
                RoundedRectangle(cornerRadius: AppDimensions.radiusMd)
                    .stroke(borderColor, lineWidth: style == .outlined ? 1 : 0)
            )
        }
        .disabled(isLoading)
    }

    private var backgroundColor: Color {
        switch style {
        case .primary:
            return AppColors.primary
        case .secondary:
            return AppColors.surfaceVariant(for: colorScheme)
        case .outlined:
            return .clear
        case .danger:
            return AppColors.error
        }
    }

    private var textColor: Color {
        switch style {
        case .primary:
            return Color.black
        case .secondary, .outlined:
            return AppColors.textPrimary(for: colorScheme)
        case .danger:
            return Color.white
        }
    }

    private var borderColor: Color {
        switch style {
        case .outlined:
            return AppColors.border(for: colorScheme)
        default:
            return .clear
        }
    }
}
