package com.karigojobs.app.feature.job.component

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.TopBarTitle
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.NavInactive
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 23/05/26
 */







@Composable
fun CanSaveButton(
    onAction: () -> Unit,
    canSave: Boolean,
    text: String = "Save"
){
    Box(
        modifier = Modifier
            .padding(vertical = dimens.Space._2md)
            .clip(KarigojobsShapes.medium)
            .background(
                color = if (canSave) MaterialTheme.colorScheme.primaryContainer else appColor.cardColors
            )
            .clickable(enabled = canSave, onClick = onAction),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (canSave) MaterialTheme.colorScheme.primary else NavInactive
            ),
            modifier = Modifier.padding(
                horizontal = dimens.Padding.sm,
                vertical = dimens.Padding.xs
            )
        )
    }
}