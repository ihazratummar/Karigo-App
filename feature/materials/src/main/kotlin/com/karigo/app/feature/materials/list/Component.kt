package com.karigo.app.feature.materials.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
                value = state.editingMaterial?.name ?:"",
                onValueChange = { event(MaterialListEvent.EditMaterialName(it)) },
                placeholder = "e.g. PVC Pipe 1/2 inch"
            )

            Row(
                modifier = Modifier.contentHorizontalPadding().fillMaxWidth(),
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
                        value = state.editingMaterial?.unit?:"",
                        onValueChange = { event(MaterialListEvent.EditMaterialUnit(it)) },
                        placeholder = "ea, mtr, kg"
                    )
                }
            }

            HorizontalDivider()

            Button(
                onClick = { event(MaterialListEvent.UpdateMaterials) },
                modifier = Modifier.contentHorizontalPadding().fillMaxWidth(),
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

