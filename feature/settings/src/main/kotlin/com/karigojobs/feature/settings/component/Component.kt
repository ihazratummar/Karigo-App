package com.karigojobs.feature.settings.component

import androidx.annotation.UiContext
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.settings.SettingsEvent
import com.karigojobs.presentation.settings.SettingsState
import com.karigojobs.share.model.TradeSeeds
import com.karigojobs.share.model.TradeType
import com.karigojobs.ui.color
import com.karigojobs.ui.common.KarigoButtons
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.TradeCard
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.icon
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 27/06/26
 */


data class QuickAccessData(
    val icon: Int,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun SettingsScreenQuickAction(
    modifier: Modifier = Modifier,
    icon: Int,
    label: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
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
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(dimens.Icon.sm)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}


@Composable
fun SettingsComponent(
    modifier: Modifier = Modifier,
    title: String = "MY TRADE",
    action: @Composable () -> Unit? = {},
    content: @Composable () -> Unit = {}
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = KarigojobsText2,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(Modifier.weight(1f))
                action()
            }
            content()
        }
    }
}

data class SettingsTabData(
    val icon: Int,
    val name: String,
    val onClick: () -> Unit
)

@Composable
fun SettingsOptionRow(
    modifier: Modifier = Modifier,
    icon: Int,
    tabName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
    ) {
        KarigoIconWIthBg(
            icon = icon,
            size = dimens.Height.minTouch / 1.3f,
        )
        Text(
            text = tabName,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.weight(1f))
        KarigoIconWIthBg(
            icon = R.drawable.arrow_right,
            iconBackGroundColor = Color.Transparent,
            size = dimens.Height.minTouch / 2.4f
        )
    }
}

@Composable
fun SettingsTradeCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    trades: Set<TradeType>?
) {
    SettingsComponent(
        modifier = modifier,
        title = "MY TRADE",
        action = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.clickable(onClick = onClick)
            ) {
                Text(
                    text = "Change",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = KarigojobsAccent
                    ),
                    modifier = Modifier.padding(dimens.Space.md)
                )
            }
        },
        content = {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(dimens.Space._2xs),
                verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs),
                maxItemsInEachRow = 5
            ) {
                trades?.forEach { trades ->
                    TradeCard(
                        trade = trades
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTradeChangeModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: SettingsState,
    event: (SettingsEvent) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = appColor.modalColor
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(),
            containerColor = Color.Transparent,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    windowInsets = WindowInsets(),
                    containerColor = Color.Transparent
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                    ) {
                        HorizontalDivider()
                        Row(
                            modifier = Modifier
                                .padding(horizontal = dimens.Padding.base)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                        ) {
                            KarigoButtons(
                                modifier = Modifier.weight(1f),
                                onClick = onDismiss,
                                buttonColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                                label = "Cancel"
                            )
                            KarigoButtons(
                                modifier = Modifier.weight(1f),
                                onClick = { event(SettingsEvent.SaveTrades) },
                                label = "Save ${state.selectedCount}",
                                icon = R.drawable.check,
                                enabled = state.selectedCount != 0,
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(dimens.Padding.base)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                    ) {
                        Text(
                            text = "${state.selectedCount} selected",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = appColor.primaryText,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Pick the trades you work in to get relevant starter materials",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = appColor.secondaryText
                            )
                        )
                    }

                    TextButton(
                        onClick = { event(SettingsEvent.ClearAllTrade) }
                    ) {
                        Text(
                            text = "Clear All",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = appColor.secondaryText
                            )
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    items(TradeType.entries.toList()) { trade ->
                        val isSelected = state.editTrades.contains(trade)
                        TradeTypeCheckCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { event(SettingsEvent.EditTrade(trade = trade)) },
                            tradeType = trade,
                            isSelected = isSelected
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun TradeTypeCheckCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    tradeType: TradeType,
    isSelected: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) appColor.accentBg else appColor.iconBgColor,
        ),
        border = if (!isSelected) null else customCardBorder()
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KarigoIconWIthBg(
                icon = tradeType.icon(),
                iconColor = if (isSelected) appColor.primaryText else tradeType.color(),
                iconBackGroundColor = if (isSelected) Color.Transparent else tradeType.color()
                    .copy(0.1f),
                size = dimens.Height.minTouch / 1.1f
            )

            Spacer(Modifier.width(dimens.Space.md))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = tradeType.displayName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = tradeType.description,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = appColor.secondaryText,
                    )
                )
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = {
                    onClick()
                }
            )
        }
    }
}