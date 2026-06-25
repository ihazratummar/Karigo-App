package com.karigojobs.feature.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.karigojobs.presentation.settings.SettingsState
import com.karigojobs.ui.common.ActionNeedBanner
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.common.contentHorizontalPadding


/**
 * @author hazratummar
 * Created on 24/06/26
 */


@Composable
fun SettingsScreen(
    state: SettingsState,
    onCompleteBannerClick: () -> Unit = {}
) {


    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        topBar ={
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
                .contentHorizontalPadding()
        ) {
            item {
                if (state.workerProfileModel == null){
                    ActionNeedBanner(onClick = onCompleteBannerClick)
                }
            }
        }
    }
}