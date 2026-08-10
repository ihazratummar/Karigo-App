package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsWarning
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_early_adopter_desc
import karigojobs.shared.resources.generated.resources.paywall_early_adopter_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun EarlyAdopterBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.md))
            .background(appColor.cardColors)
            .border(dimens.Border.thin, appColor.divider, RoundedCornerShape(dimens.Radius.md))
            .padding(dimens.Padding.md)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)) {
            Icon(
                painter = painterResource(R.drawable.info),
                contentDescription = null,
                tint = KarigojobsWarning,
                modifier = Modifier.size(dimens.Icon.xs)
            )
            Column {
                Text(
                    text = stringResource(Res.string.paywall_early_adopter_title),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = KarigojobsWarning
                )
                Spacer(Modifier.height(dimens.Space._2xs))
                Text(
                    text = stringResource(Res.string.paywall_early_adopter_desc),
                    style = MaterialTheme.typography.bodySmall,
                    color = appColor.tertiaryText
                )
            }
        }
    }
}
