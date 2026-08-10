package com.karigojobs.ui.common

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.toLocaleString
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.payments_add_payment
import karigojobs.shared.resources.generated.resources.payments_amount_label
import karigojobs.shared.resources.generated.resources.payments_btn_add_payment
import karigojobs.shared.resources.generated.resources.payments_date_label
import karigojobs.shared.resources.generated.resources.payments_method_bank_transfer
import karigojobs.shared.resources.generated.resources.payments_method_cash
import karigojobs.shared.resources.generated.resources.payments_method_upi
import karigojobs.shared.resources.generated.resources.payments_no_payments_yet
import karigojobs.shared.resources.generated.resources.payments_note_label
import karigojobs.shared.resources.generated.resources.payments_note_placeholder
import karigojobs.shared.resources.generated.resources.payments_payment_method
import karigojobs.shared.resources.generated.resources.payments_received_title
import karigojobs.shared.resources.generated.resources.payments_record_payment_title
import karigojobs.shared.resources.generated.resources.payments_remaining_balance
import karigojobs.shared.resources.generated.resources.payments_total_received
import org.jetbrains.compose.resources.stringResource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class UiPaymentRecord(
    val id: String,
    val amount: Double,
    val paymentMethod: String,
    val paymentDate: Long,
    val note: String
)

@Composable
fun PaymentsReceivedCard(
    currency: String,
    payments: List<UiPaymentRecord>,
    totalReceived: Double,
    onAddPaymentClick: () -> Unit,
    onDeletePaymentClick: (String) -> Unit,
    formatEpochMs: (Long) -> String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
        shape = KarigojobsShapes.medium,
        border = BorderStroke(dimens.Border.thin, appColor.divider)
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.Padding.base)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_payments_received),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(dimens.Icon.sm)
                    )
                    Text(
                        text = stringResource(Res.string.payments_received_title),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = appColor.primaryText,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Pill button for Add Payment
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(dimens.Radius.xl))
                        .border(BorderStroke(dimens.Border.thin, appColor.divider), RoundedCornerShape(dimens.Radius.xl))
                        .clickable { onAddPaymentClick() }
                        .padding(horizontal = dimens.Space.base, vertical = dimens.Space.sm)
                ) {
                    Text(
                        text = stringResource(Res.string.payments_add_payment),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = dimens.Text.base,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Payment Items
            if (payments.isEmpty()) {
                Text(
                    text = stringResource(Res.string.payments_no_payments_yet),
                    color = appColor.secondaryText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = dimens.Padding.sm)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)) {
                    payments.forEach { pay ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(dimens.Radius.md))
                                .background(appColor.iconBgColor)
                                .padding(horizontal = dimens.Space.base, vertical = dimens.Space._2md),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(dimens.Icon.base)
                                        .clip(CircleShape)
                                        .background(appColor.accentBg)
                                        .border(BorderStroke(dimens.Border.thin, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currency,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = dimens.Text.sm,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                                    ) {
                                        Text(
                                            text = "$currency${pay.amount.toLocaleString()}",
                                            color = appColor.primaryText,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(dimens.Radius.xs))
                                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                                .padding(horizontal = dimens.Space.sm, vertical = dimens.Space._2xs)
                                        ) {
                                            Text(
                                                text = pay.paymentMethod,
                                                color = appColor.primaryText,
                                                fontSize = dimens.Text._2xs,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    val details = formatEpochMs(pay.paymentDate) + if (pay.note.isNotBlank()) " · ${pay.note}" else ""
                                    Text(
                                        text = details,
                                        color = appColor.secondaryText,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                            IconButton(
                                onClick = { onDeletePaymentClick(pay.id) },
                                modifier = Modifier.size(dimens.Icon.base)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.delete),
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(dimens.Icon.xs)
                                )
                            }
                        }
                    }
                }
            }

            // Divider
            HorizontalDivider(
                color = appColor.divider,
                thickness = dimens.Border.thin
            )

            // Total Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.payments_total_received),
                    color = appColor.secondaryText,
                    fontSize = dimens.Text.base,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$currency${totalReceived.toLocaleString()}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = dimens.Text.lg,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordPaymentBottomSheet(
    currency: String,
    remainingBalance: Double,
    amountInput: String,
    onAmountChange: (String) -> Unit,
    selectedMethod: String,
    onMethodSelect: (String) -> Unit,
    paymentDate: Long,
    onDatePickerClick: () -> Unit,
    noteInput: String,
    onNoteChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAddPaymentClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val sheetDateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val cashText = stringResource(Res.string.payments_method_cash)
    val upiText = stringResource(Res.string.payments_method_upi)
    val bankText = stringResource(Res.string.payments_method_bank_transfer)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = appColor.modalColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.Padding.base)
                .padding(bottom = dimens.Padding.xl, top = dimens.Padding.sm),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Text(
                text = stringResource(Res.string.payments_record_payment_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = appColor.primaryText,
                    fontWeight = FontWeight.Bold
                )
            )

            // Label: AMOUNT
            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                Text(
                    text = stringResource(Res.string.payments_amount_label, currency),
                    color = appColor.secondaryText,
                    fontSize = dimens.Text.sm,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = amountInput,
                    onValueChange = onAmountChange,
                    placeholder = { Text("0", color = appColor.secondaryText) },
                    prefix = { Text("$currency ", color = appColor.secondaryText) },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_stepper),
                            contentDescription = null,
                            tint = appColor.secondaryText,
                            modifier = Modifier.size(dimens.Icon.sm)
                        )
                    },
                    supportingText = {
                        Text(
                            text = stringResource(Res.string.payments_remaining_balance, "$currency${remainingBalance.toLocaleString()}"),
                            color = appColor.secondaryText
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = appColor.primaryText,
                        unfocusedTextColor = appColor.primaryText,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = appColor.divider
                    ),
                    singleLine = true
                )
            }

            // Label: PAYMENT METHOD
            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                Text(
                    text = stringResource(Res.string.payments_payment_method),
                    color = appColor.secondaryText,
                    fontSize = dimens.Text.sm,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    listOf(cashText, upiText, bankText).forEach { method ->
                        val isSelected = selectedMethod == method || (selectedMethod == "Cash" && method == cashText) || (selectedMethod == "UPI" && method == upiText) || (selectedMethod == "Bank Transfer" && method == bankText)
                        val iconRes = when {
                            method == upiText || method == "UPI" -> R.drawable.ic_upi
                            else -> R.drawable.ic_bank
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(dimens.Radius.sm))
                                .background(if (isSelected) appColor.accentBg else appColor.cardColors)
                                .border(
                                    BorderStroke(dimens.Border.thin, if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent),
                                    RoundedCornerShape(dimens.Radius.sm)
                                )
                                .clickable { onMethodSelect(method) }
                                .padding(vertical = dimens.Space.base),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                            ) {
                                if (method == cashText || method == "Cash") {
                                    Box(
                                        modifier = Modifier
                                            .size(dimens.Icon.base)
                                            .clip(CircleShape)
                                            .background(if (isSelected) appColor.accentBg else appColor.iconBgColor)
                                            .border(BorderStroke(dimens.Border.thin, if (isSelected) MaterialTheme.colorScheme.primary else appColor.secondaryText.copy(alpha = 0.4f)), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = currency,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else appColor.secondaryText,
                                            fontSize = dimens.Text.sm,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                } else {
                                    Icon(
                                        painter = painterResource(iconRes),
                                        contentDescription = null,
                                        tint = if (isSelected) MaterialTheme.colorScheme.primary else appColor.secondaryText,
                                        modifier = Modifier.size(dimens.Icon.base)
                                    )
                                }
                                Text(
                                    text = method,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else appColor.secondaryText,
                                    fontSize = dimens.Text.sm,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Label: DATE
            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                Text(
                    text = stringResource(Res.string.payments_date_label),
                    color = appColor.secondaryText,
                    fontSize = dimens.Text.sm,
                    fontWeight = FontWeight.Bold
                )
                val formattedDate = sheetDateFormat.format(Date(paymentDate))
                OutlinedTextField(
                    value = formattedDate,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = onDatePickerClick) {
                            Icon(
                                painter = painterResource(R.drawable.calendar1),
                                contentDescription = "Select Date",
                                tint = appColor.secondaryText
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDatePickerClick() },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = appColor.primaryText,
                        unfocusedTextColor = appColor.primaryText,
                        focusedBorderColor = appColor.divider,
                        unfocusedBorderColor = appColor.divider
                    )
                )
            }

            // Label: NOTE (OPTIONAL)
            Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)) {
                Text(
                    text = stringResource(Res.string.payments_note_label),
                    color = appColor.secondaryText,
                    fontSize = dimens.Text.sm,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = noteInput,
                    onValueChange = onNoteChange,
                    placeholder = { Text(stringResource(Res.string.payments_note_placeholder), color = appColor.secondaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = appColor.primaryText,
                        unfocusedTextColor = appColor.primaryText,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = appColor.divider
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(dimens.Space.md))

            val isAmountValid = (amountInput.toDoubleOrNull() ?: 0.0) > 0.0 &&
                    (amountInput.toDoubleOrNull() ?: 0.0) <= remainingBalance

            Button(
                onClick = onAddPaymentClick,
                enabled = isAmountValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAmountValid) MaterialTheme.colorScheme.surfaceVariant else appColor.iconBgColor,
                    contentColor = if (isAmountValid) appColor.primaryText else appColor.secondaryText.copy(alpha = 0.5f),
                    disabledContainerColor = appColor.iconBgColor,
                    disabledContentColor = appColor.secondaryText.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(dimens.Radius.xl),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Height.minTouch)
            ) {
                Text(stringResource(Res.string.payments_btn_add_payment), fontWeight = FontWeight.Bold)
            }
        }
    }
}
