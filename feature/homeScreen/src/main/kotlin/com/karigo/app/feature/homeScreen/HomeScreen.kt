package com.karigo.app.feature.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.karigo.ui.theme.dimens
import kotlin.random.Random


/**
 * @author hazratummar
 * Created on 18/05/26
 */


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Surface () {  ->
        LazyColumn(
            modifier = Modifier
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