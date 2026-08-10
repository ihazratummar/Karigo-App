package com.karigojobs.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.karigojobs.app.android.ui.R
import com.karigojobs.share.model.ProStatus
import com.karigojobs.ui.theme.KarigojobsAccent
import com.karigojobs.ui.theme.KarigojobsCard
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsRaised
import com.karigojobs.ui.theme.KarigojobsText
import com.karigojobs.ui.theme.dimens

/**
 * Reusable Pro Lock Badge to indicate premium features
 */
@Composable
fun ProLockBadge(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.Radius.xs))
            .background(KarigojobsRaised)
            .padding(horizontal = dimens.Padding.xs, vertical = dimens.Padding._2xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
    ) {
        Icon(
            painter = painterResource(R.drawable.crown_fill),
            contentDescription = "Pro Feature",
            tint = KarigojobsIconColor,
            modifier = Modifier.size(dimens.Icon._2xs)
        )
        Text(
            text = "PRO",
            style = MaterialTheme.typography.labelSmall,
            color = KarigojobsAccent
        )
    }
}

/**
 * Reusable Composable Wrapper for Pro Features.
 * When isPro is false, wraps content and intercepts clicks with onLockedClick (opens Paywall).
 */
@Composable
fun ProFeatureGuard(
    proStatus: ProStatus,
    onLockedClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBadge: Boolean = true,
    content: @Composable (isLocked: Boolean) -> Unit
) {
    val isLocked = !proStatus.hasProAccess

    Box(
        modifier = modifier
    ) {
        content(isLocked)

        if (isLocked && showBadge) {
            ProLockBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimens.Padding.xs)
            )
        }

        if (isLocked) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(dimens.Radius.sm))
                    .background(KarigojobsCard.copy(alpha = 0.35f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onLockedClick()
                    }
            )
        }
    }
}

/**
 * Reusable Click Modifier to Guard Pro Actions.
 * If user is Pro or Preview Mode is enabled, invokes onClick.
 * If user is Free, intercepts click and invokes onLockedClick (triggers Paywall).
 */
fun Modifier.proClickGuard(
    proStatus: ProStatus,
    onLockedClick: () -> Unit,
    onClick: () -> Unit
): Modifier {
    return this.clickable {
        if (proStatus.hasProAccess) {
            onClick()
        } else {
            onLockedClick()
        }
    }
}
