package com.karigojobs.feature.settings.earnings.earningCompoent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoTextWIthBg
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.client_details_total_jobs
import karigojobs.shared.resources.generated.resources.common_jobs_count
import karigojobs.shared.resources.generated.resources.earnings_jobs_count
import karigojobs.shared.resources.generated.resources.earnings_label_revenue
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 30/07/26
 */


data class EarningTopCardData(
    val headerText: StringResource,
    val balanceText: String,
    val labelText: String
)


@Composable
internal fun EarningTopCard(
    modifier: Modifier,
    headerText: StringResource,
    balanceText: String,
    labelText: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = RoundedCornerShape(dimens.Radius.md),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
        ) {
            Text(
                text = stringResource(headerText),
                color = appColor.secondaryText,
                fontSize = dimens.Text._2xs,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = balanceText,
                color = appColor.primaryText,
                fontSize = dimens.Text.lg,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = labelText,
                color = appColor.secondaryText,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@Composable
fun TopClientCard(
    modifier: Modifier = Modifier,
    index: Int,
    clientName: String,
    totalJobs: Int,
    totalRevenue: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = KarigojobsShapes.medium
    ) {
        Row(
            modifier = Modifier.padding(dimens.Space.base),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            KarigoTextWIthBg(text = index.toLocaleString(), size = dimens.Height.minTouch / 1.4f)

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(Res.string.common_jobs_count, totalJobs),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = appColor.secondaryText
                    )
                )
            }

            Text(
                text = totalRevenue,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}