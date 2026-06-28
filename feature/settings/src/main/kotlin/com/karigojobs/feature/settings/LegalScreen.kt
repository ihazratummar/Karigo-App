package com.karigojobs.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.*

@Composable
fun LegalScreen(
    pageKey: String,
    onBack: () -> Unit
) {
    val document = LegalPages.documents[pageKey]

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = document?.title ?: "Legal Page",
                isNavBack = true,
                onNavigationClick = onBack,
                isDivider = false
            )
        }
    ) { paddingValues ->
        if (document == null) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Document not found",
                    style = MaterialTheme.typography.bodyMedium.copy(color = KarigojobsText2)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .contentHorizontalPadding()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.base),
                contentPadding = PaddingValues(bottom = dimens.Padding.xl)
            ) {
                // Header
                item {
                    DocumentHeader(document)
                }

                // Global Document Callout (if any)
                document.callout?.let { callout ->
                    item {
                        CalloutBox(text = callout.text, type = callout.type)
                    }
                }

                // Sections
                items(document.sections) { section ->
                    SectionItem(section = section)
                }

                // Footer
                item {
                    HorizontalDivider(
                        color = Color(0xFF2E2E2E),
                        thickness = dimens.Divider.thickness,
                        modifier = Modifier.padding(vertical = dimens.Padding.lg)
                    )
                    Text(
                        text = "${document.title} • Karigo • Effective June 30, 2026",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KarigojobsText3,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimens.Padding.lg)
                    )
                }
            }
        }
    }
}

@Composable
private fun DocumentHeader(document: LegalDocument) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.Padding.md),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
    ) {
        Surface(
            color = Color.White.copy(alpha = 0.05f),
            shape = KarigojobsShapes.small,
            border = customCardBorder()
        ) {
            Text(
                text = document.badge,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = KarigojobsText2,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding._2xs)
            )
        }

        Text(
            text = document.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = "App: Karigo",
                style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText3)
            )
            Text(
                text = "Effective: June 30, 2026",
                style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText3)
            )
        }
    }
}

@Composable
private fun SectionItem(section: LegalSection) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.Padding.sm),
        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.05f), shape = KarigojobsShapes.small)
                    .border(dimens.Border.thin, Color(0xFF2E2E2E), KarigojobsShapes.small)
                    .padding(horizontal = dimens.Padding.xs, vertical = dimens.Padding._2xs),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = section.number,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = KarigojobsText2,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Text(
                text = section.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        section.content.forEach { item ->
            when (item) {
                is LegalContentItem.Paragraph -> {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = KarigojobsText2,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.25f
                        )
                    )
                }
                is LegalContentItem.BulletList -> {
                    BulletListLayout(items = item.items)
                }
                is LegalContentItem.Table -> {
                    TableLayout(headers = item.headers, rows = item.rows)
                }
                is LegalContentItem.Callout -> {
                    CalloutBox(text = item.text, type = item.type)
                }
                is LegalContentItem.Subheading -> {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = dimens.Padding.xs)
                    )
                }
                is LegalContentItem.ContactBlock -> {
                    ContactBlockLayout(item)
                }
            }
        }
    }
}

@Composable
private fun BulletListLayout(items: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs),
        modifier = Modifier.padding(start = dimens.Padding.md, bottom = dimens.Padding.xs)
    ) {
        items.forEach { bulletText ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = KarigojobsIconColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = bulletText,
                    style = MaterialTheme.typography.bodyMedium.copy(color = KarigojobsText2)
                )
            }
        }
    }
}

@Composable
private fun TableLayout(
    headers: List<String>,
    rows: List<List<TableCell>>
) {
    val columnCount = headers.size
    val weights = when (columnCount) {
        3 -> listOf(1.2f, 1.8f, 1f)
        else -> List(columnCount) { 1f }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.Padding.xs),
        shape = KarigojobsShapes.medium,
        border = customCardBorder(),
        colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(dimens.Padding.sm),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                headers.forEachIndexed { index, headerTitle ->
                    val weight = weights.getOrElse(index) { 1f }
                    Text(
                        text = headerTitle.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText3,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(weight)
                    )
                }
            }

            // Data Rows
            rows.forEachIndexed { rowIndex, rowCells ->
                if (rowIndex > 0) {
                    HorizontalDivider(color = Color(0xFF2E2E2E), thickness = dimens.Divider.thickness)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.Padding.sm),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                    verticalAlignment = Alignment.Top
                ) {
                    rowCells.forEachIndexed { cellIndex, cell ->
                        val weight = weights.getOrElse(cellIndex) { 1f }
                        Box(
                            modifier = Modifier.weight(weight),
                            contentAlignment = Alignment.TopStart
                        ) {
                            when (cell) {
                                is TableCell.Text -> {
                                    Text(
                                        text = cell.text,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = KarigojobsText2,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                is TableCell.Badge -> {
                                    TableCellBadge(cell)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableCellBadge(cell: TableCell.Badge) {
    val badgeColors = when (cell.type) {
        BadgeType.NO -> Pair(Color(0xFF13221E), Color(0xFF00E6C3))
        BadgeType.YES -> Pair(Color(0xFF221F13), Color(0xFFFFC107))
        BadgeType.OPT -> Pair(Color(0xFF131922), Color(0xFF2196F3))
    }

    Surface(
        color = badgeColors.first,
        shape = CircleShape,
        border = BorderStroke(dimens.Border.thin, badgeColors.second.copy(alpha = 0.3f))
    ) {
        Text(
            text = cell.text,
            style = MaterialTheme.typography.labelSmall.copy(
                color = badgeColors.second,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding._2xs)
        )
    }
}

@Composable
private fun CalloutBox(
    text: String,
    type: CalloutType
) {
    val config = when (type) {
        CalloutType.GREEN -> Triple(Color(0xFF13221E), Color(0xFF00E6C3), "🔒")
        CalloutType.AMBER -> Triple(Color(0xFF221F13), Color(0xFFFFC107), "⚠️")
        CalloutType.BLUE -> Triple(Color(0xFF131922), Color(0xFF2196F3), "📋")
        CalloutType.RED -> Triple(Color(0xFF221313), Color(0xFFF44336), "⛔")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.Padding.xs),
        shape = KarigojobsShapes.medium,
        border = BorderStroke(dimens.Border.thin, config.second.copy(alpha = 0.25f)),
        colors = CardDefaults.cardColors(containerColor = config.first)
    ) {
        Row(
            modifier = Modifier.padding(dimens.Padding.base),
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = config.third,
                style = MaterialTheme.typography.titleMedium.copy(color = config.second)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = KarigojobsText2,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2f
                )
            )
        }
    }
}

@Composable
private fun ContactBlockLayout(block: LegalContentItem.ContactBlock) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.Padding.xs),
        shape = KarigojobsShapes.large,
        border = customCardBorder(),
        colors = CardDefaults.cardColors(containerColor = KarigojobsCard)
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            ContactInfoRow(label = "App", value = block.appName)
            ContactInfoRow(label = "Developer", value = block.developer)
            ContactInfoRow(label = "Email", value = block.email, isEmail = true)
            ContactInfoRow(label = "Grievance Officer", value = block.grievanceOfficer)
            ContactInfoRow(label = "Response time", value = block.responseTime)
        }
    }
}

@Composable
private fun ContactInfoRow(
    label: String,
    value: String,
    isEmail: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = KarigojobsText3,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.width(dimens.Size.chartBarMaxH) // Fixed label width using layout token
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isEmail) KarigojobsIconColor else Color.White,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.weight(1f)
        )
    }
}
