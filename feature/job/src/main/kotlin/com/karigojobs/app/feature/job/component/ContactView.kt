package com.karigojobs.app.feature.job.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.karigojobs.app.android.ui.R
import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsSearchField
import com.karigojobs.ui.theme.ModalBackGround
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 24/05/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPicker(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onContactClick: (DeviceContact) -> Unit = {},
    contacts: List<DeviceContact> = emptyList(),
    isLoading: Boolean = false
) {
    var search by remember { mutableStateOf("") }
    val filteredContacts = remember(search, contacts) {
        if (search.isBlank()) contacts
        else contacts.filter {
            it.name.contains(search, ignoreCase = true) ||
                    it.phoneNumber.any { number -> number.contains(search) }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = ModalBackGround
    ) {
        Column(
            modifier = Modifier.padding(horizontal = dimens.Padding.xl),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            Text(
                text = "Select Client",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )

            KarigojobsSearchField(
                modifier = Modifier,
                query = search,
                onQueryChange = { search = it },
                placeholder = "Search Client..."
            )
            HorizontalDivider()

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredContacts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No contacts found",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
                ) {
                    items(filteredContacts) { contact ->
                        ClientInfo(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        onContactClick(contact)
                                        onDismiss()
                                    }
                                ),
                            deviceContact = contact
                        )
                    }
                }
            }
        }

    }
}


@Composable
fun ClientInfo(
    modifier: Modifier = Modifier,
    deviceContact: DeviceContact
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        KarigoIconWIthBgCick(
            icon = R.drawable.user_line,
            iconColor = MaterialTheme.colorScheme.primary,
            iconBackGroundColor = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(Modifier.width(dimens.Space.base))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = deviceContact.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = deviceContact.phoneNumber.firstOrNull() ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}