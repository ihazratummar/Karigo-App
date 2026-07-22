package com.karigojobs.feature.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.common.KarigoIconWIthBg
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens

@Composable
fun SettingsValueRow(
    modifier: Modifier = Modifier,
    icon: Int,
    label: String,
    value: String,
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
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        )
        KarigoIconWIthBg(
            icon = R.drawable.arrow_right,
            iconBackGroundColor = Color.Transparent,
            size = dimens.Height.minTouch / 2.4f
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SettingsSelectionModal(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    items: List<T>,
    selectedItem: T,
    itemLabel: @Composable (T) -> String,
    onItemSelected: (T) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = appColor.modalColor
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(dimens.Padding.base)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = appColor.secondaryText
                        )
                    )
                }
                
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    items(items) { item ->
                        val isSelected = item == selectedItem
                        SettingsSelectionCard(
                            modifier = Modifier.fillMaxWidth(),
                            label = itemLabel(item),
                            isSelected = isSelected,
                            onClick = { onItemSelected(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSelectionCard(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) appColor.accentBg else appColor.cardColors
        ),
        border = customCardBorder()
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = appColor.primaryText,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            )
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
        }
    }
}
