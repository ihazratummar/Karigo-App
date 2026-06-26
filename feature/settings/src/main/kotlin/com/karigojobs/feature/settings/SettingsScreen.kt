package com.karigojobs.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import com.karigojobs.app.android.ui.R
import com.karigojobs.feature.settings.component.WorkerProfileCard
import com.karigojobs.presentation.settings.SettingsState
import com.karigojobs.ui.common.ActionNeedBanner
import com.karigojobs.ui.common.IconPlaceholder
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.dimens

/**
 * Settings screen showing either a banner to complete setup or the worker's business profile card.
 */
@Composable
fun SettingsScreen(
    state: SettingsState,
    onCompleteBannerClick: () -> Unit = {}
) {
    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                title = "Settings",
                isNavBack = false,
                isDivider = false
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .contentHorizontalPadding(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base)
        ) {
            item {
                val profile = state.workerProfileModel
                if (profile == null) {
                    ActionNeedBanner(onClick = onCompleteBannerClick)
                } else {
                    WorkerProfileCard(
                        profile = profile,
                        onEditClick = onCompleteBannerClick
                    )
                }
            }
        }
    }
}

