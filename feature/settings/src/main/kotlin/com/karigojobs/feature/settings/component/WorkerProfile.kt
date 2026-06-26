package com.karigojobs.feature.settings.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.common.IconPlaceholder
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 26/06/26
 */

@Composable
fun WorkerProfileCard(
    profile: com.karigojobs.share.model.WorkerProfileModel,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        ),
        shape = KarigojobsShapes.large,
        border = BorderStroke(dimens.Border.thin, Color(0xFF2E2E2E))
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md),
                verticalAlignment = Alignment.CenterVertically
            ) {

                KarigoIconWIthBg(
                    icon = R.drawable.carpenter,
                    iconColor = KarigojobsAccent,
                    iconBackGroundColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                        0.1f
                    )
                )
                Column(
                    modifier = Modifier.weight(1f)
                ){
                    Text(
                        text = profile.businessName.ifBlank { "No Business Name" },
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = profile.ownerName,
                        style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText2)
                    )
                }

                KarigoIconWIthBgCick(
                    icon = R.drawable.edit,
                    iconColor = KarigojobsAccent,
                    iconBackGroundColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.2f),
                    isBorder = true,
                    onClick = onEditClick
                )
            }

            Spacer(modifier = Modifier.height(dimens.Space.xs))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
            ) {
                if (profile.phone.isNotBlank()) {
                    ProfileDetailRow(
                        iconRes = R.drawable.phone,
                        value = profile.phone
                    )
                }
                if (profile.email.isNotBlank()) {
                    ProfileDetailRow(
                        iconRes = R.drawable.email,
                        value = profile.email
                    )
                }

                if (profile.gstNumber.isNotBlank()) {
                    ProfileDetailRow(
                        iconRes = R.drawable.estimate,
                        value = profile.gstNumber
                    )
                }

                if (profile.address.isNotBlank()) {
                    ProfileDetailRow(
                        iconRes = R.drawable.map_point,
                        value = profile.address
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileDetailRow(
    iconRes: Int?,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = KarigojobsText3,
                modifier = Modifier.size(dimens.Icon._2xs)
            )
        } else {
            IconPlaceholder(size = dimens.Size.badgeMin, color = KarigojobsIconColor)
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(color = KarigojobsText3)
        )
    }
}