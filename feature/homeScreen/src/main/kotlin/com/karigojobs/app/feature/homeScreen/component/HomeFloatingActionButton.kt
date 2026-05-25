package com.karigojobs.app.feature.homeScreen.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeFloatingActionButton(
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {}
) {
    KarigojobsThemePreview(darkTheme = true) {
        FloatingActionButton(
            onClick = onFabClick,
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                painter = painterResource(R.drawable.add),
                contentDescription = "Add Job",
                modifier = Modifier.size(dimens.Icon.sm)
            )
        }
    }
}