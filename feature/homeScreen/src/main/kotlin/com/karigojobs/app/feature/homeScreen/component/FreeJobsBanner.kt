package com.karigojobs.app.feature.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.ProStatus
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.*
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

    val maxJobs = monthlyJobLimit.maxFreeJobs
    val usedJobs = monthlyJobLimit.usedJobsCount
    val remaining = monthlyJobLimit.remainingJobsCount
    val progress = (usedJobs.toFloat() / maxJobs.toFloat()).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.md))
            .background(appColor.cardColors)
            .border(dimens.Border.thin, appColor.divider, RoundedCornerShape(dimens.Radius.md))
            .padding(dimens.Padding.md)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            // Header Row
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
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(dimens.Icon.xs)
                    )
                    Text(
                        text = stringResource(Res.string.home_free_jobs_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = appColor.primaryText
                    )
                }

                Text(
                    text = stringResource(Res.string.home_free_jobs_remaining, remaining),
                    style = MaterialTheme.typography.bodySmall,
                    color = appColor.secondaryText
                )
            }

            // Linear Progress Indicator
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

            // Subtext & Go Pro Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.home_free_jobs_used_fmt, usedJobs, maxJobs),
                    style = MaterialTheme.typography.labelSmall,
                    color = appColor.tertiaryText
                )

                Button(
                    onClick = onGoProClick,
                    modifier = Modifier.height(dimens.Height.buttonSm),
                    shape = RoundedCornerShape(dimens.Radius.sm),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appColor.iconBgColor,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    val price = monthlyPrice
                    val goProText = if (!price.isNullOrEmpty()) {
                        stringResource(Res.string.home_btn_go_pro, price)
                    } else {
                        stringResource(Res.string.pro_banner_title)
                    }
                    Text(
                        text = goProText,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
