import SwiftUI
import sharedNewKit

struct TradeSelectionView: View {
    @Environment(\.colorScheme) private var colorScheme
    let selectedTrades: Set<TradeType>
    let canContinue: BooleanLiteralType
    let onToggleTrade: (TradeType) -> Void
    let onConfirm: () -> Void

    @State private var searchText: String = ""
    private let allTrades: [TradeType] = Helper().getAllTradeTypes()

    private var filteredTrades: [TradeType] {
        if searchText.trimmingCharacters(in: .whitespaces).isEmpty {
            return allTrades
        } else {
            return allTrades.filter { trade in
                formattedName(trade).localizedCaseInsensitiveContains(searchText) ||
                trade.name.localizedCaseInsensitiveContains(searchText)
            }
        }
    }

    private let columns = [
        GridItem(.flexible(), spacing: 12),
        GridItem(.flexible(), spacing: 12)
    ]

    var body: some View {
        ZStack {
            AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

            VStack(spacing: 0) {
                // Header Area
                VStack(alignment: .leading, spacing: 10) {
                    HStack(alignment: .top) {
                        VStack(alignment: .leading, spacing: 4) {
                            Text("Select Your Trade")
                                .font(.system(size: 28, weight: .bold, design: .rounded))
                                .foregroundColor(AppColors.textPrimary(for: colorScheme))

                            Text("Select one or more trades to configure default rates and material lists.")
                                .font(.system(size: 14, weight: .regular))
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                .lineSpacing(2)
                        }

                        Spacer()

                        // Selection Counter Pill
                        HStack(spacing: 4) {
                            Image(systemName: "checkmark.circle.fill")
                                .font(.system(size: 13, weight: .bold))
                            Text("\(selectedTrades.count)")
                                .font(.system(size: 13, weight: .bold))
                        }
                        .foregroundColor(selectedTrades.isEmpty ? AppColors.textSecondary(for: colorScheme) : .black)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 6)
                        .background(selectedTrades.isEmpty ? AppColors.surfaceVariant(for: colorScheme) : AppColors.primary)
                        .clipShape(Capsule())
                    }

                    // Search Field
                    HStack(spacing: 8) {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(AppColors.textSecondary(for: colorScheme))

                        TextField("Search trades (e.g. Electrician, Plumber)...", text: $searchText)
                            .font(.system(size: 15, weight: .regular))
                            .foregroundColor(AppColors.textPrimary(for: colorScheme))

                        if !searchText.isEmpty {
                            Button(action: { searchText = "" }) {
                                Image(systemName: "xmark.circle.fill")
                                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
                            }
                        }
                    }
                    .padding(.horizontal, 12)
                    .padding(.vertical, 10)
                    .background(AppColors.cardBackground(for: colorScheme))
                    .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
                    .overlay(
                        RoundedRectangle(cornerRadius: 12, style: .continuous)
                            .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
                    )
                }
                .padding(.horizontal, 20)
                .padding(.top, 12)
                .padding(.bottom, 12)

                // Trade Cards Grid
                ScrollView(showsIndicators: false) {
                    LazyVGrid(columns: columns, spacing: 12) {
                        ForEach(filteredTrades, id: \.name) { trade in
                            let isSelected = selectedTrades.contains(trade)
                            NativeTradeTile(
                                trade: trade,
                                isSelected: isSelected,
                                onTap: {
                                    withAnimation(.spring(response: 0.25, dampingFraction: 0.75)) {
                                        onToggleTrade(trade)
                                    }
                                }
                            )
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 6)
                }

                // Sticky Bottom Action CTA
                VStack(spacing: 6) {
                    Button(action: onConfirm) {
                        HStack(spacing: 8) {
                            Text(selectedTrades.isEmpty ? "Select at least 1 trade" : "Continue with \(selectedTrades.count) Trade\(selectedTrades.count == 1 ? "" : "s")")
                                .font(.system(size: 16, weight: .bold))

                            Image(systemName: "arrow.right")
                                .font(.system(size: 15, weight: .bold))
                        }
                        .foregroundColor(canContinue ? .black : AppColors.textSecondary(for: colorScheme))
                        .frame(maxWidth: .infinity)
                        .frame(height: 52)
                        .background(canContinue ? AppColors.primary : AppColors.surfaceVariant(for: colorScheme))
                        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                        .shadow(color: canContinue ? AppColors.primary.opacity(0.3) : Color.clear, radius: 8, x: 0, y: 4)
                    }
                    .disabled(!canContinue)
                }
                .padding(.horizontal, 20)
                .padding(.top, 12)
                .padding(.bottom, 24)
                .background(
                    AppColors.background(for: colorScheme)
                        .shadow(color: Color.black.opacity(colorScheme == .dark ? 0.3 : 0.06), radius: 8, x: 0, y: -4)
                )
            }
        }
    }

    private func formattedName(_ trade: TradeType) -> String {
        trade.name
            .replacingOccurrences(of: "_", with: " ")
            .capitalized
    }
}

private struct NativeTradeTile: View {
    @Environment(\.colorScheme) private var colorScheme
    let trade: TradeType
    let isSelected: Bool
    let onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack(spacing: 12) {
                // Vibrant Icon Circle Container
                ZStack {
                    Circle()
                        .fill(iconBackgroundColor(for: trade))
                        .frame(width: 42, height: 42)

                    Image(systemName: iconForTrade(trade))
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(iconColor(for: trade))
                }

                // Trade Title
                Text(formattedName(trade))
                    .font(.system(size: 14, weight: isSelected ? .bold : .semibold))
                    .foregroundColor(isSelected ? AppColors.primary : AppColors.textPrimary(for: colorScheme))
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                    .minimumScaleFactor(0.85)

                Spacer(minLength: 0)

                // Selection Checkmark Badge
                if isSelected {
                    Image(systemName: "checkmark.circle.fill")
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(AppColors.primary)
                }
            }
            .padding(.horizontal, 12)
            .frame(height: 64)
            .background(
                isSelected
                ? AppColors.primaryContainer(for: colorScheme)
                : AppColors.cardBackground(for: colorScheme)
            )
            .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 16, style: .continuous)
                    .stroke(
                        isSelected ? AppColors.primary : AppColors.border(for: colorScheme),
                        lineWidth: isSelected ? 2 : 1
                    )
            )
            .shadow(
                color: Color.black.opacity(colorScheme == .dark ? 0.25 : 0.04),
                radius: isSelected ? 6 : 4,
                x: 0,
                y: isSelected ? 3 : 2
            )
            .scaleEffect(isSelected ? 1.02 : 1.0)
        }
        .buttonStyle(PlainButtonStyle())
    }

    private func formattedName(_ trade: TradeType) -> String {
        trade.name
            .replacingOccurrences(of: "_", with: " ")
            .capitalized
    }

    private func iconForTrade(_ trade: TradeType) -> String {
        switch trade.name {
        case "ELECTRICIAN": return "bolt.fill"
        case "PLUMBER": return "drop.fill"
        case "CARPENTER": return "hammer.fill"
        case "MASON": return "square.grid.3x3.fill"
        case "PAINTER": return "paintpalette.fill"
        case "AC_TECHNICIAN": return "snowflake"
        case "WELDER": return "flame.fill"
        case "CIVIL_CONTRACTOR": return "building.2.fill"
        case "CCTV_SECURITY": return "video.fill"
        case "APPLIANCE_REPAIR": return "wrench.and.screwdriver.fill"
        case "CAR_BIKE_MECHANIC": return "car.fill"
        case "WATERPROOFING": return "umbrella.fill"
        case "PEST_CONTROL": return "ant.fill"
        case "SOLAR_INSTALLER": return "sun.max.fill"
        case "ALUMINIUM_UPVC": return "rectangle.split.2x2.fill"
        case "BORE_WELL": return "arrow.down.circle.fill"
        case "GAS_LPG_FITTER": return "flame"
        case "NETWORK_SUPPORT": return "wifi"
        default: return "wrench.fill"
        }
    }

    private func iconColor(for trade: TradeType) -> Color {
        switch trade.name {
        case "ELECTRICIAN": return Color(hex: 0xFFB800)
        case "PLUMBER": return Color(hex: 0x007AFF)
        case "CARPENTER": return Color(hex: 0xFF9500)
        case "PAINTER": return Color(hex: 0xAF52DE)
        case "MASON": return Color(hex: 0x8E8E93)
        case "AC_TECHNICIAN": return Color(hex: 0x32ADE6)
        case "WELDER": return Color(hex: 0xFF3B30)
        case "CIVIL_CONTRACTOR": return Color(hex: 0x34C759)
        case "CCTV_SECURITY": return Color(hex: 0x5856D6)
        case "APPLIANCE_REPAIR": return Color(hex: 0xFF2D55)
        default: return AppColors.primary
        }
    }

    private func iconBackgroundColor(for trade: TradeType) -> Color {
        iconColor(for: trade).opacity(colorScheme == .dark ? 0.2 : 0.12)
    }
}
