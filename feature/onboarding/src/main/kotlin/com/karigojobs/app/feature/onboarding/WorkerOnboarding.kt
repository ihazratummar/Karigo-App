package com.karigojobs.app.feature.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import com.karigojobs.app.android.ui.R
import com.karigojobs.presentation.onboarding.WorkerProfileEffect
import com.karigojobs.presentation.onboarding.WorkerProfileEvent
import com.karigojobs.presentation.onboarding.WorkerProfileState
import com.karigojobs.presentation.onboarding.WorkerProfileStep
import com.karigojobs.ui.common.IconPlaceholder
import com.karigojobs.ui.common.KarigoIconWIthBgCick
import com.karigojobs.ui.common.KarigojobsTextField
import com.karigojobs.ui.common.customCardBorder
import com.karigojobs.ui.theme.KarigojobsIconColor
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.KarigojobsText2
import com.karigojobs.ui.theme.KarigojobsText3
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_btn_continue
import karigojobs.shared.resources.generated.resources.common_required
import karigojobs.shared.resources.generated.resources.worker_btn_finish
import karigojobs.shared.resources.generated.resources.worker_btn_skip_this
import karigojobs.shared.resources.generated.resources.worker_business
import karigojobs.shared.resources.generated.resources.worker_business_name
import karigojobs.shared.resources.generated.resources.worker_business_tagline
import karigojobs.shared.resources.generated.resources.worker_business_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_contact
import karigojobs.shared.resources.generated.resources.worker_contact_tagline
import karigojobs.shared.resources.generated.resources.worker_email_address
import karigojobs.shared.resources.generated.resources.worker_email_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_extra_business_address
import karigojobs.shared.resources.generated.resources.worker_extra_business_address_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_extra_gst
import karigojobs.shared.resources.generated.resources.worker_extra_gst_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_extra_info
import karigojobs.shared.resources.generated.resources.worker_extra_info_tagline
import karigojobs.shared.resources.generated.resources.worker_optional_details
import karigojobs.shared.resources.generated.resources.worker_optional_skip
import karigojobs.shared.resources.generated.resources.worker_owner_asking_name
import karigojobs.shared.resources.generated.resources.worker_owner_name_section
import karigojobs.shared.resources.generated.resources.worker_owner_name_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_owner_question
import karigojobs.shared.resources.generated.resources.worker_phone_number
import karigojobs.shared.resources.generated.resources.worker_phone_textfield_placeholder
import karigojobs.shared.resources.generated.resources.worker_progress_meter
import karigojobs.shared.resources.generated.resources.worker_required_fields
import karigojobs.shared.resources.generated.resources.worker_your_progress
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource

/**
 * Worker Onboarding wizard screen consisting of 4 steps to set up their profile.
 */
@Composable
fun WorkerOnboarding(
    modifier: Modifier = Modifier,
    state: WorkerProfileState,
    event: (WorkerProfileEvent) -> Unit,
    effect: SharedFlow<WorkerProfileEffect>?,
    onBackClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect?.collect { effect ->
            when (effect) {
                WorkerProfileEffect.NavBack -> {
                    onBackClick()
                }
                is WorkerProfileEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            OnboardingHeader(
                step = state.currentStep,
                onBack = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = dimens.Padding.base)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(dimens.Space.xl))

            // Body Step Views
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AnimatedContent(
                    targetState = state.currentStep,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "step_transition"
                ) { step ->
                    when (step) {
                        WorkerProfileStep.OWNER_NAME -> {
                            OwnerNameStep(
                                name = state.ownerName,
                                onNameChange = { event(WorkerProfileEvent.OwnerNameField(it)) }
                            )
                        }
                        WorkerProfileStep.BUSINESS_NAME -> {
                            BusinessNameStep(
                                businessName = state.businessName,
                                onBusinessNameChange = { event(WorkerProfileEvent.BusinessNameField(it)) }
                            )
                        }
                        WorkerProfileStep.CONTACT_DETAILS -> {
                            ContactDetailsStep(
                                phone = state.phoneNumber,
                                email = state.email,
                                onPhoneChange = { event(WorkerProfileEvent.PhoneNumberField(it)) },
                                onEmailChange = { event(WorkerProfileEvent.EmailField(it)) }
                            )
                        }
                        WorkerProfileStep.EXTRA_INFO -> {
                            ExtraInfoStep(
                                address = state.address,
                                onAddressChange = { event(WorkerProfileEvent.AddressField(it)) }
                            )
                        }
                    }
                }
            }

            // Bottom Navigation Card & Button Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.Padding.base),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.sm),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Your Progress Card on Steps 3 and 4
                if (state.currentStep == WorkerProfileStep.CONTACT_DETAILS || state.currentStep == WorkerProfileStep.EXTRA_INFO) {
                    ProgressCard(state = state)
                }

                HorizontalDivider(color = Color(0xFF1E1E1E), thickness = dimens.Divider.thickness)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.Space.sm)
                ) {
                    // Left navigation button (back or restart)
                    if (state.currentStep != WorkerProfileStep.OWNER_NAME) {
                        Box(
                            modifier = Modifier
                                .size(dimens.Icon._3xl)
                                .clip(KarigojobsShapes.medium)
                                .background(Color(0xFF1E1E1E))
                                .clickable {
                                    when (state.currentStep) {
                                        WorkerProfileStep.BUSINESS_NAME -> event(WorkerProfileEvent.BackToOwnerName)
                                        WorkerProfileStep.CONTACT_DETAILS -> event(WorkerProfileEvent.BackToBusiness)
                                        WorkerProfileStep.EXTRA_INFO -> event(WorkerProfileEvent.BackToContactDetails)
                                        else -> {}
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_left),
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(dimens.Icon.sm)
                            )
                        }
                    }

                    // Main Action Button (Continue / Finish Setup)
                    val isLastStep = state.currentStep == WorkerProfileStep.EXTRA_INFO
                    val isStepValid = when (state.currentStep) {
                        WorkerProfileStep.OWNER_NAME -> state.ownerName.isNotBlank()
                        WorkerProfileStep.BUSINESS_NAME -> state.businessName.isNotBlank()
                        else -> true
                    }

                    val buttonColor = if (isStepValid) KarigojobsIconColor else Color(0xFF1E1E1E)
                    val contentColor = if (isStepValid) Color.Black else KarigojobsText3

                    Button(
                        onClick = {
                            if (isStepValid) {
                                when (state.currentStep) {
                                    WorkerProfileStep.OWNER_NAME -> event(WorkerProfileEvent.OwnerNameComplete)
                                    WorkerProfileStep.BUSINESS_NAME -> event(WorkerProfileEvent.BusinessNameCompete)
                                    WorkerProfileStep.CONTACT_DETAILS -> event(WorkerProfileEvent.ContactDetailsComplete)
                                    WorkerProfileStep.EXTRA_INFO -> event(WorkerProfileEvent.ExtraInfoComplete)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f).height(dimens.Height.minTouch),
                        enabled = isStepValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = contentColor,
                            disabledContainerColor = Color(0xFF1E1E1E),
                            disabledContentColor = KarigojobsText3
                        ),
                        shape = KarigojobsShapes.medium
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
                        ) {
                            if (isLastStep) {
                                Icon(
                                    painter = painterResource(R.drawable.check),
                                    contentDescription = null,
                                    tint = contentColor,
                                    modifier = Modifier.size(dimens.Icon.xs)
                                )
                                Text(
                                    text = stringResource(Res.string.worker_btn_finish),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            } else {
                                Text(
                                    text = stringResource(Res.string.common_btn_continue),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Icon(
                                    painter = painterResource(R.drawable.arrow_right),
                                    contentDescription = null,
                                    tint = contentColor,
                                    modifier = Modifier.size(dimens.Icon.xs)
                                )
                            }
                        }
                    }
                }

                // Skip button for optional steps (Steps 3 & 4)
                if (state.currentStep == WorkerProfileStep.CONTACT_DETAILS || state.currentStep != WorkerProfileStep.EXTRA_INFO) {
                    TextButton(
                        onClick = {
                            when (state.currentStep) {
                                WorkerProfileStep.CONTACT_DETAILS -> event(WorkerProfileEvent.ContactDetailsComplete)
                                WorkerProfileStep.EXTRA_INFO -> event(WorkerProfileEvent.ExtraInfoComplete)
                                else -> {}
                            }
                        }
                    ) {
                        Text(
                            text = stringResource(Res.string.worker_btn_skip_this),
                            style = MaterialTheme.typography.labelLarge.copy(color = KarigojobsText2)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(dimens.Space.xl))
                }
            }
        }
    }
}

@Composable
private fun OnboardingHeader(
    step: WorkerProfileStep,
    onBack: () -> Unit
) {
    val (stepNumber, progress, percentage) = when (step) {
        WorkerProfileStep.OWNER_NAME -> Triple(stringResource(Res.string.worker_progress_meter, 1, 4), 0.25f, "25%")
        WorkerProfileStep.BUSINESS_NAME -> Triple(stringResource(Res.string.worker_progress_meter, 2, 4), 0.50f, "50%")
        WorkerProfileStep.CONTACT_DETAILS -> Triple(stringResource(Res.string.worker_progress_meter, 3, 4), 0.75f, "75%")
        WorkerProfileStep.EXTRA_INFO -> Triple(stringResource(Res.string.worker_progress_meter, 4, 4), 1.00f, "100%")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Squared back button inside dark card background
        KarigoIconWIthBgCick(
            icon =R.drawable.arrow_left,
            iconColor = appColor.primaryText,
            onClick = onBack
        )
        Spacer(modifier = Modifier.width(dimens.Space.base))

        Column(
            modifier = Modifier.padding(end = dimens.Padding.base).weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$stepNumber ",
                    style = MaterialTheme.typography.bodySmall.copy(color = appColor.secondaryText)
                )
                Text(
                    text = percentage,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = KarigojobsIconColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(dimens.Space.xs))

            // Horizon Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.Height.progressTrack)
                    .clip(CircleShape)
                    .background(Color(0xFF1E1E1E))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(KarigojobsIconColor, Color.Transparent)
                            )
                        )
                )
            }
        }
    }
}

@Composable
private fun OwnerNameStep(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.worker_owner_question),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )
        )
        Text(
            text = stringResource(Res.string.worker_owner_asking_name),
            style = MaterialTheme.typography.bodyLarge.copy(color = appColor.secondaryText)
        )

        Spacer(modifier = Modifier.height(dimens.Space._2xl))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
        ) {
            Text(
                text = stringResource(Res.string.worker_owner_name_section),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = appColor.secondaryText
                )
            )
            Text(
                text = stringResource(Res.string.common_required),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }

        Spacer(modifier = Modifier.height(dimens.Space.sm))

        KarigojobsTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = stringResource(Res.string.worker_owner_name_textfield_placeholder),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.user_line),
                    contentDescription = null,
                    tint = appColor.secondaryText,
                    modifier = Modifier.size(dimens.Icon.sm)
                )
            }
        )
    }
}

@Composable
private fun BusinessNameStep(
    businessName: String,
    onBusinessNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.worker_business),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )
        )
        Text(
            text = stringResource(Res.string.worker_business_tagline),
            style = MaterialTheme.typography.bodyLarge.copy(color = appColor.secondaryText)
        )

        Spacer(modifier = Modifier.height(dimens.Space._2xl))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
        ) {
            Text(
                text = stringResource(Res.string.worker_business_name),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = appColor.secondaryText
                )
            )
            Text(
                text = stringResource(Res.string.common_required),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            )
        }

        Spacer(modifier = Modifier.height(dimens.Space.sm))

        KarigojobsTextField(
            value = businessName,
            onValueChange = onBusinessNameChange,
            placeholder = stringResource(Res.string.worker_business_textfield_placeholder),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.store),
                    contentDescription = null,
                    tint = appColor.secondaryText,
                    modifier = Modifier.size(dimens.Icon.sm)
                )
            }
        )
    }
}

@Composable
private fun ContactDetailsStep(
    phone: String,
    email: String,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.worker_contact),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )
        )
        Text(
            text = stringResource(Res.string.worker_contact_tagline),
            style = MaterialTheme.typography.bodyLarge.copy(color = KarigojobsText2)
        )

        Spacer(modifier = Modifier.height(dimens.Space._2xl))

        Text(
            text = stringResource(Res.string.worker_phone_number),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(dimens.Space.sm))
        KarigojobsTextField(
            value = phone,
            onValueChange = onPhoneChange,
            placeholder = stringResource(Res.string.worker_phone_textfield_placeholder),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.phone),
                    contentDescription = null,
                    tint = appColor.secondaryText,
                    modifier = Modifier.size(dimens.Icon.sm)
                )
            }
        )
        Spacer(modifier = Modifier.height(dimens.Space.xs))
        Text(
            text = stringResource(Res.string.worker_optional_skip),
            style = MaterialTheme.typography.labelSmall.copy(color = appColor.tertiaryText)
        )

        Spacer(modifier = Modifier.height(dimens.Space.lg))

        Text(
            text = stringResource(Res.string.worker_email_address),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(dimens.Space.sm))
        KarigojobsTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = stringResource(Res.string.worker_email_textfield_placeholder),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.email),
                    contentDescription = null,
                    tint = appColor.secondaryText,
                    modifier = Modifier.size(dimens.Icon.sm)
                )
            }
        )
        Spacer(modifier = Modifier.height(dimens.Space.xs))
        Text(
            text = stringResource(Res.string.worker_optional_skip),
            style = MaterialTheme.typography.labelSmall.copy(color = appColor.tertiaryText)
        )
    }
}

@Composable
private fun ExtraInfoStep(
    address: String,
    onAddressChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(Res.string.worker_extra_info),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.primaryText
            )
        )
        Text(
            text = stringResource(Res.string.worker_extra_info_tagline),
            style = MaterialTheme.typography.bodyLarge.copy(color = appColor.secondaryText)
        )

        Spacer(modifier = Modifier.height(dimens.Space._2xl))

        Text(
            text = stringResource(Res.string.worker_extra_business_address),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = appColor.secondaryText
            )
        )
        Spacer(modifier = Modifier.height(dimens.Space.sm))
        KarigojobsTextField(
            value = address,
            onValueChange = onAddressChange,
            placeholder = stringResource(Res.string.worker_extra_business_address_textfield_placeholder),
            singleLine = false,
            minLines = 3,
            maxLines = 3,
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.map_point),
                    contentDescription = null,
                    tint = appColor.secondaryText,
                    modifier = Modifier.size(dimens.Icon.sm)
                )
            }
        )
        Spacer(modifier = Modifier.height(dimens.Space.xs))
        Text(
            text = stringResource(Res.string.worker_optional_skip),
            style = MaterialTheme.typography.labelSmall.copy(color = appColor.tertiaryText)
        )
    }
}

@Composable
private fun ProgressCard(state: WorkerProfileState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = appColor.cardColors
        ),
        shape = KarigojobsShapes.medium,
        border = customCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.sm)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.Space.xs)
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = null,
                    tint = Color(0xFF00E6C3),
                    modifier = Modifier.size(dimens.Icon.xs)
                )
                Text(
                    text = stringResource(Res.string.worker_your_progress),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = appColor.primaryText
                    )
                )
            }

            Spacer(modifier = Modifier.height(dimens.Space._2xs))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.worker_required_fields),
                    style = MaterialTheme.typography.labelMedium,
                    color = appColor.secondaryText
                )
                val requiredCount = (if (state.ownerName.isNotBlank()) 1 else 0) + (if (state.businessName.isNotBlank()) 1 else 0)
                Text(
                    text = "$requiredCount/2",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (requiredCount == 2) Color(0xFF00E6C3) else appColor.primaryText
                )
            }

            if (state.currentStep == WorkerProfileStep.EXTRA_INFO) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.worker_optional_details),
                        style = MaterialTheme.typography.labelMedium,
                        color = appColor.secondaryText
                    )
                    val optionalCount = (if (state.phoneNumber.isNotBlank()) 1 else 0) +
                            (if (state.email.isNotBlank()) 1 else 0) +
                            (if (state.address.isNotBlank()) 1 else 0)
                    Text(
                        text = "$optionalCount/3",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = if (optionalCount == 3) Color(0xFF00E6C3) else appColor.primaryText
                    )
                }
            }
        }
    }
}
