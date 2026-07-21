package com.karigojobs.feature.client.component

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.karigojob.share.utils.formatNumber
import com.karigojob.share.utils.toInitials
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.ClientModel
import com.karigojobs.ui.common.CommunicationButton
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigoSelectedCardColor
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.theme.whatsAppColor
import com.karigojobs.ui.toLocaleString


/**
 * @author hazratummar
 * Created on 24/06/26
 */


data class EarningState(
    val title: String,
    val number: Double,
    val color: Color,
    val isCount: Boolean = false
)

@Composable
fun EarningCard(
    modifier: Modifier = Modifier,
    title: String = "TOTAL JOBS",
    number: Double = 0.0,
    color: Color,
    isCount: Boolean = false
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        shape = KarigojobsShapes.medium,
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = appColor.secondaryText
                )
            )

            Text(
                text = if (isCount) number.toLocaleString() else "${deviceInfo.currency} ${number.toLocaleString()}",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}


@Composable
fun ClientCardWithAction(
    modifier: Modifier = Modifier,
    client: ClientModel,
    onCallClick: () -> Unit = {},
    onWhatAppClick: () -> Unit = {}
) {


    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        shape = KarigojobsShapes.medium,
        border = customCardBorder()
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
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
            ) {
                Box(
                    modifier = Modifier.size(dimens.Height.inputLg)
                        .background(
                            color = appColor.accentBg,
                            shape = KarigojobsShapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = client.name.toInitials(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = KarigojobsAccent
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    Text(
                        text = client.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = client.phone,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = appColor.secondaryText,
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CommunicationButton(
                    modifier = Modifier.weight(1f),
                    onClick = onCallClick,
                    buttonColor = appColor.accentBg,
                    contentColor = KarigojobsAccent,
                    icon = R.drawable.phone,
                    buttonText = "Call"
                )
                Spacer(Modifier.width(dimens.Space.sm))
                CommunicationButton(
                    modifier = Modifier.weight(1f),
                    onClick = onWhatAppClick,
                    buttonColor = Color.Transparent,
                    contentColor = whatsAppColor,
                    icon = R.drawable.whatsapp,
                    buttonText = "WhatsApp"
                )
            }
        }
    }
}


