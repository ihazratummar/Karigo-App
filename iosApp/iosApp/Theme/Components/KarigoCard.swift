import SwiftUI

struct KarigoCard<Content: View>: View {
    @Environment(\.colorScheme) private var colorScheme
    let padding: CGFloat
    let cornerRadius: CGFloat
    let content: () -> Content

    init(
        padding: CGFloat = AppDimensions.spaceBase,
        cornerRadius: CGFloat = AppDimensions.radiusMd,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.padding = padding
        self.cornerRadius = cornerRadius
        self.content = content
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            content()
        }
        .padding(padding)
        .background(AppColors.cardBackground(for: colorScheme))
        .cornerRadius(cornerRadius)
        .overlay(
            RoundedRectangle(cornerRadius: cornerRadius)
                .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
        )
    }
}
