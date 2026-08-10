package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.paywall_feat_csv
import karigojobs.shared.resources.generated.resources.paywall_feat_discount
import karigojobs.shared.resources.generated.resources.paywall_feat_earnings
import karigojobs.shared.resources.generated.resources.paywall_feat_logo
import karigojobs.shared.resources.generated.resources.paywall_feat_multi_worker
import karigojobs.shared.resources.generated.resources.paywall_feat_pdf_export
import karigojobs.shared.resources.generated.resources.paywall_feat_reminders
import karigojobs.shared.resources.generated.resources.paywall_feat_unlimited_jobs
import karigojobs.shared.resources.generated.resources.paywall_feat_whatsapp
import karigojobs.shared.resources.generated.resources.whats_included_in_pro
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaywallFeaturesComparison(modifier: Modifier = Modifier) {
    val features = listOf(
        Pair(R.drawable.ic_pdf, Res.string.paywall_feat_pdf_export),
        Pair(R.drawable.whatsapp, Res.string.paywall_feat_whatsapp),
        Pair(R.drawable.labour, Res.string.paywall_feat_multi_worker),
        Pair(R.drawable.earning, Res.string.paywall_feat_earnings),
        Pair(R.drawable.store, Res.string.paywall_feat_logo),
        Pair(R.drawable.ic_discount, Res.string.paywall_feat_discount),
        Pair(R.drawable.ic_csv, Res.string.paywall_feat_csv),
        Pair(R.drawable.ic_bell, Res.string.paywall_feat_reminders),
        Pair(R.drawable.ic_infinity, Res.string.paywall_feat_unlimited_jobs)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.lg))
            .background(appColor.cardColors)
            .padding(dimens.Padding.md)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.base),
            modifier = Modifier.padding(bottom = dimens.Padding.sm)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_double_check),
                contentDescription = null,
                tint = Color(0xFF00FFCC),
                modifier = Modifier.size(dimens.Icon.xs)
            )
            Text(
                text = stringResource(Res.string.whats_included_in_pro),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )
        }

        features.forEach { (iconRes, featureRes) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.Padding.sm),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color(0xFF00FFCC),
                    modifier = Modifier.size(dimens.Icon.xs)
                )

                Text(
                    text = stringResource(featureRes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = appColor.primaryText
                )
            }
        }
    }
}
