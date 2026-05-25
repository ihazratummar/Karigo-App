package com.karigojobs.app.feature.homeScreen.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.color
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */

@Composable
fun ScrollableTradeView(trades: Set<TradeType> = emptySet()) {

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(trades.toList()) { trade ->
            Box(
                modifier = Modifier
                    .padding(vertical = dimens.Padding.xs, horizontal = dimens.Padding.xs)
                    .clip(KarigojobsShapes.medium)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(
                        vertical = dimens.Padding.xs,
                        horizontal = dimens.Padding.sm
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon.xs)
                            .clip(KarigojobsShapes.small)
                            .background(
                                color = trade.color().copy(0.2f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(trade.icon()),
                            contentDescription = null,
                            modifier = Modifier.size(dimens.Icon._2xs),
                            tint = trade.color()
                        )
                    }
                    Text(
                        text = trade.displayName,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}


@Composable
fun RecentJobs(
    modifier: Modifier = Modifier,
    job: JobModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = KarigojobsShapes.large,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                KarigoIconWIthBg(
                    icon = job.tradeType.icon(),
                    iconColor = KarigojobsAccent,
                    iconBackGroundColor = SurfaceOverlay
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    Text(
                        text = job.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                    Text(
                        text = job.clientName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(KarigojobsShapes.small)
                        .background(
                            color = job.status.color().surface
                        )
                        .border(
                            width = dimens.Border.thin,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = KarigojobsShapes.small
                        )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = job.status.toString(),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding._2xs
                        ),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = job.status.color().accent
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.createdAt.toReadableDate(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "${deviceInfo.currency}${job.total}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}
