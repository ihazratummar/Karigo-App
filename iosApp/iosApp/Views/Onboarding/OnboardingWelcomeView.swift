import SwiftUI

struct OnboardingWelcomeView: View {
    @Environment(\.colorScheme) private var colorScheme
    let onGetStarted: () -> Void

    var body: some View {
        ZStack {
            // Background Base with subtle gradient glow
            AppColors.background(for: colorScheme).edgesIgnoringSafeArea(.all)

            VStack(spacing: 0) {
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 28) {
                        Spacer().frame(height: 12)

                        // Hero Branding Emblem with Radial Glow
                        ZStack {
                            Circle()
                                .fill(
                                    RadialGradient(
                                        colors: [AppColors.primary.opacity(0.25), Color.clear],
                                        center: .center,
                                        startRadius: 20,
                                        endRadius: 90
                                    )
                                )
                                .frame(width: 140, height: 140)

                            Circle()
                                .fill(AppColors.primary)
                                .frame(width: 76, height: 76)
                                .shadow(color: AppColors.primary.opacity(0.4), radius: 16, x: 0, y: 8)

                            Image(systemName: "wrench.and.screwdriver.fill")
                                .font(.system(size: 34, weight: .bold))
                                .foregroundColor(.black)
                        }

                        // App Name & Tagline
                        VStack(spacing: 10) {
                            HStack(spacing: 8) {
                                Text("PRO CONTRACTOR COMPANION")
                                    .font(.system(size: 11, weight: .bold))
                                    .foregroundColor(AppColors.primary)
                                    .padding(.horizontal, 10)
                                    .padding(.vertical, 4)
                                    .background(AppColors.primary.opacity(0.12))
                                    .clipShape(Capsule())
                            }

                            Text("Karigo")
                                .font(.system(size: 40, weight: .black, design: .rounded))
                                .foregroundColor(AppColors.textPrimary(for: colorScheme))

                            Text("Run your contracting business like a pro.\nJobs, estimates, materials & client billing — all in one place.")
                                .font(.system(size: 15, weight: .regular))
                                .foregroundColor(AppColors.textSecondary(for: colorScheme))
                                .multilineTextAlignment(.center)
                                .lineSpacing(4)
                                .padding(.horizontal, 24)
                        }

                        // Premium Feature Cards
                        VStack(spacing: 14) {
                            WelcomeFeatureCard(
                                iconSystemName: "briefcase.fill",
                                iconGradient: [Color(hex: 0x00D4AA), Color(hex: 0x00A884)],
                                title: "Jobs & Work Logs",
                                description: "Track active sites, labour logs, status updates, and client contacts effortlessly."
                            )

                            WelcomeFeatureCard(
                                iconSystemName: "doc.text.fill",
                                iconGradient: [Color(hex: 0xFFB800), Color(hex: 0xE6A100)],
                                title: "Estimates & Professional Invoices",
                                description: "Create itemized PDF estimates and send WhatsApp invoices with 1-tap."
                            )

                            WelcomeFeatureCard(
                                iconSystemName: "tray.full.fill",
                                iconGradient: [Color(hex: 0x00E5FF), Color(hex: 0x00B8D4)],
                                title: "Material Price Book",
                                description: "Pre-loaded trade starter kits & custom material rate manager."
                            )
                        }
                        .padding(.horizontal, 16)
                    }
                    .padding(.bottom, 20)
                }

                // Bottom Sticky CTA
                VStack(spacing: 12) {
                    Button(action: onGetStarted) {
                        HStack(spacing: 10) {
                            Text("Get Started")
                                .font(.system(size: 17, weight: .bold))

                            Image(systemName: "arrow.right")
                                .font(.system(size: 16, weight: .bold))
                        }
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(height: 54)
                        .background(AppColors.primary)
                        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
                        .shadow(color: AppColors.primary.opacity(0.35), radius: 12, x: 0, y: 6)
                    }

                    Text("No login required • Fully offline-first & secure")
                        .font(.system(size: 12, weight: .medium))
                        .foregroundColor(AppColors.textTertiary(for: colorScheme))
                }
                .padding(.horizontal, 20)
                .padding(.top, 12)
                .padding(.bottom, 24)
                .background(
                    AppColors.background(for: colorScheme)
                        .shadow(color: Color.black.opacity(0.15), radius: 10, x: 0, y: -4)
                )
            }
        }
    }
}

private struct WelcomeFeatureCard: View {
    @Environment(\.colorScheme) private var colorScheme
    let iconSystemName: String
    let iconGradient: [Color]
    let title: String
    let description: String

    var body: some View {
        HStack(alignment: .top, spacing: 16) {
            ZStack {
                RoundedRectangle(cornerRadius: 14, style: .continuous)
                    .fill(
                        LinearGradient(
                            colors: iconGradient,
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 46, height: 46)

                Image(systemName: iconSystemName)
                    .font(.system(size: 20, weight: .semibold))
                    .foregroundColor(.black)
            }

            VStack(alignment: .leading, spacing: 4) {
                Text(title)
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(AppColors.textPrimary(for: colorScheme))

                Text(description)
                    .font(.system(size: 13, weight: .regular))
                    .foregroundColor(AppColors.textSecondary(for: colorScheme))
                    .lineSpacing(2)
                    .fixedSize(horizontal: false, vertical: true)
            }

            Spacer(minLength: 0)
        }
        .padding(16)
        .background(AppColors.cardBackground(for: colorScheme))
        .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
        .overlay(
            RoundedRectangle(cornerRadius: 18, style: .continuous)
                .stroke(AppColors.border(for: colorScheme), lineWidth: 1)
        )
    }
}
