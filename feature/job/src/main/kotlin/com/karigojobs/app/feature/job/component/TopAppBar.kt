package com.karigojobs.app.feature.job.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.NavInactive
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */

@Composable
fun AppBarIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.arrow_left,
    iconColor: Color = NavInactive,
    iconBackGroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(dimens.Space._4xl)
            .padding(dimens.Padding.xs)
            .clip(KarigojobsShapes.medium)
            .background(color = iconBackGroundColor)
            .clickable(onClick = onClick)
        ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Back",
            modifier = Modifier.size(dimens.Icon.xs),
            tint = iconColor
        )
    }
}

@Composable
fun TopBarTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = "New Job",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobTopAppBar(
    onNavigationClick: () -> Unit = {},
    onAction: () -> Unit = {},
    canSave: Boolean = false
) {
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            title = {
                TopBarTitle()
            },
            navigationIcon = {
                AppBarIcon(onClick = onNavigationClick)
            },
            actions = {
                Box(
                    modifier = Modifier
                        .size(dimens.Space._4xl)
                        .padding(vertical = dimens.Space._2md)
                        .clip(KarigojobsShapes.medium)
                        .background(
                            color = if (canSave) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable(enabled = canSave, onClick = onAction),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (canSave) MaterialTheme.colorScheme.primary else NavInactive
                        ),
                        modifier = Modifier.padding(
                            horizontal = dimens.Padding.sm,
                            vertical = dimens.Padding.xs
                        )
                    )
                }
            },
            windowInsets = WindowInsets(),
        )
        HorizontalDivider()
    }
}