import SwiftUI

struct KarigoIconView: View {
    @Environment(\.colorScheme) private var colorScheme
    let systemName: String
    let iconColor: Color
    let backgroundColor: Color?
    let size: CGFloat

    init(
        systemName: String,
        iconColor: Color = AppColors.primary,
        backgroundColor: Color? = nil,
        size: CGFloat = AppDimensions.iconSm
    ) {
        self.systemName = systemName
        self.iconColor = iconColor
        self.backgroundColor = backgroundColor
        self.size = size
    }

    var body: some View {
        Image(systemName: systemName)
            .font(.system(size: size, weight: .semibold))
            .foregroundColor(iconColor)
            .padding(AppDimensions.spaceSm)
            .background(backgroundColor ?? AppColors.primaryContainer(for: colorScheme).opacity(0.3))
            .clipShape(Circle())
    }
}
