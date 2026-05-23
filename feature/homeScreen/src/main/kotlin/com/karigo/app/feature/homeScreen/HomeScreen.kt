package com.karigo.app.feature.homeScreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.karigo.ui.theme.KarigoThemePreview
import com.karigo.ui.theme.dimens
import kotlin.random.Random


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    KarigoThemePreview (darkTheme = true){
        Scaffold (
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimens.spacingXs),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Good Morning, Hazrat Ummar",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "Ummar Construction",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    },
                    windowInsets = WindowInsets(),
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimens.spacingMd),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(30) {
                    val randomColor = Color(
                        red = Random.nextInt(256),
                        green = Random.nextInt(256),
                        blue = Random.nextInt(256)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimens.topBarHeight)
                            .background(
                                color = randomColor
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$it"
                        )
                    }
                }
            }
        }
    }
}