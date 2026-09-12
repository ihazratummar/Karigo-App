package com.karigojobs.app.feature.onboarding.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.onboarding.OnboardingIntent
import com.karigojobs.presentation.onboarding.OnboardingState
import com.karigojobs.presentation.onboarding.OnboardingStep
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.common.bounceClickable
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsThemePreview
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_btn_continue
import karigojobs.shared.resources.generated.resources.onboarding_language_subtitle
import karigojobs.shared.resources.generated.resources.onboarding_language_title
import org.jetbrains.compose.resources.stringResource

/**
 * @author hazratummar
 * Created on 07/09/26
 */

@Composable
fun LanguageSelectContent(
    modifier: Modifier = Modifier,
    onboardingState: OnboardingState = OnboardingState(),
    event: (OnboardingIntent) -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            Button(
                onClick = { event(OnboardingIntent.ConfirmLanguage) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.Space.base)
                    .padding(bottom = dimens.Space.base),
                shape = KarigojobsShapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(Res.string.common_btn_continue),
                    modifier = Modifier.padding(vertical = dimens.Space.xs),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(dimens.Space.lg),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimens.Space.sm, bottom = dimens.Space.sm)
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimens.Icon._4xl)
                            .clip(KarigojobsShapes.extraLarge)
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_translate),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(dimens.Icon.base)
                        )
                    }

                    Spacer(Modifier.height(dimens.Space.md))

                    Text(
                        text = stringResource(Res.string.onboarding_language_title),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(Modifier.height(dimens.Space.xs))

                    Text(
                        text = stringResource(Res.string.onboarding_language_subtitle),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            items(
                items = AppLanguage.entries.chunked(2)
            ) { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.md)
                ) {
                    pair.forEach { language ->
                        LanguageCard(
                            modifier = Modifier.weight(1f),
                            language = language,
                            isSelected = onboardingState.selectedLanguage == language,
                            onSelect = { event(OnboardingIntent.SelectLanguage(language)) }
                        )
                    }
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Spacer(Modifier.height(dimens.Space.lg))
            }
        }
    }
}

@Composable
private fun LanguageCard(
    modifier: Modifier = Modifier,
    language: AppLanguage,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val cardBackground = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val cardBorder = if (isSelected) {
        BorderStroke(dimens.Border.thick, MaterialTheme.colorScheme.primary)
    } else {
        BorderStroke(
            dimens.Border.thin,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        )
    }

    Card(
        modifier = modifier
            .bounceClickable(onClick = onSelect),
        shape = KarigojobsShapes.large,
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        border = cardBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.Space.base),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.Space._2xs)
            ) {
                Text(
                    text = language.nativeName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = language.displayName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Box(
                modifier = Modifier
                    .size(dimens.Icon.base)
                    .clip(CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.background(MaterialTheme.colorScheme.primary)
                        } else {
                            Modifier.border(
                                dimens.Border.thin,
                                MaterialTheme.colorScheme.outlineVariant,
                                CircleShape
                            )
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        painter = painterResource(R.drawable.check),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(dimens.Icon.xs)
                    )
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LanguageSelectContentPreview() {
    KarigojobsThemePreview {
        LanguageSelectContent(
            onboardingState = OnboardingState(
                currentStep = OnboardingStep.LANGUAGE,
                selectedLanguage = AppLanguage.HINDI
            )
        )
    }
}
