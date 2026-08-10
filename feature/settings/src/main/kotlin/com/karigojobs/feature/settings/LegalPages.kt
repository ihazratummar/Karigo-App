package com.karigojobs.feature.settings

sealed interface LegalContentItem {
    data class Paragraph(val text: String) : LegalContentItem
    data class BulletList(val items: List<String>) : LegalContentItem
    data class Table(val headers: List<String>, val rows: List<List<TableCell>>) : LegalContentItem
    data class Callout(val text: String, val type: CalloutType) : LegalContentItem
    data class Subheading(val text: String) : LegalContentItem
    data class ContactBlock(
        val appName: String,
        val developer: String,
        val email: String,
        val grievanceOfficer: String,
        val responseTime: String
    ) : LegalContentItem
}

sealed interface TableCell {
    data class Text(val text: String) : TableCell
    data class Badge(val text: String, val type: BadgeType) : TableCell
}

enum class CalloutType {
    GREEN, AMBER, BLUE, RED
}

enum class BadgeType {
    YES, NO, OPT
}

data class LegalCallout(
    val text: String,
    val type: CalloutType
)

data class LegalSection(
    val number: String,
    val title: String,
    val content: List<LegalContentItem>
)

data class LegalDocument(
    val key: String,
    val title: String,
    val badge: String,
    val callout: LegalCallout? = null,
    val sections: List<LegalSection>
)

object LegalPages {
    val documents: Map<String, LegalDocument> by lazy {
        mapOf(
            "privacy" to LegalDocument(
                key = "privacy",
                title = "Privacy Policy",
                badge = "Document 01 · Privacy Policy",
                callout = LegalCallout(
                    text = "Plain language summary: Karigo is an offline app. All the data you enter — clients, jobs, materials, invoices — lives only on your device. We have no server. We cannot see your data. The only external data processing is your subscription status through RevenueCat and Google Play.",
                    type = CalloutType.GREEN
                ),
                sections = listOf(
                    LegalSection(
                        number = "01",
                        title = "Who We Are",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo (\"we\", \"our\", \"us\", \"the Developer\") is a mobile application developed and published by an independent solo developer for a global audience. The application is available on Google Play Store under the package name com.karigojobs.app."),
                            LegalContentItem.Paragraph("Karigo is a job logging, material tracking, and invoice generation tool designed for field workers, tradesmen, and contractors. The application is designed to function entirely without internet connectivity for all core features. For all enquiries relating to this Privacy Policy, contact us at: hazratummar9@gmail.com")
                        )
                    ),
                    LegalSection(
                        number = "02",
                        title = "Scope of This Policy",
                        content = listOf(
                            LegalContentItem.Paragraph("This Privacy Policy applies to your use of the Karigo mobile application on Android devices. It describes how we handle information in connection with your use of the app, including data that may be processed by third-party service providers on our behalf."),
                            LegalContentItem.Paragraph("This policy does not apply to third-party websites, applications, or services that may be linked from within our app (such as Google Play or WhatsApp). Those services are governed by their own privacy policies."),
                            LegalContentItem.Paragraph("This policy applies globally to all users of Karigo, including users in India, the European Economic Area (GDPR), California, USA (CCPA/CPRA), and all other jurisdictions.")
                        )
                    ),
                    LegalSection(
                        number = "03",
                        title = "Data We Do Not Collect",
                        content = listOf(
                            LegalContentItem.Callout(
                                text = "Karigo does not operate any servers. We do not transmit, store, or have any access to the business data you enter into the application. The following categories of data are never collected by Karigo:",
                                type = CalloutType.GREEN
                            ),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Client names, phone numbers, or addresses",
                                    "Job records, materials, labour rates, or any business data",
                                    "Invoice contents, amounts, or payment records",
                                    "Your business name, logo, or UPI ID",
                                    "Device location or GPS coordinates",
                                    "Contact list or call logs",
                                    "Photos taken within the app",
                                    "Any biometric data",
                                    "Any financial account information",
                                    "Email address (unless you contact us voluntarily)"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "04",
                        title = "Data Processed by Third-Party Services",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo uses the following third-party services, each of which may process limited data as described below. We do not control how these services collect or process data beyond what is described here."),
                            LegalContentItem.Subheading("4.1 Google Play Billing"),
                            LegalContentItem.Paragraph("When you purchase the Pro subscription, your payment is processed entirely by Google Play. Google may process your Google account identifier, payment method details, and transaction history. Karigo does not receive your payment card details, bank account information, or UPI credentials at any time."),
                            LegalContentItem.Subheading("4.2 Zero Advertising IDs & No Cross-App Tracking"),
                            LegalContentItem.Paragraph("Karigo explicitly blocks Google Advertising ID (AAID) collection (`AD_ID` permission removed) and disables ad personalization signals. Karigo contains no advertising networks and does not track users across apps or websites. We do not display advertisements. We do not sell or share data with any third party for advertising purposes."),
                            LegalContentItem.Subheading("4.3 Anonymized & Aggregate Usage Diagnostics"),
                            LegalContentItem.Paragraph("Karigo logs high-level aggregate usage metrics (such as screen views and feature interactions) to improve app stability. IP addresses are automatically anonymized, and no personally identifiable information (no names, phone numbers, client data, or financial figures) is ever collected or transmitted."),
                            LegalContentItem.Table(
                                headers = listOf("Data type", "Collected by Karigo?", "Where stored"),
                                rows = listOf(
                                    listOf(
                                        TableCell.Text("Business data (clients, jobs, invoices)"),
                                        TableCell.Badge("Not by Karigo", BadgeType.NO),
                                        TableCell.Text("Your device only")
                                    ),
                                    listOf(
                                        TableCell.Text("Purchase token / subscription status"),
                                        TableCell.Badge("Google Play only", BadgeType.YES),
                                        TableCell.Text("Google Play servers")
                                    ),
                                    listOf(
                                        TableCell.Text("Payment details"),
                                        TableCell.Badge("Not by Karigo", BadgeType.NO),
                                        TableCell.Text("Google servers only")
                                    ),
                                    listOf(
                                        TableCell.Text("Crash diagnostics"),
                                        TableCell.Badge("Only if opted in on device", BadgeType.OPT),
                                        TableCell.Text("Google Play Console")
                                    ),
                                    listOf(
                                        TableCell.Text("Location, contacts, microphone, SMS"),
                                        TableCell.Badge("Never", BadgeType.NO),
                                        TableCell.Text("N/A — not accessed")
                                    )
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "05",
                        title = "Data Stored Locally on Your Device",
                        content = listOf(
                            LegalContentItem.Paragraph("All business data entered into Karigo is stored exclusively in a local SQLite database on your device (implemented via SQLDelight). This data is contained within Android's sandboxed private app storage and is not accessible by other applications without root access."),
                            LegalContentItem.Paragraph("Locally stored data includes: client profiles, job records, material rate library, job material entries, invoice records, and your business settings (name, phone, UPI ID, and logo image)."),
                            LegalContentItem.Callout(
                                text = "Data loss risk: Because your data is stored only on your device, it will be permanently lost if you uninstall Karigo, factory-reset your device, or lose your device without first exporting your data. We have no ability to recover lost data on your behalf. We strongly recommend using the CSV export feature in Settings regularly.",
                                type = CalloutType.AMBER
                            )
                        )
                    ),
                    LegalSection(
                        number = "06",
                        title = "Android Permissions We Request",
                        content = listOf(
                            LegalContentItem.Table(
                                headers = listOf("Permission", "Purpose", "Required?"),
                                rows = listOf(
                                    listOf(
                                        TableCell.Text("CAMERA"),
                                        TableCell.Text("Capture photos of your business logo or job site photos using CameraX. Photos are saved to device private storage only — never uploaded."),
                                        TableCell.Badge("Optional", BadgeType.OPT)
                                    ),
                                    listOf(
                                        TableCell.Text("READ_MEDIA_IMAGES (API 33+) / READ_EXTERNAL_STORAGE"),
                                        TableCell.Text("Select an existing photo from your gallery to use as your business logo."),
                                        TableCell.Badge("Optional", BadgeType.OPT)
                                    ),
                                    listOf(
                                        TableCell.Text("WRITE_EXTERNAL_STORAGE (API ≤ 28)"),
                                        TableCell.Text("Save PDF invoices to your device's Downloads folder so they can be accessed externally."),
                                        TableCell.Badge("Optional", BadgeType.OPT)
                                    ),
                                    listOf(
                                        TableCell.Text("POST_NOTIFICATIONS (API 33+)"),
                                        TableCell.Text("Send local on-device reminders about unpaid invoices and overdue jobs. No server is involved — all notifications are generated entirely on-device."),
                                        TableCell.Badge("Optional", BadgeType.OPT)
                                    ),
                                    listOf(
                                        TableCell.Text("RECEIVE_BOOT_COMPLETED"),
                                        TableCell.Text("Reschedule local WorkManager notification jobs after a device restart."),
                                        TableCell.Badge("Required (notifications only)", BadgeType.YES)
                                    ),
                                    listOf(
                                        TableCell.Text("INTERNET"),
                                        TableCell.Text("Used exclusively by RevenueCat and Google Play Billing to verify subscription status when connectivity is available. Core app features work fully without active internet."),
                                        TableCell.Badge("Required (billing only)", BadgeType.YES)
                                    )
                                )
                            ),
                            LegalContentItem.Paragraph("Karigo does not request access to Location, Microphone, Contacts, Call Logs, SMS, Sensors, or any other sensitive Android permissions.")
                        )
                    ),
                    LegalSection(
                        number = "07",
                        title = "How We Use Information",
                        content = listOf(
                            LegalContentItem.Paragraph("The limited data processed through RevenueCat and Google Play is used solely for:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Subscription verification: To determine whether your Pro subscription is active and unlock Pro features accordingly",
                                    "Purchase acknowledgment: Google Play requires all purchases to be acknowledged within 3 days to prevent automatic refunds — RevenueCat handles this on our behalf",
                                    "Purchase restoration: When you reinstall the app or change devices, to restore your Pro access via your Google account",
                                    "Bug diagnosis: Anonymised crash reports (if opted in at Android system level) are used solely to identify and fix technical bugs in future versions"
                                )
                            ),
                            LegalContentItem.Paragraph("We do not use any data for profiling, targeted advertising, marketing, sale to third parties, or any purpose other than those stated above.")
                        )
                    ),
                    LegalSection(
                        number = "08",
                        title = "Legal Bases for Processing (GDPR)",
                        content = listOf(
                            LegalContentItem.Paragraph("For users in the European Economic Area, we process data on the following legal bases under the General Data Protection Regulation (EU) 2016/679:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Contract performance (Art. 6(1)(b) GDPR): Processing your subscription purchase token through RevenueCat is necessary to fulfil our contract with you to provide Pro features.",
                                    "Legitimate interests (Art. 6(1)(f) GDPR): Processing anonymised crash reports to improve app stability, where such interests are not overridden by your rights.",
                                    "Consent (Art. 6(1)(a) GDPR): Processing of usage and diagnostics data through Android's opt-in system, where you have given consent via your device settings."
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "09",
                        title = "Data Sharing & Disclosure",
                        content = listOf(
                            LegalContentItem.Paragraph("We do not sell, rent, trade, or otherwise transfer your personal information to third parties for commercial purposes."),
                            LegalContentItem.Paragraph("The only data that leaves your device is:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Your Google Play purchase token — shared with RevenueCat solely for subscription verification",
                                    "Anonymised crash diagnostics — shared with Google Play Console only if you have opted in at the device level"
                                )
                            ),
                            LegalContentItem.Paragraph("Legal disclosures: We may disclose information to comply with applicable law, a valid legal process, governmental request, or court order. As an offline app with no server, we hold no business data that could be disclosed. Only RevenueCat subscription records would be subject to such disclosure."),
                            LegalContentItem.Paragraph("WhatsApp sharing: When you use Karigo's \"Share via WhatsApp\" feature to send a PDF invoice, this is initiated entirely by you through Android's system share intent. Karigo does not intercept or log the content of shares. The privacy policies of WhatsApp apply to that transfer.")
                        )
                    ),
                    LegalSection(
                        number = "10",
                        title = "International Data Transfers",
                        content = listOf(
                            LegalContentItem.Paragraph("RevenueCat is a US-based company. When your subscription purchase token is transmitted to RevenueCat's servers, this constitutes a transfer of data to the United States. RevenueCat complies with applicable data protection frameworks including the EU-US Data Privacy Framework for transfers from the European Economic Area."),
                            LegalContentItem.Paragraph("By using Karigo's Pro subscription features, you consent to this limited international transfer of your purchase token for the sole purpose of subscription verification.")
                        )
                    ),
                    LegalSection(
                        number = "11",
                        title = "Data Retention & Deletion",
                        content = listOf(
                            LegalContentItem.Subheading("On-device data"),
                            LegalContentItem.Paragraph("All data stored locally on your device is retained until you delete it manually within the app or uninstall Karigo. Uninstalling the app will delete all local data permanently."),
                            LegalContentItem.Subheading("RevenueCat data"),
                            LegalContentItem.Paragraph("RevenueCat retains purchase records in accordance with their own retention policy. To request deletion of your RevenueCat data, email us at hazratummar9@gmail.com with subject \"RevenueCat Data Deletion\" and we will submit a deletion request on your behalf."),
                            LegalContentItem.Subheading("How to delete your data"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Delete all on-device data: Android Settings → Apps → Karigo → Storage → Clear Data",
                                    "Uninstall: Removes all local data permanently",
                                    "Request RevenueCat deletion: Email hazratummar9@gmail.com"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "12",
                        title = "Children's Privacy",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is designed for working adults — specifically tradesmen, field workers, and contractors — and is not directed at children under the age of 13 (or 16 in EU jurisdictions under GDPR, or 18 in jurisdictions where that age applies)."),
                            LegalContentItem.Paragraph("We do not knowingly collect personal information from children. If you believe a child has used Karigo and provided personal information, please contact us at hazratummar9@gmail.com immediately.")
                        )
                    ),
                    LegalSection(
                        number = "13",
                        title = "Your Rights",
                        content = listOf(
                            LegalContentItem.Paragraph("Depending on your country of residence, you may have the following rights:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Right to access: Request a copy of personal data held about you",
                                    "Right to rectification: Request correction of inaccurate personal data",
                                    "Right to erasure: Request deletion of your personal data",
                                    "Right to data portability: Export all your on-device data at any time via Settings → Export CSV",
                                    "Right to object: Object to processing of your personal data",
                                    "Right to withdraw consent: Disable crash reporting in Android Settings → Privacy → Usage & Diagnostics at any time",
                                    "Right to lodge a complaint: Lodge a complaint with your national data protection authority"
                                )
                            ),
                            LegalContentItem.Paragraph("These rights are guaranteed under India's Digital Personal Data Protection Act 2023, the EU GDPR, the UK GDPR, and the CCPA/CPRA (California). To exercise any right, email hazratummar9@gmail.com. We will respond within 30 days.")
                        )
                    ),
                    LegalSection(
                        number = "14",
                        title = "Security",
                        content = listOf(
                            LegalContentItem.Paragraph("Your data is stored in a SQLite database within Android's sandboxed private app storage, not accessible to other apps. All communication with RevenueCat is encrypted using TLS 1.2 or higher. PDF files generated by Karigo are stored in private storage and are only shared when you explicitly initiate a share action."),
                            LegalContentItem.Paragraph("While we implement appropriate technical measures, no security system is infallible. We recommend keeping your device locked, running a current Android version, and keeping Karigo updated to the latest version.")
                        )
                    ),
                    LegalSection(
                        number = "15",
                        title = "Changes to This Policy",
                        content = listOf(
                            LegalContentItem.Paragraph("We may update this Privacy Policy when we make material changes to the app, particularly if we introduce features that change our data practices. Material changes will be communicated through an in-app notification on the next app launch and by updating the \"Last updated\" date above. Continued use of Karigo after any update constitutes acceptance of the revised policy.")
                        )
                    ),
                    LegalSection(
                        number = "16",
                        title = "Contact & Grievance Officer",
                        content = listOf(
                            LegalContentItem.Paragraph("For all privacy enquiries, data deletion requests, or to exercise your rights under applicable data protection law:"),
                            LegalContentItem.ContactBlock(
                                appName = "Karigo (com.karigojobs.app)",
                                developer = "Independent Solo Developer",
                                email = "hazratummar9@gmail.com",
                                grievanceOfficer = "Same contact above",
                                responseTime = "Within 30 days of receipt"
                            )
                        )
                    )
                )
            ),
            "tos" to LegalDocument(
                key = "tos",
                title = "Terms of Service",
                badge = "Document 02 · Terms of Service",
                callout = LegalCallout(
                    text = "Please read these Terms of Service carefully before using Karigo. By downloading, installing, or using the app, you agree to be bound by these terms. If you do not agree, do not use the app.",
                    type = CalloutType.BLUE
                ),
                sections = listOf(
                    LegalSection(
                        number = "01",
                        title = "Acceptance of Terms",
                        content = listOf(
                            LegalContentItem.Paragraph("These Terms of Service (\"Terms\") constitute a legally binding agreement between you (\"User\", \"you\") and the independent developer of Karigo (\"Developer\", \"we\", \"us\"), governing your use of the Karigo mobile application (\"App\", \"Service\")."),
                            LegalContentItem.Paragraph("By downloading, installing, accessing, or using Karigo in any way, you confirm that you have read, understood, and agree to be bound by these Terms in full. If you are using the app on behalf of a business entity, you represent and warrant that you have authority to bind that entity to these Terms."),
                            LegalContentItem.Paragraph("These Terms incorporate our Privacy Policy, Terms & Conditions, and Disclaimer, each of which forms part of this agreement.")
                        )
                    ),
                    LegalSection(
                        number = "02",
                        title = "Description of Service",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is an Android mobile application that enables field workers, tradesmen, contractors, and small business owners to: log job details; track materials used and labour hours; generate professional PDF invoices; manage a client database; and view earnings summaries. The application operates primarily without internet connectivity (\"offline-first\")."),
                            LegalContentItem.Paragraph("Karigo is a software tool only. It does not provide legal, financial, accounting, tax, or professional advice of any kind. Users are solely responsible for the accuracy of all data they enter, all invoices they generate, and all business decisions made using the app.")
                        )
                    ),
                    LegalSection(
                        number = "03",
                        title = "Eligibility",
                        content = listOf(
                            LegalContentItem.Paragraph("You must be at least 18 years of age (or the age of legal majority in your jurisdiction) to use Karigo and enter into this agreement. By using Karigo, you represent and warrant that you meet this age requirement."),
                            LegalContentItem.Paragraph("Karigo is available to users worldwide, subject to applicable laws in your jurisdiction. It is your responsibility to ensure that your use of Karigo complies with all local laws and regulations.")
                        )
                    ),
                    LegalSection(
                        number = "04",
                        title = "User Accounts",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo does not require you to create an account to use the app. There is no registration, no login, and no user profile on our servers. All app data is stored locally on your device."),
                            LegalContentItem.Paragraph("You are responsible for maintaining the physical security of your device and for any use of Karigo on that device. We are not responsible for any loss of data resulting from device theft, loss, damage, or unauthorized access to your device.")
                        )
                    ),
                    LegalSection(
                        number = "05",
                        title = "Subscription & Billing",
                        content = listOf(
                            LegalContentItem.Subheading("5.1 Pro Subscription"),
                            LegalContentItem.Paragraph("Karigo offers a free tier with limited functionality and a paid \"Pro\" subscription that unlocks additional features including unlimited job logging, PDF invoice generation, WhatsApp sharing, and the earnings dashboard."),
                            LegalContentItem.Subheading("5.2 Payment Processing"),
                            LegalContentItem.Paragraph("All payments are processed by Google Play Billing. By subscribing, you agree to Google Play's payment terms in addition to these Terms. Karigo does not receive, store, or process any payment card details, bank account information, or UPI credentials."),
                            LegalContentItem.Subheading("5.3 Subscription Terms"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Pro subscriptions automatically renew at the end of each billing period unless cancelled before the renewal date",
                                    "Your Google Play account will be charged within 24 hours prior to the end of the current billing period",
                                    "Subscription pricing is displayed in the app before purchase and may vary by region",
                                    "Price changes will be communicated at least 30 days in advance with the ability to cancel before the new price takes effect"
                                )
                            ),
                            LegalContentItem.Subheading("5.4 Cancellation"),
                            LegalContentItem.Paragraph("You may cancel your subscription at any time through your Google Play account settings. Cancellation takes effect at the end of the current billing period. You will retain Pro access until the end of the paid period. Cancelling does not delete your locally stored data."),
                            LegalContentItem.Subheading("5.5 Lifetime Purchase"),
                            LegalContentItem.Paragraph("Where a one-time lifetime purchase option is offered, it grants access to Pro features as they exist at the time of purchase and all future updates to those features for the lifetime of the application. \"Lifetime\" means the period during which Karigo remains available on Google Play. We reserve the right to discontinue offering lifetime purchases at any time without affecting existing purchasers."),
                            LegalContentItem.Subheading("5.6 Free Trial"),
                            LegalContentItem.Paragraph("Where a free trial is offered, it will be clearly communicated within the app and Play Store listing. If you do not cancel before the trial period ends, you will be automatically charged for the subscription.")
                        )
                    ),
                    LegalSection(
                        number = "06",
                        title = "Refund Policy",
                        content = listOf(
                            LegalContentItem.Paragraph("All purchases are processed through Google Play. Refund requests are subject to Google Play's refund policy. In general, Google Play allows refund requests within 48 hours of purchase for subscriptions and 2 hours for apps."),
                            LegalContentItem.Paragraph("For purchases outside Google's standard refund window, you may contact us at hazratummar9@gmail.com to discuss your case. We will consider refund requests on a case-by-case basis in the following circumstances:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "You were charged in error due to a technical fault",
                                    "The app failed to function as described and we were unable to resolve the issue",
                                    "You were charged after cancelling your subscription due to a billing system error"
                                )
                            ),
                            LegalContentItem.Paragraph("We do not offer refunds for change of mind, unused subscription periods, or failure to cancel before a renewal date.")
                        )
                    ),
                    LegalSection(
                        number = "07",
                        title = "Free Tier Limitations",
                        content = listOf(
                            LegalContentItem.Paragraph("The free tier of Karigo provides access to a limited set of features, currently including up to 5 job entries per calendar month, unlimited client and material library access, and basic job viewing. Features available in the free tier may change over time at our discretion."),
                            LegalContentItem.Paragraph("We reserve the right to modify, restrict, or discontinue any free tier features at any time with reasonable notice. Free tier users have no entitlement to Pro features.")
                        )
                    ),
                    LegalSection(
                        number = "08",
                        title = "Intellectual Property",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo, including its source code, design, user interface, graphics, logos, and all associated materials, is the intellectual property of the Developer and is protected under applicable copyright, trademark, and intellectual property laws."),
                            LegalContentItem.Paragraph("We grant you a limited, non-exclusive, non-transferable, revocable licence to use the Karigo app on Android devices you own or control, solely for your personal or business use in accordance with these Terms."),
                            LegalContentItem.Paragraph("You may not: copy, modify, distribute, sell, or sublicense any part of the app; reverse-engineer, decompile, or disassemble the app except as permitted by applicable law; remove or alter any proprietary notices; or use the Karigo name or logo without our written permission.")
                        )
                    ),
                    LegalSection(
                        number = "09",
                        title = "Your Content & Data",
                        content = listOf(
                            LegalContentItem.Paragraph("All data you enter into Karigo — client information, job records, invoices, and all related content — belongs to you. We make no claim of ownership over your data."),
                            LegalContentItem.Paragraph("Because all data is stored locally on your device, you are solely responsible for: the accuracy and legality of all data entered; backing up your data; and exporting your data before uninstalling the app or changing devices."),
                            LegalContentItem.Paragraph("You warrant that all data you enter into Karigo does not infringe the intellectual property rights, privacy rights, or any other rights of any third party.")
                        )
                    ),
                    LegalSection(
                        number = "10",
                        title = "Prohibited Uses",
                        content = listOf(
                            LegalContentItem.Paragraph("You agree not to use Karigo:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "For any unlawful purpose or in violation of any applicable laws or regulations",
                                    "To generate fraudulent invoices or to facilitate any form of fraud or financial crime",
                                    "To impersonate any person or entity or misrepresent your affiliation with any person or entity",
                                    "To attempt to reverse engineer, decompile, or extract the source code of the application",
                                    "To bypass, disable, or circumvent any feature limitation or subscription restriction within the app",
                                    "To share your Pro subscription access with other individuals or entities not covered by your subscription",
                                    "In any manner that could damage, disable, overburden, or impair the app",
                                    "In violation of Google Play's Developer Programme Policies"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "11",
                        title = "Disclaimer of Warranties",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is provided on an \"as is\" and \"as available\" basis without warranty of any kind, express or implied, including but not limited to warranties of merchantability, fitness for a particular purpose, accuracy, reliability, or non-infringement."),
                            LegalContentItem.Paragraph("We do not warrant that: the app will be error-free or uninterrupted; defects will be corrected; the app will meet your specific requirements; or data calculated by the app (such as invoice totals or earnings) will be free of errors. You are solely responsible for verifying all figures generated by the app."),
                            LegalContentItem.Paragraph("Nothing in these Terms excludes or limits liability that cannot be excluded under applicable law.")
                        )
                    ),
                    LegalSection(
                        number = "12",
                        title = "Limitation of Liability",
                        content = listOf(
                            LegalContentItem.Paragraph("To the maximum extent permitted by applicable law, in no event shall the Developer be liable for any indirect, incidental, special, consequential, or punitive damages, including but not limited to loss of profits, loss of data, loss of revenue, loss of business, or loss of goodwill, arising out of or in connection with your use of, or inability to use, Karigo."),
                            LegalContentItem.Paragraph("In no event shall the Developer's total aggregate liability to you exceed the amount paid by you for the Karigo subscription in the 12 months immediately preceding the event giving rise to the claim, or $10 USD (ten United States dollars) or equivalent in local currency if no payment was made."),
                            LegalContentItem.Paragraph("Some jurisdictions do not allow the exclusion or limitation of certain types of liability. In such jurisdictions, our liability shall be limited to the maximum extent permitted by law.")
                        )
                    ),
                    LegalSection(
                        number = "13",
                        title = "Indemnification",
                        content = listOf(
                            LegalContentItem.Paragraph("You agree to indemnify, defend, and hold harmless the Developer from and against any claims, liabilities, damages, losses, costs, and expenses (including reasonable legal fees) arising out of or in any way connected with: your use of Karigo; your violation of these Terms; your violation of any applicable law or regulation; or any content or data you enter into the app.")
                        )
                    ),
                    LegalSection(
                        number = "14",
                        title = "Termination",
                        content = listOf(
                            LegalContentItem.Paragraph("We may suspend or terminate your access to Pro features immediately, without prior notice or liability, if you breach these Terms in any material way, including but not limited to attempting to circumvent subscription restrictions or using the app for fraudulent purposes."),
                            LegalContentItem.Paragraph("Upon termination, your right to use Pro features ceases immediately. Your locally stored data remains on your device and is not deleted by us."),
                            LegalContentItem.Paragraph("You may stop using Karigo at any time by uninstalling the app from your device.")
                        )
                    ),
                    LegalSection(
                        number = "15",
                        title = "Governing Law & Dispute Resolution",
                        content = listOf(
                            LegalContentItem.Paragraph("These Terms are governed by and construed in accordance with the laws of India, specifically the Indian Contract Act 1872, the Information Technology Act 2000 and its amendments, the Consumer Protection Act 2019, and the Digital Personal Data Protection Act 2023, without regard to its conflict of law provisions."),
                            LegalContentItem.Paragraph("For users in India: Any dispute arising out of or in connection with these Terms shall be subject to the exclusive jurisdiction of the courts located in West Bengal, India. Before initiating any legal proceedings, both parties agree to attempt to resolve disputes amicably by email within 30 days."),
                            LegalContentItem.Paragraph("For users in the EU/EEA: If you are a consumer in the European Union, you may also have recourse to the EU Online Dispute Resolution platform at ec.europa.eu/consumers/odr."),
                            LegalContentItem.Paragraph("For users in other jurisdictions: You may have additional rights under local consumer protection laws. Nothing in these Terms limits those rights.")
                        )
                    ),
                    LegalSection(
                        number = "16",
                        title = "Severability & Entire Agreement",
                        content = listOf(
                            LegalContentItem.Paragraph("If any provision of these Terms is found to be unenforceable or invalid by a court of competent jurisdiction, that provision shall be limited or eliminated to the minimum extent necessary so that the remaining Terms remain in full force and effect."),
                            LegalContentItem.Paragraph("These Terms, together with the Privacy Policy, Terms & Conditions, and Disclaimer, constitute the entire agreement between you and the Developer regarding your use of Karigo and supersede all prior agreements.")
                        )
                    ),
                    LegalSection(
                        number = "17",
                        title = "Changes to Terms",
                        content = listOf(
                            LegalContentItem.Paragraph("We reserve the right to modify these Terms at any time. Material changes will be communicated via an in-app notification at least 14 days before taking effect. Your continued use of Karigo after any changes constitutes acceptance of the revised Terms. If you do not agree to the revised Terms, you must stop using the app.")
                        )
                    ),
                    LegalSection(
                        number = "18",
                        title = "Contact",
                        content = listOf(
                            LegalContentItem.ContactBlock(
                                appName = "Karigo (com.karigojobs.app)",
                                developer = "Independent Solo Developer",
                                email = "hazratummar9@gmail.com",
                                grievanceOfficer = "Same contact above",
                                responseTime = "Within 30 days of receipt"
                            )
                        )
                    )
                )
            ),
            "toc" to LegalDocument(
                key = "toc",
                title = "Terms & Conditions",
                badge = "Document 03 · Terms & Conditions",
                callout = LegalCallout(
                    text = "These Terms & Conditions govern the specific conditions of use of the Karigo application, including the Pro subscription plan, feature access, data ownership, and acceptable conduct. They complement the Terms of Service and are incorporated into it by reference.",
                    type = CalloutType.BLUE
                ),
                sections = listOf(
                    LegalSection(
                        number = "01",
                        title = "Agreement to These Terms & Conditions",
                        content = listOf(
                            LegalContentItem.Paragraph("By using Karigo, you confirm that you have read and agree to these Terms & Conditions. These T&Cs form part of the overall legal agreement between you and the Developer, alongside the Terms of Service, Privacy Policy, and Disclaimer."),
                            LegalContentItem.Paragraph("If you are a paying subscriber (Pro plan), these T&Cs additionally govern your subscription arrangement. The Consumer Protection Act 2019 (India) and equivalent consumer protection laws in your jurisdiction apply to your purchase.")
                        )
                    ),
                    LegalSection(
                        number = "02",
                        title = "App Features & Limitations",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is provided with the following understood limitations that form part of your agreement to use the app:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "No cloud backup: Your data is stored only on your device. The Developer has no ability to back up, restore, or recover your data if it is lost",
                                    "No multi-device sync: The current version of Karigo does not synchronise data across multiple devices",
                                    "No real-time collaboration: Karigo is a single-user, single-device application",
                                    "PDF generation accuracy: PDF invoices are generated based on data you enter. The Developer is not responsible for errors in invoices resulting from incorrect data entry",
                                    "WhatsApp sharing: The WhatsApp share feature uses Android's system intent and requires WhatsApp to be installed on your device. We cannot guarantee WhatsApp compatibility with future versions",
                                    "Android compatibility: Karigo requires Android 6.0 (API 23) or higher. Functionality on older Android versions is not guaranteed"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "03",
                        title = "Pro Plan Conditions",
                        content = listOf(
                            LegalContentItem.Paragraph("The Pro plan grants access to the following features (as currently offered; subject to change with notice):"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Unlimited job entries per month (no monthly cap)",
                                    "PDF invoice generation using your business name, logo, and itemised details",
                                    "WhatsApp invoice sharing via Android system share intent",
                                    "Monthly earnings dashboard with bar chart visualisation",
                                    "Business logo inclusion on invoices",
                                    "CSV data export from Settings",
                                    "Invoice discount field",
                                    "All future Pro features added to Karigo during your active subscription period"
                                )
                            ),
                            LegalContentItem.Subheading("Pro plan is per-user and non-transferable"),
                            LegalContentItem.Paragraph("Your Pro subscription is licensed to a single user account linked to your Google Play account. Sharing of subscription access with other users is prohibited."),
                            LegalContentItem.Subheading("Lifetime plan (if purchased)"),
                            LegalContentItem.Paragraph("A lifetime Pro purchase grants permanent access to Pro features on the device linked to the purchasing Google account. \"Permanent\" is conditional on Karigo remaining an active, published application. If Karigo is discontinued, lifetime purchasers will receive at least 90 days' advance notice.")
                        )
                    ),
                    LegalSection(
                        number = "04",
                        title = "Subscription Pricing & Fairness",
                        content = listOf(
                            LegalContentItem.Paragraph("We are committed to fair pricing practices. The following commitments apply to all paying users:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Price increase notice: We will provide at least 30 days' advance notice of any subscription price increase, with the ability to cancel before the increase takes effect",
                                    "Early adopter protection: Users who subscribe during a promotional early-adopter pricing period will be honoured at the promotional rate for as long as they maintain a continuous subscription",
                                    "Lifetime purchasers: Users who purchase a lifetime plan will never be charged recurring fees for the features included at the time of their purchase",
                                    "Feature removal: If a feature you relied upon is removed from the Pro plan, you will be notified at least 14 days in advance and offered a pro-rated refund for the remaining subscription period",
                                    "No hidden fees: The price displayed in Google Play is the full price. There are no in-app purchases, additional feature unlocks, or hidden charges beyond the subscription fee"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "05",
                        title = "Offline Operation Conditions",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is designed to function without internet connectivity for all core features. However, the following conditions apply:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Subscription status while offline: RevenueCat caches your subscription status locally. Pro features remain accessible offline for up to 5 days without internet connectivity. After 5 days without network access, the app may require a connectivity check to re-verify your subscription",
                                    "Purchase restoration: Restoring purchases after a reinstall requires an active internet connection to contact RevenueCat and Google Play servers",
                                    "New subscriptions: Purchasing a new subscription requires an active internet connection at the time of purchase",
                                    "App updates: Receiving app updates through Google Play requires internet connectivity"
                                )
                            ),
                            LegalContentItem.Paragraph("We do not guarantee that all features will be available under all network conditions or on all device configurations.")
                        )
                    ),
                    LegalSection(
                        number = "06",
                        title = "Invoice Accuracy & Legal Compliance",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo generates invoices based on the information you provide. The following conditions and responsibilities apply to all users generating invoices through the app:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "You are solely responsible for ensuring that all prices, quantities, client details, and business information entered into Karigo are accurate and truthful",
                                    "Karigo does not validate invoice amounts, tax calculations, or compliance with any tax authority requirements",
                                    "If you are GST-registered, you are responsible for ensuring your invoices comply with GST invoicing requirements under the CGST Act 2017 or applicable state GST laws. Karigo's invoice template may not be GST-compliant without customisation",
                                    "Karigo is not a registered accounting software and invoices generated by Karigo may not substitute for legally required financial records in all jurisdictions",
                                    "You must not use Karigo to generate false, fraudulent, or misleading invoices",
                                    "The Developer is not liable for any tax liability, regulatory penalty, or legal consequence arising from invoices generated using Karigo"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "07",
                        title = "Data Ownership & Portability",
                        content = listOf(
                            LegalContentItem.Paragraph("All data you enter into Karigo — including client information, job records, materials, and invoice data — is exclusively owned by you. The Developer makes no claim of any kind over your business data."),
                            LegalContentItem.Paragraph("You have the right to export your data at any time using the CSV export function in Settings. You do not need to contact us to export your data. Exported data is provided in CSV format which can be opened in any spreadsheet application."),
                            LegalContentItem.Paragraph("You may delete your data at any time by clearing the app's data in Android Settings or by uninstalling the app. We have no copy of your data and cannot restore it after deletion.")
                        )
                    ),
                    LegalSection(
                        number = "08",
                        title = "User Responsibilities",
                        content = listOf(
                            LegalContentItem.Paragraph("As a user of Karigo, you agree that you are solely responsible for:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "All data entered into the app, including its accuracy and legality",
                                    "Ensuring clients' phone numbers and contact details you store in the app are lawfully obtained and that storing them complies with applicable data protection laws (including India's DPDPA 2023)",
                                    "Maintaining regular backups or CSV exports of your data",
                                    "Keeping your Android device secure and updated",
                                    "Compliance with all applicable tax laws, invoicing regulations, and business licensing requirements in your jurisdiction",
                                    "Ensuring that invoices generated through Karigo comply with the legal and tax requirements applicable to your business"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "09",
                        title = "Updates & Feature Changes",
                        content = listOf(
                            LegalContentItem.Paragraph("We may release updates to Karigo that add, modify, or remove features. We will aim to communicate significant changes through in-app notifications and Play Store update notes."),
                            LegalContentItem.Paragraph("Updates are delivered through Google Play. We recommend enabling automatic updates for Karigo to ensure you have the latest bug fixes and security improvements."),
                            LegalContentItem.Paragraph("Core features of the Pro plan will not be removed without at least 14 days' prior notice. We reserve the right to modify the free tier at any time, including reducing or expanding the features available for free.")
                        )
                    ),
                    LegalSection(
                        number = "10",
                        title = "App Availability",
                        content = listOf(
                            LegalContentItem.Paragraph("We aim to keep Karigo available on Google Play indefinitely. However, we reserve the right to discontinue the application with at least 90 days' prior notice to users. In such an event:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Pro subscribers will receive pro-rated refunds for any unused subscription period",
                                    "Lifetime purchasers will be notified at least 90 days in advance",
                                    "All users will retain access to their locally stored data until they uninstall the app",
                                    "CSV export functionality will remain available for at least 90 days after discontinuation notice"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "11",
                        title = "Feedback & Suggestions",
                        content = listOf(
                            LegalContentItem.Paragraph("We welcome feedback, feature suggestions, and bug reports. Any feedback you provide to us may be used by us to improve Karigo without any obligation to compensate you or to implement the suggestion."),
                            LegalContentItem.Paragraph("By submitting feedback, you grant us a non-exclusive, royalty-free, perpetual, irrevocable licence to use, copy, modify, and incorporate the feedback into Karigo or related products without attribution or compensation.")
                        )
                    ),
                    LegalSection(
                        number = "12",
                        title = "Third-Party Integrations",
                        content = listOf(
                            LegalContentItem.Paragraph("WhatsApp: Karigo's \"Share via WhatsApp\" feature uses Android's standard system share intent. WhatsApp is a third-party application owned by Meta Platforms, Inc. We have no partnership or affiliation with WhatsApp/Meta. Use of WhatsApp is subject to WhatsApp's own Terms of Service and Privacy Policy."),
                            LegalContentItem.Paragraph("Google Play: Karigo is distributed through Google Play, owned by Google LLC. Use of Google Play is subject to Google's Terms of Service."),
                            LegalContentItem.Paragraph("RevenueCat: Subscription management is handled by RevenueCat, Inc. Use of RevenueCat services is subject to their Terms of Service. We are responsible for our use of RevenueCat's services but not for RevenueCat's own conduct."),
                            LegalContentItem.Paragraph("We are not responsible for the availability, accuracy, or conduct of any third-party services used in connection with Karigo.")
                        )
                    ),
                    LegalSection(
                        number = "13",
                        title = "Consumer Rights (India)",
                        content = listOf(
                            LegalContentItem.Paragraph("If you are a consumer in India, you have additional rights under the Consumer Protection Act, 2019 and the Consumer Protection (E-Commerce) Rules, 2020, including:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "The right to a refund for defective products or services not matching their description",
                                    "The right to file a consumer complaint with the District Consumer Disputes Redressal Commission",
                                    "The right to approach the National Consumer Helpline (NCH) for grievance resolution"
                                )
                            ),
                            LegalContentItem.Paragraph("Our Grievance Officer for the purposes of Indian consumer law is reachable at hazratummar9@gmail.com. We will acknowledge grievances within 48 hours and endeavour to resolve them within 30 days.")
                        )
                    ),
                    LegalSection(
                        number = "14",
                        title = "Consumer Rights (EU/EEA)",
                        content = listOf(
                            LegalContentItem.Paragraph("If you are a consumer in the European Economic Area:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "You have a 14-day right of withdrawal from the date of purchase of a subscription under the EU Consumer Rights Directive, unless digital content delivery has commenced with your prior express consent",
                                    "You have the right to receive the service in conformity with the contract",
                                    "You have access to the EU Online Dispute Resolution platform at ec.europa.eu/consumers/odr"
                                )
                            )
                        )
                    ),
                    LegalSection(
                        number = "15",
                        title = "Contact",
                        content = listOf(
                            LegalContentItem.ContactBlock(
                                appName = "Karigo (com.karigojobs.app)",
                                developer = "Independent Solo Developer",
                                email = "hazratummar9@gmail.com",
                                grievanceOfficer = "Same contact above",
                                responseTime = "Within 48 hours acknowledgement; 30 days resolution"
                            )
                        )
                    )
                )
            ),
            "disclaimer" to LegalDocument(
                key = "disclaimer",
                title = "Disclaimer",
                badge = "Document 04 · Disclaimer",
                callout = LegalCallout(
                    text = "This Disclaimer limits our liability for various aspects of Karigo's use. Please read it carefully. It is incorporated into the Terms of Service by reference and forms part of the overall legal agreement governing your use of the app.",
                    type = CalloutType.AMBER
                ),
                sections = listOf(
                    LegalSection(
                        number = "01",
                        title = "As-Is Software Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is provided on an \"as is\" and \"as available\" basis without any representations or warranties, express or implied. The Developer makes no representations or warranties in relation to the app or the information and materials provided on the app."),
                            LegalContentItem.Paragraph("Without prejudice to the generality of the foregoing, the Developer does not warrant or represent that:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Karigo will be constantly available or available at all times",
                                    "The information in the app is complete, true, accurate, or non-misleading",
                                    "The app will be free of bugs, errors, or technical defects at all times",
                                    "The app will function uninterrupted on any particular Android device or Android version",
                                    "Any specific feature will remain available in future updates"
                                )
                            ),
                            LegalContentItem.Paragraph("Nothing on or in Karigo constitutes, or is meant to constitute, advice of any kind.")
                        )
                    ),
                    LegalSection(
                        number = "02",
                        title = "No Professional Advice",
                        content = listOf(
                            LegalContentItem.Callout(
                                text = "Karigo does not provide legal, financial, accounting, tax, or business advice of any kind.\n\nThe Developer is a software developer, not a chartered accountant, tax advisor, lawyer, or financial advisor. Nothing in the app, its documentation, or any communication from us should be interpreted as professional advice.",
                                type = CalloutType.RED
                            ),
                            LegalContentItem.Paragraph("Specifically:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Not accounting software: Karigo is not a certified accounting system. It is a job logging and invoicing tool. It should not be used as your sole financial record-keeping system",
                                    "Not tax advice: Karigo does not provide tax calculations, GST advice, or any guidance on your tax obligations. You are responsible for understanding and complying with your tax obligations",
                                    "Not legal advice: Invoices generated by Karigo are not guaranteed to be legally compliant in your jurisdiction. You should consult a qualified professional to ensure your invoicing practices comply with applicable laws",
                                    "Not financial advice: Earnings data displayed in Karigo is based solely on figures you have entered. It does not account for actual bank receipts, taxes, expenses, or other financial factors"
                                )
                            ),
                            LegalContentItem.Paragraph("Always seek the advice of a qualified and licensed professional for any legal, financial, tax, or accounting matter. Do not rely on information derived from Karigo as a substitute for professional advice.")
                        )
                    ),
                    LegalSection(
                        number = "03",
                        title = "Invoice & Financial Accuracy Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo calculates invoice totals based on the data you enter, including labour hours, labour rates, material quantities, material rates, and any discounts applied. The accuracy of all calculations is entirely dependent on the accuracy of the data you provide."),
                            LegalContentItem.Paragraph("The Developer disclaims all liability for:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Calculation errors resulting from incorrect data entry",
                                    "Disputes between you and your clients arising from invoices generated using Karigo",
                                    "Financial losses resulting from reliance on figures displayed in the app",
                                    "Rounding errors in currency calculations",
                                    "Incorrect earnings totals resulting from incomplete or inaccurate job records"
                                )
                            ),
                            LegalContentItem.Paragraph("You are solely responsible for verifying all invoice amounts before sending them to clients. The PDF invoice you generate is a record of the data you entered — it is not independently verified by us.")
                        )
                    ),
                    LegalSection(
                        number = "04",
                        title = "Tax & Legal Compliance Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo's invoice template is a general-purpose document. It is not guaranteed to comply with any specific legal invoicing requirements, including but not limited to:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "GST-compliant tax invoices under the CGST Act 2017 or applicable state GST laws (India)",
                                    "VAT invoices in EU member states",
                                    "Tax invoice requirements in any other jurisdiction"
                                )
                            ),
                            LegalContentItem.Paragraph("If your business is GST-registered or subject to any statutory invoicing requirements, you must consult a qualified tax professional to determine whether invoices generated by Karigo are legally sufficient. The Developer accepts no liability for any tax penalties, fines, or legal consequences arising from the use of Karigo's invoice generation feature.")
                        )
                    ),
                    LegalSection(
                        number = "05",
                        title = "Data Loss Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Because all data in Karigo is stored exclusively on your device, the Developer has no ability to recover lost data under any circumstances. Data may be permanently lost due to:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Accidental deletion of the app or clearing of app data",
                                    "Device theft, loss, damage, or failure",
                                    "Factory reset of your Android device",
                                    "Android system updates that affect app data",
                                    "Migration to a new device without first exporting data",
                                    "Technical faults or bugs in the app"
                                )
                            ),
                            LegalContentItem.Paragraph("The Developer expressly disclaims all liability for any data loss, regardless of the cause. You assume full responsibility for backing up your data through the CSV export feature in Settings or through Android's device backup functionality.")
                        )
                    ),
                    LegalSection(
                        number = "06",
                        title = "Third-Party Services Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo integrates with Google Play Billing and RevenueCat for subscription management. The Developer is not responsible for:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Downtime, errors, or failures of Google Play Billing or RevenueCat systems",
                                    "Subscription billing errors caused by Google Play or RevenueCat",
                                    "Failure of WhatsApp to receive or display shared invoice PDFs",
                                    "Changes to Google Play's or RevenueCat's policies, pricing, or features that affect Karigo's subscription functionality",
                                    "Data handling by Google Play, RevenueCat, or WhatsApp under their respective privacy policies"
                                )
                            ),
                            LegalContentItem.Paragraph("If you experience billing issues, we encourage you to contact Google Play support directly for resolution. We will assist where we can but are not responsible for third-party service failures.")
                        )
                    ),
                    LegalSection(
                        number = "07",
                        title = "External Links Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo may contain links to third-party websites or services (such as Google Play, RevenueCat, or WhatsApp). These links are provided for your convenience only. The inclusion of any link does not imply our endorsement of the linked website or service. We have no control over the content, privacy practices, or reliability of linked third-party resources."),
                            LegalContentItem.Paragraph("We accept no responsibility for any loss or damage that may arise from your use of any linked third-party website or service.")
                        )
                    ),
                    LegalSection(
                        number = "08",
                        title = "Device Compatibility Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("Karigo is designed for Android devices running Android 6.0 (API 23) or higher. While we aim to maintain compatibility with a wide range of Android devices, we do not guarantee that the app will function correctly on all devices, all Android versions, all custom Android skins (such as MIUI, One UI, ColorOS, etc.), or devices with restricted permissions due to manufacturer customisation."),
                            LegalContentItem.Paragraph("The Developer is not responsible for:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "App malfunction due to device-specific hardware or software configurations",
                                    "WorkManager notification failures due to manufacturer-specific battery optimisation",
                                    "PDF rendering differences across different Android versions",
                                    "Features that require permissions that have been denied by your device's permission management system"
                                )
                            ),
                            LegalContentItem.Paragraph("If you experience device-specific issues, please contact us at hazratummar9@gmail.com with your device model and Android version and we will endeavour to assist.")
                        )
                    ),
                    LegalSection(
                        number = "09",
                        title = "App Availability Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("The Developer makes no commitment that Karigo will remain available on Google Play indefinitely or that it will continue to be updated or maintained. While we intend to continue developing and supporting Karigo, we reserve the right to:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Cease development and updates at any time",
                                    "Remove the app from Google Play with reasonable notice",
                                    "Change the app's features, pricing, or terms at any time with appropriate notice"
                                )
                            ),
                            LegalContentItem.Paragraph("Because Karigo is offline-first and your data is stored locally, discontinuation of the app does not affect your existing data on your device.")
                        )
                    ),
                    LegalSection(
                        number = "10",
                        title = "No Endorsement Disclaimer",
                        content = listOf(
                            LegalContentItem.Paragraph("References in the app or its documentation to any trade names, products, services, processes, or other information by trade name, trademark, manufacturer, supplier, or otherwise do not constitute or imply endorsement, sponsorship, or recommendation by us. Karigo is an independent product and is not affiliated with, endorsed by, or sponsored by Google, Meta (WhatsApp), or any other company whose services may be mentioned in the app."),
                            LegalContentItem.Paragraph("\"Karigo\" and the Karigo logo are trademarks of the Developer. All other trademarks mentioned in connection with the app belong to their respective owners.")
                        )
                    ),
                    LegalSection(
                        number = "11",
                        title = "Limitation of Liability — Recap",
                        content = listOf(
                            LegalContentItem.Paragraph("To the fullest extent permitted by applicable law, the Developer shall not be liable for any loss or damage of any nature, including but not limited to:"),
                            LegalContentItem.BulletList(
                                listOf(
                                    "Loss of data, business, revenue, profits, or goodwill",
                                    "Any indirect, consequential, special, punitive, or incidental damage",
                                    "Losses caused by the actions of any third-party service (Google Play, RevenueCat, WhatsApp)",
                                    "Losses arising from your failure to back up data",
                                    "Losses arising from reliance on invoice or financial data generated by the app",
                                    "Losses arising from your failure to comply with tax or legal requirements in your jurisdiction",
                                    "Losses arising from device failure, theft, or loss"
                                )
                            ),
                            LegalContentItem.Paragraph("The Developer's aggregate maximum liability to you under all circumstances shall not exceed the amount you have paid for Karigo Pro in the 12 months preceding the event giving rise to the claim, or $10 USD (ten United States dollars) or equivalent in local currency, whichever is greater."),
                            LegalContentItem.Paragraph("Some jurisdictions do not permit the exclusion of implied warranties or limitation of liability. Where such restrictions apply, the Developer's liability is limited to the maximum extent permitted by law in that jurisdiction.")
                        )
                    ),
                    LegalSection(
                        number = "12",
                        title = "Jurisdiction & Applicability",
                        content = listOf(
                            LegalContentItem.Paragraph("This Disclaimer is governed by the laws of India. It applies to all users of Karigo globally. Users in specific jurisdictions may have additional rights under local consumer protection or data protection law that supplement or, where required by law, override the limitations expressed in this Disclaimer."),
                            LegalContentItem.Paragraph("Nothing in this Disclaimer is intended to exclude rights that cannot be lawfully excluded under applicable consumer protection legislation in your jurisdiction.")
                        )
                    ),
                    LegalSection(
                        number = "13",
                        title = "Contact",
                        content = listOf(
                            LegalContentItem.ContactBlock(
                                appName = "Karigo (com.karigojobs.app)",
                                developer = "Independent Solo Developer",
                                email = "hazratummar9@gmail.com",
                                grievanceOfficer = "Same contact above",
                                responseTime = "Within 30 days"
                            )
                        )
                    )
                )
            )
        )
    }
}
