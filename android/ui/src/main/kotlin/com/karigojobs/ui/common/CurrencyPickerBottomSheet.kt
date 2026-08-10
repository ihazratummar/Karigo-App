package com.karigojobs.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.CurrencyOption
import com.karigojobs.share.model.CurrencyOptions
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.settings_all_currencies
import karigojobs.shared.resources.generated.resources.settings_currency_search_placeholder
import karigojobs.shared.resources.generated.resources.settings_currency_title
import karigojobs.shared.resources.generated.resources.settings_popular_currencies
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerBottomSheet(
    currentCurrencySymbol: String,
    onCurrencySelected: (CurrencyOption) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var searchQuery by remember { mutableStateOf("") }

    val filteredCurrencies = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            CurrencyOptions.ALL_CURRENCIES
        } else {
            val q = searchQuery.trim().lowercase()
            CurrencyOptions.ALL_CURRENCIES.filter {
                it.code.lowercase().contains(q) ||
                        it.name.lowercase().contains(q) ||
                        it.symbol.lowercase().contains(q)
            }
        }
    }

    val popularCurrencies = remember(searchQuery) {
        if (searchQuery.isNotBlank()) emptyList()
        else CurrencyOptions.POPULAR_CURRENCIES
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = appColor.modalColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.Padding.base)
                .padding(bottom = dimens.Padding.xl, top = dimens.Padding.xs),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            // Header Title
            Text(
                text = stringResource(Res.string.settings_currency_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = stringResource(Res.string.settings_currency_search_placeholder),
                        color = appColor.secondaryText
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Search",
                        tint = appColor.secondaryText,
                        modifier = Modifier.size(dimens.Icon.sm)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                painter = painterResource(R.drawable.close),
                                contentDescription = "Clear",
                                tint = appColor.secondaryText,
                                modifier = Modifier.size(dimens.Icon.xs)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = appColor.primaryText,
                    unfocusedTextColor = appColor.primaryText,
                    focusedBorderColor = Color(0xFF00FFCC),
                    unfocusedBorderColor = appColor.secondaryText.copy(alpha = 0.4f)
                ),
                singleLine = true
            )

            // LazyColumn for currency list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = dimens.Space._8xl * 4),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
            ) {
                // Popular Currencies Section (if searching is empty)
                if (popularCurrencies.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(Res.string.settings_popular_currencies).uppercase(),
                            color = appColor.secondaryText,
                            fontSize = dimens.Text._2xs,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = dimens.Padding.xs)
                        )
                    }

                    items(popularCurrencies, key = { "pop_${it.code}" }) { item ->
                        CurrencyRowItem(
                            currencyOption = item,
                            isSelected = currentCurrencySymbol == item.symbol || currentCurrencySymbol == item.code,
                            onClick = {
                                onCurrencySelected(item)
                                onDismissRequest()
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(dimens.Space.sm))
                        Text(
                            text = stringResource(Res.string.settings_all_currencies).uppercase(),
                            color = appColor.secondaryText,
                            fontSize = dimens.Text._2xs,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = dimens.Padding.xs)
                        )
                    }
                }

                // All Currencies List
                items(filteredCurrencies, key = { "all_${it.code}" }) { item ->
                    CurrencyRowItem(
                        currencyOption = item,
                        isSelected = currentCurrencySymbol == item.symbol || currentCurrencySymbol == item.code,
                        onClick = {
                            onCurrencySelected(item)
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyRowItem(
    currencyOption: CurrencyOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimens.Radius.md))
            .background(if (isSelected) Color(0xFF0E2929) else appColor.cardColors)
            .border(
                border = if (isSelected) {
                    androidx.compose.foundation.BorderStroke(dimens.Border.thin, Color(0xFF00FFCC))
                } else {
                    androidx.compose.foundation.BorderStroke(dimens.Border.thin, Color.Transparent)
                },
                shape = RoundedCornerShape(dimens.Radius.md)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = dimens.Padding.base, vertical = dimens.Padding.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            // Flag & Symbol Badge
            Box(
                modifier = Modifier
                    .size(dimens.Icon.xl)
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFF1E3B3B) else Color(0xFF1E2124)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currencyOption.symbol,
                    color = if (isSelected) Color(0xFF00FFCC) else appColor.primaryText,
                    fontSize = dimens.Text.base,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Text(
                        text = currencyOption.flagEmoji,
                        fontSize = dimens.Text.base
                    )
                    Text(
                        text = currencyOption.code,
                        color = appColor.primaryText,
                        fontSize = dimens.Text.base,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = currencyOption.name,
                    color = appColor.secondaryText,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF00FFCC),
                unselectedColor = appColor.secondaryText.copy(alpha = 0.5f)
            )
        )
    }
}
