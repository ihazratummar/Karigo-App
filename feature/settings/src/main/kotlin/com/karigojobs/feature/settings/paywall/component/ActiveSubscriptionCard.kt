package com.karigojobs.feature.settings.paywall.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
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
import karigojobs.shared.resources.generated.resources.activated_on
import karigojobs.shared.resources.generated.resources.days_remaining
import karigojobs.shared.resources.generated.resources.next_renewal
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActiveSubscriptionCard(
    planTitle: String,
    priceLabel: String,
    activatedOnFormatted: String,
    nextRenewalFormatted: String,
    daysRemainingText: String,
    progressFraction: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.xl))
            .background(appColor.cardColors)
            .border(BorderStroke(dimens.Border.thin, appColor.divider), RoundedCornerShape(dimens.Radius.xl))
    ) {
        Column(modifier = Modifier.padding(dimens.Padding.md)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Box(
                    modifier = Modifier
                        .size(dimens.Icon._3xl)
                        .clip(RoundedCornerShape(dimens.Radius.md))
                        .background(Color(0xFF0E2929)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.crown_fill),
                        contentDescription = null,
                        tint = Color(0xFF00FFCC),
                        modifier = Modifier.size(dimens.Icon.md)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = planTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = appColor.primaryText
                    )
                    Text(
                        text = priceLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = appColor.secondaryText
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon._2xs)
                            .clip(CircleShape)
                            .background(Color(0xFF00FFCC))
                    )
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FFCC)
                    )
                }
            }

            Spacer(Modifier.height(dimens.Space.md))

            // Metadata Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimens.Radius.md))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                    .padding(dimens.Padding.md)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(Res.string.activated_on),
                            style = MaterialTheme.typography.bodySmall,
                            color = appColor.secondaryText
                        )
                        Text(
                            text = activatedOnFormatted,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = appColor.primaryText
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(Res.string.next_renewal),
                            style = MaterialTheme.typography.bodySmall,
                            color = appColor.secondaryText
                        )
                        Text(
                            text = nextRenewalFormatted,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = appColor.primaryText
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(Res.string.days_remaining),
                            style = MaterialTheme.typography.bodySmall,
                            color = appColor.secondaryText
                        )
                        Text(
                            text = daysRemainingText,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00FFCC)
                        )
                    }
                }
            }

            Spacer(Modifier.height(dimens.Space.sm))

            // Progress Bar Indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Border.thick * 2)
                    .clip(CircleShape)
                    .background(appColor.divider)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .height(dimens.Border.thick * 2)
                        .clip(CircleShape)
                        .background(Color(0xFF00FFCC))
                )
            }
        }
    }
}
