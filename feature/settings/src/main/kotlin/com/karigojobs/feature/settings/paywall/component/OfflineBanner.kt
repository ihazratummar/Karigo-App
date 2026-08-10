package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsWarning
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_offline_desc
import karigojobs.shared.resources.generated.resources.paywall_offline_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun OfflineBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(KarigojobsWarning.copy(alpha = 0.15f))
            .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.xs)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
        ) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                tint = KarigojobsWarning,
                modifier = Modifier.size(dimens.Icon.xs)
            )
            Column {
                Text(
                    text = stringResource(Res.string.paywall_offline_title),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = KarigojobsWarning
                )
                Text(
                    text = stringResource(Res.string.paywall_offline_desc),
                    style = MaterialTheme.typography.labelSmall,
                    color = KarigojobsWarning.copy(alpha = 0.85f)
                )
            }
        }
    }
}
