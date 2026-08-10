package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_trust_cancel
import karigojobs.shared.resources.generated.resources.paywall_trust_secure
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrustIndicators(modifier: Modifier = Modifier) {
    val items = listOf(
        Pair(R.drawable.privacy_policy, Res.string.paywall_trust_secure),
        Pair(R.drawable.close, Res.string.paywall_trust_cancel)
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { (icon, label) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(dimens.Icon._2xs)
                )
                Text(stringResource(label), style = MaterialTheme.typography.labelSmall, color = appColor.secondaryText)
            }
        }
    }
}
