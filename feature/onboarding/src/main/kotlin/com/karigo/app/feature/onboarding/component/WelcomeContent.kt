package com.karigo.app.feature.onboarding.component

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
import com.karigo.app.android.ui.R
import com.karigo.ui.theme.KarigoShapes
import com.karigo.ui.theme.KarigoThemePreview
import com.karigo.ui.theme.dimens


/**
 * @author hazratummar
 * Created on 21/05/26
 */

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WelcomeContent(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimens.Space.xl)
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(dimens.Icon._6xl)
                .clip(KarigoShapes.extraLarge)
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
            text = "Karigo",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
        Spacer(Modifier.height(dimens.Space.base))
        Text(
            text = """
                    The simplest way to manage jobs, clients, and 
                    earnings — built for Indian contractors, 
                    plumbers, electricians & more.
                """.trimIndent(),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(value = 1.3f, type = TextUnitType.Em),
            )
        )

        Spacer(Modifier.height(dimens.Space.xl))

        val welcomeInfoData = listOf<WelcomeScreenCardData>(
            WelcomeScreenCardData(
                icon = R.drawable.job_line,
                title = "Track every job",
                description = "Labour, materials & invoices in one place",
                iconColor = Color(0xFF5CFAFF)
            ),
            WelcomeScreenCardData(
                icon = R.drawable.whatsapp,
                title = "Share via WhatsApp",
                description = "Send invoices to clients instantly",
                iconColor = Color(0xFF25D366)
            ),
            WelcomeScreenCardData(
                icon = R.drawable.chart_line,
                title = "Know your earnings",
                description = "Weekly reports & payment trcaking",
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
                shape = KarigoShapes.large,
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
                            .clip(KarigoShapes.large)
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
            shape = KarigoShapes.medium
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(vertical = dimens.Space.md)
            )
        }

    }
}


private data class WelcomeScreenCardData(
    val icon: Int,
    val title: String,
    val description: String,
    val iconColor: Color
)
