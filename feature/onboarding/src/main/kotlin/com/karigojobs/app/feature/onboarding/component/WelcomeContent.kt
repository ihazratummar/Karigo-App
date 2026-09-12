package com.karigojobs.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.karigojobs.app.android.ui.R
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_btn_get_started
import karigojobs.shared.resources.generated.resources.onboarding_started_description
import karigojobs.shared.resources.generated.resources.onboarding_started_earnings
import karigojobs.shared.resources.generated.resources.onboarding_started_earnings_description
import karigojobs.shared.resources.generated.resources.onboarding_started_track_description
import karigojobs.shared.resources.generated.resources.onboarding_started_track_job
import karigojobs.shared.resources.generated.resources.onboarding_started_whatsapp
import karigojobs.shared.resources.generated.resources.onboarding_started_whatsapp_description
import org.jetbrains.compose.resources.stringResource


/**
 * @author hazratummar
 * Created on 21/05/26
 */

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit = {},
    onBackToLanguageClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        if (onBackToLanguageClick != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .padding(horizontal = dimens.Space.base, vertical = dimens.Space.sm)
            ) {
                IconButton(onClick = onBackToLanguageClick) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(dimens.Icon.base)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = dimens.Space.xl)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        Box(
            modifier = Modifier
                .size(dimens.Icon._6xl)
                .clip(KarigojobsShapes.extraLarge)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.mechanic),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimens.Icon.lg)
            )
        }

        Spacer(Modifier.height(dimens.Space.lg))

        Text(
            text = "Karigojobs",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
        Spacer(Modifier.height(dimens.Space.base))
        Text(
            text = stringResource(Res.string.onboarding_started_description),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(value = 1.3f, type = TextUnitType.Em),
            )
        )

        Spacer(Modifier.height(dimens.Space.xl))

        val welcomeInfoData = listOf(
            WelcomeScreenCardData(
                icon = R.drawable.job_line,
                title = stringResource(Res.string.onboarding_started_track_job),
                description = stringResource(Res.string.onboarding_started_track_description),
                iconColor = Color(0xFF5CFAFF)
            ),
            WelcomeScreenCardData(
                icon = R.drawable.whatsapp,
                title = stringResource(Res.string.onboarding_started_whatsapp),
                description = stringResource(Res.string.onboarding_started_whatsapp_description),
                iconColor = Color(0xFF25D366)
            ),
            WelcomeScreenCardData(
                icon = R.drawable.chart_line,
                title = stringResource(Res.string.onboarding_started_earnings),
                description = stringResource(Res.string.onboarding_started_earnings_description),
                iconColor = Color(0xFFA089FF)
            )
        )

        welcomeInfoData.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.Space.xs),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = KarigojobsShapes.large,
                border = BorderStroke(
                    width = dimens.Space._2xs / 1.1f,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.1f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimens.Space.lg),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon._2xl * 1.3f)
                            .clip(KarigojobsShapes.large)
                            .background(color = it.iconColor.copy(0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = null,
                            modifier = Modifier.size(dimens.Icon.sm),
                            tint = it.iconColor
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                    ) {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = it.description,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(dimens.Space.xl))

        Button(
            onClick = onGetStartedClick,
            modifier = Modifier.fillMaxWidth(),
            shape = KarigojobsShapes.medium
        ) {
            Text(
                text = stringResource(Res.string.common_btn_get_started),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(vertical = dimens.Space.md)
            )
        }

        }
    }
}


private data class WelcomeScreenCardData(
    val icon: Int,
    val title: String,
    val description: String,
    val iconColor: Color
)
