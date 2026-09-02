import SwiftUI
import sharedNewKit

struct WorkerProfileSetupView: View {
    @Environment(\.colorScheme) private var colorScheme
    @StateObject private var wrapper = WorkerProfileViewModelWrapper()
    let onProfileCompleted: () -> Void

    var body: some View {
        ZStack {
            AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

            VStack(spacing: 0) {
                // Header Title Section
                VStack(alignment: .leading, spacing: 4) {
                    Text("Setup Business Profile")
                        .font(.system(size: 28, weight: .bold, design: .rounded))
                        .foregroundColor(AppColors.textPrimary(for: colorScheme))

                    Text("This details appear on your invoices, estimates, and customer records.")
                        .font(.system(size: 14, weight: .regular))
                        .foregroundColor(AppColors.textSecondary(for: colorScheme))
                        .lineSpacing(2)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 20)
                .padding(.top, 12)
                .padding(.bottom, 12)

                ScrollView(showsIndicators: false) {
                    VStack(spacing: 24) {
                        // Profile Avatar Emblem
                        ZStack(alignment: .bottomTrailing) {
                            Circle()
                                .fill(AppColors.primaryContainer(for: colorScheme))
                                .frame(width: 84, height: 84)

                            Image(systemName: "person.crop.square.fill")
                                .font(.system(size: 38, weight: .bold))
                                .foregroundColor(AppColors.primary)

                            ZStack {
                                Circle()
                                    .fill(AppColors.primary)
                                    .frame(width: 26, height: 26)

                                Image(systemName: "pencil")
                                    .font(.system(size: 13, weight: .bold))
                                    .foregroundColor(.black)
                            }
                        }
                        .padding(.top, 4)

                        // Native Inset Form Container
                        VStack(spacing: 16) {
                            NativeFormInputField(
                                sectionTitle: "OWNER / CONTRACTOR NAME",
                                placeholder: "e.g. Hazrat Ummar",
                                iconSystemName: "person.fill",
                                text: Binding(
                                    get: { wrapper.state.ownerName },
                                    set: { wrapper.onOwnerNameChanged($0) }
                                ),
                                textContentType: .name,
                                capitalization: .words
                            )

                            NativeFormInputField(
                                sectionTitle: "BUSINESS / FIRM NAME",
                                placeholder: "e.g. Ummar Electric & Contracting",
                                iconSystemName: "building.2.fill",
                                text: Binding(
                                    get: { wrapper.state.businessName },
                                    set: { wrapper.onBusinessNameChanged($0) }
                                ),
                                textContentType: .organizationName,
                                capitalization: .words
                            )

                            NativeFormInputField(
                                sectionTitle: "WHATSAPP / PHONE NUMBER",
                                placeholder: "e.g. +91 98765 43210",
                                iconSystemName: "phone.fill",
                                text: Binding(
                                    get: { wrapper.state.phoneNumber },
                                    set: { wrapper.onPhoneNumberChanged($0) }
                                ),
                                keyboardType: .phonePad,
                                textContentType: .telephoneNumber
                            )

                            NativeFormInputField(
                                sectionTitle: "CITY / SERVICE LOCATION",
                                placeholder: "e.g. Mumbai, Maharashtra",
                                iconSystemName: "mappin.circle.fill",
                                text: Binding(
                                    get: { wrapper.state.address },
                                    set: { wrapper.onAddressChanged($0) }
                                ),
                                textContentType: .addressCity,
                                capitalization: .words
                            )
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 24)
                }

                // Sticky Bottom Save CTA Button
                VStack(spacing: 6) {
                    Button(action: {
                        wrapper.onExtraInfoComplete()
                        onProfileCompleted()
                    }) {
                        HStack(spacing: 8) {
                            Text("Save Profile & Launch Karigo")
                                .font(.system(size: 16, weight: .bold))

                            Image(systemName: "checkmark.circle.fill")
                                .font(.system(size: 18, weight: .bold))
                        }
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(height: 52)
                        .background(AppColors.primary)
                        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                        .shadow(color: AppColors.primary.opacity(0.35), radius: 8, x: 0, y: 4)
                    }
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
}

private struct NativeFormInputField: View {
    @Environment(\.colorScheme) private var colorScheme
    let sectionTitle: String
    let placeholder: String
    let iconSystemName: String
    @Binding var text: String
    var keyboardType: UIKeyboardType = .default
    var textContentType: UITextContentType? = nil
    var capitalization: UITextAutocapitalizationType = .none

    @FocusState private var isFocused: Bool

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(sectionTitle)
                .font(.system(size: 11, weight: .bold))
                .foregroundColor(isFocused ? AppColors.primary : AppColors.textSecondary(for: colorScheme))

            HStack(spacing: 12) {
                Image(systemName: iconSystemName)
                    .font(.system(size: 17, weight: .semibold))
                    .foregroundColor(isFocused ? AppColors.primary : AppColors.textSecondary(for: colorScheme))
                    .frame(width: 22)

                TextField(placeholder, text: $text)
                    .font(.system(size: 15, weight: .medium))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))
                    .keyboardType(keyboardType)
                    .textContentType(textContentType)
                    .autocapitalization(capitalization)
                    .focused($isFocused)

                if !text.isEmpty {
                    Button(action: { text = "" }) {
                        Image(systemName: "xmark.circle.fill")
                            .font(.system(size: 16))
                            .foregroundColor(AppColors.textSecondary(for: colorScheme))
                    }
                }
            }
            .padding(.horizontal, 14)
            .frame(height: 50)
            .background(AppColors.cardBackground(for: colorScheme))
            .clipShape(RoundedRectangle(cornerRadius: 14, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 14, style: .continuous)
                    .stroke(
                        isFocused ? AppColors.primary : AppColors.border(for: colorScheme),
                        lineWidth: isFocused ? 2 : 1
                    )
            )
            .shadow(
                color: Color.black.opacity(colorScheme == .dark ? 0.2 : 0.03),
                radius: isFocused ? 6 : 4,
                x: 0,
                y: 2
            )
        }
    }
}
