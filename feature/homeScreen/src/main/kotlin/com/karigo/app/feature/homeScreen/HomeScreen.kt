package com.karigo.app.feature.homeScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigo.app.feature.homeScreen.component.HomeFloatingActionButton
import com.karigo.app.feature.homeScreen.component.HomeTopAppBar
import com.karigo.app.feature.homeScreen.component.ScrollableTradeView
import com.karigo.presentation.dashboard.HomeState
import com.karigo.share.model.TradeType
import com.karigo.ui.color
import com.karigo.ui.icon
import com.karigo.ui.theme.KarigoShapes
import com.karigo.ui.theme.KarigoThemePreview
import com.karigo.ui.theme.dimens
import kotlin.random.Random


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit,
    onFabClick:() -> Unit,
    onSeeAllJobClick: () -> Unit,
    homeState: HomeState
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopAppBar()
        },
        contentWindowInsets = WindowInsets(),
        floatingActionButton = {
            HomeFloatingActionButton(
                onFabClick = onFabClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.base),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ScrollableTradeView(trades = homeState.selectedTrades)
            }
        }
    }
}