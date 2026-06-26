package com.karigo.app.feature.siteEstimate.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.ui.theme.dimens
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.estimate.add.SiteEstimateEvent
import com.karigojobs.presentation.estimate.add.SiteEstimateState
import com.karigojobs.presentation.estimate.details.EstimateDetailsState
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.common.MinusButton
import com.karigojobs.ui.common.PlusButton
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.dashedBorder
import com.karigojobs.ui.theme.ChartBarInactive
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.SurfaceOverlay
import com.karigojobs.ui.theme.deviceInfo


/**
 * @author hazratummar
 * Created on 06/06/26
 */


@Composable
fun AddMaterialCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .dashedBorder(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                cornerRadius = dimens.Radius.md,
                gapLength = dimens.Space.xs,
            )
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "Add Materials",
                modifier = Modifier.size(dimens.Icon.xs),
                tint = KarigojobsText2
            )
            Spacer(modifier = Modifier.size(dimens.Padding.md))
            Text(
                text = "Add Material",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = KarigojobsText2
                )
            )
        }
    }

}





@Composable
fun SelectedMaterialSection(
    modifier: Modifier = Modifier,
    selectedMaterial: SiteEstimateMaterial,
    isRateVisible: Boolean = true,
    number: Int,
    onMaterialQuantityChange: (String) -> Unit,
    onMaterialMinusClick: () -> Unit,
    onMaterialPlusClick: () -> Unit,
    onMaterialRemoveClick: () -> Unit
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(dimens.Padding.md)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .size(dimens.Icon.base),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$number",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = KarigojobsIconColor
                        )
                    )
                }
                Spacer(Modifier.width(dimens.Space.sm))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    Text(
                        text = selectedMaterial.materialName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    if (isRateVisible) {
                        Text(
                            text = "${deviceInfo.currency}${selectedMaterial.rate} / ${selectedMaterial.unit}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = KarigojobsText2
                            )
                        )
                    }
                }


                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimens.Icon.xs)
                        .clickable(
                            onClick = onMaterialRemoveClick
                        ),
                    tint = MaterialTheme.colorScheme.error,

                    )
            }

            Row(
                modifier = Modifier
                    .padding(dimens.Padding.sm)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = SurfaceOverlay,
                            shape = KarigojobsShapes.medium
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = dimens.Padding.sm),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                    ) {
                        MinusButton(
                            size = dimens.Icon.lg,
                            onClick = onMaterialMinusClick
                        )
                        BasicTextField(
                            value = selectedMaterial.quantityInput,
                            onValueChange = { onMaterialQuantityChange(it) },
                            modifier = Modifier,
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,

                                )

                        )
                        PlusButton(
                            size = dimens.Icon.lg,
                            onClick = onMaterialPlusClick
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = SurfaceOverlay,
                            shape = KarigojobsShapes.small
                        )
                ) {
                    Text(
                        text = selectedMaterial.unit,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        ),
                        modifier = Modifier.padding(dimens.Padding.sm)
                    )
                }
            }
        }
    }
}


@Composable
fun EstimateCard(
    modifier: Modifier = Modifier,
    estimate: SiteEstimateModel,
    onDeleteClick: () -> Unit,
    onEstimateClick: () -> Unit
) {

    Card(
        onClick = onEstimateClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        ),
        shape = KarigojobsShapes.medium
    ) {
        Column(
            modifier = Modifier.padding(dimens.Space.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                KarigoIconWIthBg(
                    icon = R.drawable.estimate,
                    iconColor = KarigojobsIconColor,
                    iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = estimate.projectTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "${estimate.clientName}· ${estimate.date.toReadableDate()}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        )
                    )
                }
                KarigoIconWIthBgCick(
                    icon = R.drawable.delete,
                    iconBackGroundColor = Color.Transparent,
                    iconColor = MaterialTheme.colorScheme.error,
                    onClick = onDeleteClick,
                    size = dimens.Height.minTouch * 0.85f
                )
            }
            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (estimate.showRate) {
                    Text(
                        text = "${deviceInfo.currency}${estimate.total}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = KarigojobsIconColor,
                            fontWeight = FontWeight.W700
                        )
                    )
                } else {
                    Box(
                        modifier = Modifier.background(
                            color = SurfaceOverlay,
                            shape = KarigojobsShapes.medium
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No prices",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = KarigojobsText2
                            ),
                            modifier = Modifier.padding(
                                horizontal = dimens.Padding.md,
                                vertical = dimens.Padding.xs
                            )
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun EstimateDetailsCard(
    modifier: Modifier = Modifier,
    state: EstimateDetailsState
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            Text(
                text = state.estimateDetails?.projectTitle ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "${state.estimateDetails?.clientName}",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = KarigojobsText2
                )
            )
            Spacer(Modifier.height(dimens.Padding.xs))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.calendar1),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.Icon._2xs),
                    tint = KarigojobsText3
                )
                Spacer(Modifier.width(dimens.Padding._2xs))
                Text(
                    text = "${state.estimateDetails?.date?.toReadableDate()}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = KarigojobsText3
                    )
                )
                Spacer(Modifier.width(dimens.Padding.md))
                Icon(
                    painter = painterResource(R.drawable.stack),
                    contentDescription = null,
                    modifier = Modifier.size(dimens.Icon._2xs),
                    tint = KarigojobsText3
                )
                Spacer(Modifier.width(dimens.Padding._2xs))
                Text(
                    text = "${state.siteEstimateMaterial.size} items",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = KarigojobsText3
                    )
                )
            }
            Spacer(Modifier.height(dimens.Padding.xs))

            state.estimateDetails?.siteNote?.let { text ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = ChartBarInactive,
                            shape = KarigojobsShapes.medium
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = KarigojobsText2
                        ),
                        modifier = Modifier.padding(dimens.Padding.base)
                    )
                }
            }
        }
    }
}

@Composable
fun EstimateMaterialsList(
    modifier: Modifier = Modifier,
    state: EstimateDetailsState
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = KarigojobsCard
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = "Materials",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            state.siteEstimateMaterial.forEachIndexed { index, material ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = KarigojobsAccent
                            ),
                            modifier = Modifier.padding(dimens.Padding.sm)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = material.materialName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = "${material.quantity} ${material.unit}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = KarigojobsText2
                            )
                        )
                    }
                    state.estimateDetails?.showRate?.let {
                        if (it) {
                            Text(
                                text = "${deviceInfo.currency} ${material.total}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EstimateTotalCard(
    modifier: Modifier = Modifier,
    totalItemSize : Int ,
    estimateTotal : Double
) {


    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = KarigojobsShapes.medium,
        border = BorderStroke(
            width = dimens.Border.thin,
            color = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Estimate Total",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "$totalItemSize items",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Text(
                text = "${deviceInfo.currency}${estimateTotal}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = KarigojobsIconColor
                )
            )
        }
    }
}