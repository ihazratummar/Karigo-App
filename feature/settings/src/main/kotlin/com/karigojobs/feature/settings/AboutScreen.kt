package com.karigojobs.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.pm.PackageInfoCompat
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.*

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val packageInfo = remember(context) {
        runCatching { context.packageManager.getPackageInfo(context.packageName, 0) }.getOrNull()
    }
    val versionName = packageInfo?.versionName ?: "0.11.0"
    val versionCode = packageInfo?.let { PackageInfoCompat.getLongVersionCode(it) } ?: 1L
    val versionText = "Version $versionName ($versionCode)"

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = "About Karigo",
                isNavBack = true,
                onNavigationClick = onBack,
                isDivider = false
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .contentHorizontalPadding()
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.lg),
            contentPadding = PaddingValues(bottom = dimens.Padding.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Header Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimens.Padding.lg),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon._7xl)
                            .background(Color(0xFF13221E), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.electrician),
                            contentDescription = "Karigo Logo",
                            tint = KarigojobsIconColor,
                            modifier = Modifier.size(dimens.Icon._4xl)
                        )
                    }

                    Text(
                        text = "Karigo",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "The all-in-one business companion for contractors, service professionals, and tradespeople.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = KarigojobsText2,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = dimens.Padding.md)
                    )

                    // Version Badge
                    Surface(
                        color = Color.White.copy(alpha = 0.05f),
                        shape = CircleShape,
                        border = customCardBorder()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                            modifier = Modifier.padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding._2xs)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(dimens.Space.sm)
                                    .background(Color(0xFF10B981), shape = CircleShape)
                            )
                            Text(
                                text = versionText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = KarigojobsText2,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Card 1: What Karigo Does
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigojobsShapes.large,
                    border = customCardBorder(),
                    colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
                ) {
                    Column(
                        modifier = Modifier.padding(dimens.Padding.base),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.electrician),
                                contentDescription = "What Karigo Does Icon",
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = "What Karigo Does",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = "Karigo helps you run your contracting or service business from your phone — no paperwork, no spreadsheets. Manage jobs, track clients, build material price lists, create professional site estimates, monitor earnings, and share everything via WhatsApp. Built specifically for the way tradespeople work.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = KarigojobsText2,
                                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.25f
                            )
                        )
                    }
                }
            }

            // Card 2: Key Features
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigojobsShapes.large,
                    border = customCardBorder(),
                    colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
                ) {
                    Column(
                        modifier = Modifier.padding(dimens.Padding.base),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.alert),
                                contentDescription = "Key Features Icon",
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = "Key Features",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                        ) {
                            FeatureRow(
                                iconRes = R.drawable.job_line,
                                title = "Job Management",
                                description = "Track jobs from start to payment"
                            )
                            FeatureRow(
                                iconRes = R.drawable.user_line,
                                title = "Client Records",
                                description = "Keep client details and history"
                            )
                            FeatureRow(
                                iconRes = R.drawable.stack,
                                title = "Material Library",
                                description = "Build your price list once, reuse forever"
                            )
                            FeatureRow(
                                iconRes = R.drawable.estimate,
                                title = "Site Estimates",
                                description = "Create estimates on-site, share via WhatsApp"
                            )
                            FeatureRow(
                                iconRes = R.drawable.chart_line,
                                title = "Earnings Tracking",
                                description = "See your revenue at a glance"
                            )
                            FeatureRow(
                                iconRes = R.drawable.stack,
                                title = "Data Backup",
                                description = "Back up to Google Drive, restore anytime"
                            )
                        }
                    }
                }
            }

            // Card 3: Who Is It For
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigojobsShapes.large,
                    border = customCardBorder(),
                    colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
                ) {
                    Column(
                        modifier = Modifier.padding(dimens.Padding.base),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.user_action),
                                contentDescription = "Who Is It For Icon",
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = "Who Is It For",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = "Karigo is built for the hardworking professionals who build, fix, and maintain our world:",
                            style = MaterialTheme.typography.bodyMedium.copy(color = KarigojobsText2)
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                        ) {
                            TradeType.entries.forEach { trade ->
                                Surface(
                                    color = Color.White.copy(alpha = 0.05f),
                                    shape = KarigojobsShapes.small,
                                    border = customCardBorder()
                                ) {
                                    Text(
                                        text = trade.displayName,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = KarigojobsText2,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        modifier = Modifier.padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding.xs)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Card 4: Made with Pride callout
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = KarigojobsShapes.large,
                    border = androidx.compose.foundation.BorderStroke(dimens.Border.thin, KarigojobsIconColor.copy(alpha = 0.4f)),
                    colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.Padding.base),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.love), // Similar icon for pride/heart
                            contentDescription = "Made with Pride Icon",
                            tint = KarigojobsIconColor,
                            modifier = Modifier.size(dimens.Icon.base)
                        )

                        Text(
                            text = "Made with pride",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Built for the way contractors work — with multi-currency pricing, WhatsApp sharing, tax invoice support, and an interface simple enough for anyone to use.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = KarigojobsText2,
                                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Footer Section
            item {
                Text(
                    text = "Karigo v$versionName · Made for the world · All rights reserved",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = KarigojobsText3,
                        fontWeight = FontWeight.Medium
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimens.Padding.lg)
                )
            }
        }
    }
}

@Composable
private fun FeatureRow(
    iconRes: Int,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            color = Color.White.copy(alpha = 0.05f),
            shape = KarigojobsShapes.small,
            border = customCardBorder(),
            modifier = Modifier.size(dimens.Icon.lg)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "$title Icon",
                    tint = KarigojobsIconColor,
                    modifier = Modifier.size(dimens.Icon.xs)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
            )
        }
    }
}
