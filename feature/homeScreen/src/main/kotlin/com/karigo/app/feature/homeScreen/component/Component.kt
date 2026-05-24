package com.karigo.app.feature.homeScreen.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigo.share.model.TradeType
import com.karigo.ui.color
import com.karigo.ui.icon
import com.karigo.ui.theme.KarigoShapes
import com.karigo.ui.theme.KarigoThemePreview
import com.karigo.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ScrollableTradeView(trades: Set<TradeType> = emptySet()){
    KarigoThemePreview(darkTheme = true) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(trades.toList()) { trade ->
                Box(
                    modifier = Modifier
                        .padding(vertical = dimens.Padding.xs, horizontal = dimens.Padding.xs)
                        .clip(KarigoShapes.medium)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = dimens.Padding.xs, horizontal = dimens.Padding.sm),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(dimens.Icon.xs)
                                .clip(KarigoShapes.small)
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

}
