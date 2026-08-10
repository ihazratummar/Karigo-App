package com.karigojobs.app.feature.homeScreen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.ProStatus
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.home_btn_go_pro_simple
import karigojobs.shared.resources.generated.resources.home_free_jobs_title
import karigojobs.shared.resources.generated.resources.home_quota_estimates
import karigojobs.shared.resources.generated.resources.home_quota_jobs
import karigojobs.shared.resources.generated.resources.home_quota_pdf
import karigojobs.shared.resources.generated.resources.home_quota_used_fmt
import org.jetbrains.compose.resources.stringResource

@Composable
fun FreeJobsBanner(
    proStatus: ProStatus,
    monthlyJobLimit: MonthlyJobLimit,
    onGoProClick: () -> Unit,
    modifier: Modifier = Modifier,
    monthlyPrice: String? = null
) {
    if (proStatus.hasProAccess) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.lg))
            .background(appColor.cardColors)
            .border(dimens.Border.thin, appColor.divider, RoundedCornerShape(dimens.Radius.lg))
            .padding(dimens.Padding.md)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            // Header Row: Icon + Title + Go Pro Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.store),
                        contentDescription = null,
                        tint = Color(0xFFFFB800),
                        modifier = Modifier.size(dimens.Icon.xs)
                    )
                    Text(
                        text = stringResource(Res.string.home_free_jobs_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = appColor.primaryText
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF00FFCC).copy(alpha = 0.12f))
                        .border(BorderStroke(dimens.Border.thin, Color(0xFF00FFCC)), CircleShape)
                        .clickable(onClick = onGoProClick)
                        .padding(horizontal = dimens.Padding.sm, vertical = dimens.Padding._2xs),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.home_btn_go_pro_simple),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FFCC)
                    )
                }
            }

            Spacer(Modifier.height(dimens.Space._2xs))

            // Usage Progress Item 1: Jobs
            QuotaUsageItem(
                label = stringResource(Res.string.home_quota_jobs),
                usedCount = monthlyJobLimit.usedJobsCount,
                maxCount = monthlyJobLimit.maxFreeJobs
            )

            // Usage Progress Item 2: Estimates
            QuotaUsageItem(
                label = stringResource(Res.string.home_quota_estimates),
                usedCount = monthlyJobLimit.usedEstimatesCount,
                maxCount = monthlyJobLimit.maxFreeEstimates
            )

            // Usage Progress Item 3: PDF Exports
            QuotaUsageItem(
                label = stringResource(Res.string.home_quota_pdf),
                usedCount = monthlyJobLimit.usedPdfCount,
                maxCount = monthlyJobLimit.maxFreePdfExports
            )
        }
    }
}

@Composable
private fun QuotaUsageItem(
    label: String,
    usedCount: Int,
    maxCount: Int
) {
    val progress = (usedCount.toFloat() / maxCount.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)

    Column(verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = appColor.secondaryText
            )

            Text(
                text = stringResource(Res.string.home_quota_used_fmt, usedCount, maxCount),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = appColor.secondaryText
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.Space._2xs)
                .clip(RoundedCornerShape(dimens.Radius.xs)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = appColor.iconBgColor,
            strokeCap = StrokeCap.Round
        )
    }
}
