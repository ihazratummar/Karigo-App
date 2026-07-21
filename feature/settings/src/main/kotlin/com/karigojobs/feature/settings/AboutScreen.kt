package com.karigojobs.feature.settings

import org.jetbrains.compose.resources.stringResource
import karigojobs.shared.resources.generated.resources.*

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
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
    val versionText = stringResource(Res.string.about_version, versionName, versionCode.toInt())

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = stringResource(Res.string.about_title),
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
                            .background(appColor.accentBg, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.electrician),
                            contentDescription = stringResource(Res.string.about_logo_description),
                            tint = KarigojobsIconColor,
                            modifier = Modifier.size(dimens.Icon._4xl)
                        )
                    }

                    Text(
                        text = "Karigo",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = stringResource(Res.string.about_tagline),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = KarigojobsText2,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = dimens.Padding.md)
                    )

                    // Version Badge
                    Surface(
                        color = appColor.primaryText.copy(alpha = 0.05f),
                        shape = CircleShape,
                        border = customCardBorder()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                            modifier = Modifier.padding(
                                horizontal = dimens.Padding.sm,
                                vertical = dimens.Padding._2xs
                            )
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
                    colors = CardDefaults.cardColors(containerColor = appColor.cardColors)
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
                                contentDescription = stringResource(Res.string.about_section_what_does),
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = stringResource(Res.string.about_section_what_does),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = appColor.primaryText,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = stringResource(Res.string.about_what_does_desc),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = appColor.secondaryText,
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
                    colors = CardDefaults.cardColors(containerColor = appColor.cardColors)
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
                                contentDescription = stringResource(Res.string.about_section_features),
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = stringResource(Res.string.about_section_features),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = appColor.primaryText,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                        ) {
                            FeatureRow(
                                iconRes = R.drawable.job_line,
                                title = stringResource(Res.string.about_feature_job_title),
                                description = stringResource(Res.string.about_feature_job_desc)
                            )
                            FeatureRow(
                                iconRes = R.drawable.user_line,
                                title = stringResource(Res.string.about_feature_client_title),
                                description = stringResource(Res.string.about_feature_client_desc)
                            )
                            FeatureRow(
                                iconRes = R.drawable.stack,
                                title = stringResource(Res.string.about_feature_material_title),
                                description = stringResource(Res.string.about_feature_material_desc)
                            )
                            FeatureRow(
                                iconRes = R.drawable.estimate,
                                title = stringResource(Res.string.about_feature_estimate_title),
                                description = stringResource(Res.string.about_feature_estimate_desc)
                            )
                            FeatureRow(
                                iconRes = R.drawable.chart_line,
                                title = stringResource(Res.string.about_feature_earning_title),
                                description = stringResource(Res.string.about_feature_earning_desc)
                            )
                            FeatureRow(
                                iconRes = R.drawable.stack,
                                title = stringResource(Res.string.settings_option_backup),
                                description = stringResource(Res.string.about_feature_backup_desc)
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
                    colors = CardDefaults.cardColors(containerColor = appColor.cardColors)
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
                                contentDescription = stringResource(Res.string.about_section_who_for),
                                tint = KarigojobsIconColor,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                            Text(
                                text = stringResource(Res.string.about_section_who_for),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = appColor.primaryText,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = stringResource(Res.string.about_who_for_desc),
                            style = MaterialTheme.typography.bodyMedium.copy(color = appColor.secondaryText)
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                            verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                        ) {
                            TradeType.entries.forEach { trade ->
                                key(trade.name) {
                                    Surface(
                                        color = appColor.primaryText.copy(alpha = 0.05f),
                                        shape = KarigojobsShapes.small,
                                        border = customCardBorder()
                                    ) {
                                        Text(
                                            text = stringResource(trade.displayNameRes),
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = appColor.secondaryText,
                                                fontWeight = FontWeight.Medium
                                            ),
                                            modifier = Modifier.padding(
                                                horizontal = dimens.Padding.sm,
                                                vertical = dimens.Padding.xs
                                            )
                                        )
                                    }
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
                    border = BorderStroke(
                        dimens.Border.thin,
                        KarigojobsIconColor.copy(alpha = 0.4f)
                    ),
                    colors = CardDefaults.cardColors(containerColor = appColor.cardColors)
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
                            contentDescription = stringResource(Res.string.about_section_made_pride),
                            tint = KarigojobsIconColor,
                            modifier = Modifier.size(dimens.Icon.base)
                        )

                        Text(
                            text = stringResource(Res.string.about_section_made_pride),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = stringResource(Res.string.about_made_pride_desc),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = appColor.secondaryText,
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
                    text = stringResource(Res.string.about_copyright, versionName),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = appColor.tertiaryText,
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
            color = appColor.primaryText.copy(alpha = 0.05f),
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
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
            )
        }
    }
}
