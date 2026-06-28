package com.karigojobs.app.feature.homeScreen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.TradeCard
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
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
            TradeCard(trade = trade)
        }
    }
}


@Composable
fun SiteEstimatesCard(
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onAddEstimateClick : () -> Unit = {}
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = KarigojobsCard),
        shape = KarigojobsShapes.medium
    ) {

        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Site Estimates",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Box(
                    modifier = Modifier
                        .bounceClickable(onSeeAllClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            Text(
                text = "Walk a job site, build a material list, and WhatsApp it to client.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Button(
                onClick = onAddEstimateClick,
                shape = KarigojobsShapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    width = dimens.Border.thin,
                    color = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add New Estimate",
                    modifier = Modifier.size(dimens.Icon.xs)
                )
                Spacer(Modifier.width(dimens.Space.base))

                Text(
                    text = "New Estimate",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

            }
        }
    }
}