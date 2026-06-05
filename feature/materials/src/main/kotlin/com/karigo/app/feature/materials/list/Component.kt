package com.karigo.app.feature.materials.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.karigojobs.presentation.materials.list.MaterialListEvent
import com.karigojobs.presentation.materials.list.MaterialListState
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.deviceInfo
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 05/06/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialEditModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit
) {


    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = ModalBackGround
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = "Edit Material",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.contentHorizontalPadding()
            )
            HorizontalDivider()
            Text(
                text = "NAME",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = KarigojobsText2
                ),
                modifier = Modifier.contentHorizontalPadding()
            )
            KarigojobsTextField(
                modifier = Modifier.contentHorizontalPadding(),
                value = state.editingMaterial?.name ?: "",
                onValueChange = { event(MaterialListEvent.EditMaterialName(it)) },
                placeholder = "e.g. PVC Pipe 1/2 inch"
            )

            Row(
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "PRICE(${deviceInfo.currency})",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KarigojobsText2
                        )
                    )
                    KarigojobsTextField(
                        value = state.editingMaterial?.price.toString(),
                        onValueChange = { event(MaterialListEvent.EditMaterialPrice(it)) },
                        placeholder = "0",
                        keyboardType = KeyboardType.Number
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "UNIT",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KarigojobsText2
                        )
                    )
                    KarigojobsTextField(
                        value = state.editingMaterial?.unit ?: "",
                        onValueChange = { event(MaterialListEvent.EditMaterialUnit(it)) },
                        placeholder = "ea, mtr, kg"
                    )
                }
            }

            HorizontalDivider()

            Button(
                onClick = { event(MaterialListEvent.UpdateMaterials) },
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxWidth(),
                shape = KarigojobsShapes.medium
            ) {
                Text(
                    text = "Save Changes",
                    modifier = Modifier.padding(dimens.Padding.sm)
                )
            }

            Spacer(Modifier.height(dimens.Space._2xl))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMaterialModal(
    modifier: Modifier = Modifier,
    state: MaterialListState,
    event: (MaterialListEvent) -> Unit
) {


    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { event(MaterialListEvent.ToggleAddMaterialModal(isOpen = false)) },
        containerColor = ModalBackGround
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            Text(
                text = "Add Material",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.contentHorizontalPadding()
            )
            HorizontalDivider()
            Text(
                text = "NAME",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = KarigojobsText2
                ),
                modifier = Modifier.contentHorizontalPadding()
            )
            KarigojobsTextField(
                modifier = Modifier.contentHorizontalPadding(),
                value = state.newMaterialName,
                onValueChange = { event(MaterialListEvent.NewMaterialName(it)) },
                placeholder = "e.g. PVC Pipe 1/2 inch"
            )


            Column(
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Text(
                    text = "CATEGORY",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = KarigojobsText2
                    )
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
                ) {
                    state.selectTrades.forEach { tradeType ->
                        val isSelected = state.newMaterialTradeType == tradeType
                        Card(
                            onClick = {
                                event(MaterialListEvent.NewMaterialTradeType(tradeType = tradeType))
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) KarigojobsAccent else KarigojobsCard
                            ),
                            shape = KarigojobsShapes.large,
                            border = customCardBorder()
                        ) {
                            Text(
                                text = tradeType.displayName,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else KarigojobsText2
                                ),
                                modifier = Modifier.padding(
                                    horizontal = dimens.Padding.base,
                                    vertical = dimens.Padding.sm
                                )
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "PRICE(${deviceInfo.currency})",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KarigojobsText2
                        )
                    )
                    KarigojobsTextField(
                        value = state.newMaterialPrice,
                        onValueChange = { event(MaterialListEvent.NewMaterialRate(it)) },
                        placeholder = "0",
                        keyboardType = KeyboardType.Number
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Text(
                        text = "UNIT",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = KarigojobsText2
                        )
                    )
                    KarigojobsTextField(
                        value = state.newMaterialUnit,
                        onValueChange = { event(MaterialListEvent.NewMaterialUnit(it)) },
                        placeholder = "ea, mtr, kg"
                    )
                }
            }

            HorizontalDivider()

            Button(
                onClick = { event(MaterialListEvent.AddMaterial) },
                modifier = Modifier
                    .contentHorizontalPadding()
                    .fillMaxWidth(),
                shape = KarigojobsShapes.medium,
                enabled = state.canAddNewMaterial
            ) {
                Text(
                    text = "Add to Library",
                    modifier = Modifier.padding(dimens.Padding.sm)
                )
            }

            Spacer(Modifier.height(dimens.Space._2xl))
        }
    }
}



