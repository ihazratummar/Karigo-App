package com.karigojobs.app.feature.homeScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.ui.getGreeting
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_contractor
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.milliseconds


/**
 * @author hazratummar
 * Created on 23/05/26
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    profile : WorkerProfileModel?
) {

    var greeting by remember { mutableStateOf(getGreeting()) }
    LaunchedEffect(Unit) {
        while (true){
            greeting = getGreeting()
            delay((60000 * 5).milliseconds)
        }
    }

    TopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimens.Space.xs),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${stringResource(greeting)}, ${profile?.ownerName ?: stringResource(Res.string.common_contractor)}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = profile?.businessName?:"Karigo",
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