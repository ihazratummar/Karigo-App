package com.karigojobs.feature.settings.backup

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.karigojobs.app.android.ui.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.karigojobs.presentation.backup.DataBackupEffect
import com.karigojobs.presentation.backup.DataBackupEvent
import com.karigojobs.presentation.backup.DataBackupViewModel
import com.karigojobs.ui.common.KarigoTopAppBar
import com.karigojobs.ui.theme.KarigojobsShapes
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import com.karigojobs.ui.theme.Primary
import com.karigojobs.ui.theme.Error
import com.karigojobs.ui.theme.OnPrimary
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBackupScreen(
    viewModel: DataBackupViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var showBackupDialog by remember { mutableStateOf(false) }
    var showRestoreDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        val account = GoogleDriveAuth.getSignedInAccount(context)
        if (account != null && account.email != null) {
            val token = GoogleDriveAuth.getAccessToken(context, account)
            if (token != null) {
                viewModel.onEvent(DataBackupEvent.OnAccountConnected(account.email!!, token))
            }
        }

        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is DataBackupEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val account = GoogleDriveAuth.getAccountFromIntent(result.data)
            if (account != null && account.email != null) {
                coroutineScope.launch {
                    val token = GoogleDriveAuth.getAccessToken(context, account)
                    if (token != null) {
                        viewModel.onEvent(DataBackupEvent.OnAccountConnected(account.email!!, token))
                    } else {
                        Toast.makeText(context, "Sign-in failed: Unable to get access token", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, "Sign-in failed: Account info missing", Toast.LENGTH_LONG).show()
            }
        } else {
            try {
                GoogleSignIn.getSignedInAccountFromIntent(result.data).getResult(ApiException::class.java)
                Toast.makeText(context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                val errorCode = e.statusCode
                val errorMessage = com.google.android.gms.common.api.CommonStatusCodes.getStatusCodeString(errorCode)
                Toast.makeText(context, "Google Sign-In Error: $errorMessage ($errorCode)", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Sign-in failed/cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (showBackupDialog) {
        AlertDialog(
            onDismissRequest = { showBackupDialog = false },
            title = { Text("Confirm Backup", fontWeight = FontWeight.Bold, color = appColor.primaryText) },
            text = { Text("This will securely upload your current device data and settings to Google Drive, overwriting any previous backup. Proceed?", color = appColor.secondaryText) },
            confirmButton = {
                Button(
                    onClick = {
                        showBackupDialog = false
                        coroutineScope.launch {
                            val account = GoogleDriveAuth.getSignedInAccount(context)
                            val token = account?.let { GoogleDriveAuth.getAccessToken(context, it) }
                            if (token != null) {
                                viewModel.onEvent(DataBackupEvent.BackUpNow(token))
                            } else {
                                Toast.makeText(context, "Authentication error. Try reconnecting.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Back Up", color = OnPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBackupDialog = false }) {
                    Text("Cancel", color = appColor.secondaryText)
                }
            },
            containerColor = appColor.background
        )
    }

    if (showRestoreDialog) {
        AlertDialog(
            onDismissRequest = { showRestoreDialog = false },
            title = { Text("Confirm Restore", fontWeight = FontWeight.Bold, color = Error) },
            text = { Text("Warning: Restoring will overwrite ALL current device data and settings with the data from your Google Drive backup. This action cannot be undone. Are you sure you want to proceed?", color = appColor.secondaryText) },
            confirmButton = {
                Button(
                    onClick = {
                        showRestoreDialog = false
                        coroutineScope.launch {
                            val account = GoogleDriveAuth.getSignedInAccount(context)
                            val token = account?.let { GoogleDriveAuth.getAccessToken(context, it) }
                            if (token != null) {
                                viewModel.onEvent(DataBackupEvent.RestoreBackup(token))
                            } else {
                                Toast.makeText(context, "Authentication error. Try reconnecting.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Error)
                ) {
                    Text("Restore Data", color = OnPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRestoreDialog = false }) {
                    Text("Cancel", color = appColor.secondaryText)
                }
            },
            containerColor = appColor.background
        )
    }

    Scaffold(
        topBar = {
            KarigoTopAppBar(
                onNavigationClick = onBack,
                title = "Cloud Sync & Backup"
            )
        },
        containerColor = appColor.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(dimens.Padding.base),
            verticalArrangement = Arrangement.spacedBy(dimens.Space.lg)
        ) {
            
            // Header Image/Icon 
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = dimens.Padding.lg),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(dimens.Avatar._2xl)
                        .background(Primary.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.server),
                        contentDescription = "Cloud Backup",
                        tint = Primary,
                        modifier = Modifier.size(dimens.Icon.xl)
                    )
                }
            }

            // Connection Card
            Card(
                colors = CardDefaults.cardColors(containerColor = appColor.cardColors),
                shape = KarigojobsShapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = dimens.Elevation.sm),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(dimens.Padding.base)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(dimens.Avatar.md)
                                .background(if (state.isConnected) Primary.copy(alpha=0.1f) else Error.copy(alpha=0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("G", color = if (state.isConnected) Primary else Error, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.width(dimens.Space.base))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (state.isConnected) "Google Drive Connected" else "Not Connected",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = appColor.primaryText
                                )
                            )
                            if (state.email != null) {
                                Text(
                                    text = state.email!!,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = appColor.secondaryText
                                    )
                                )
                            }
                        }
                        // Status dot
                        Box(
                            modifier = Modifier
                                .size(dimens.Space.base)
                                .background(if (state.isConnected) Color(0xFF4CAF50) else Color(0xFF9E9E9E), CircleShape)
                        )
                    }

                    if (state.isConnected) {
                        Spacer(modifier = Modifier.height(dimens.Space.base))
                        HorizontalDivider(
                            Modifier,
                            thickness = dimens.Border.thin,
                            color = appColor.divider
                        )
                        Spacer(modifier = Modifier.height(dimens.Space.base))
                        Text(
                            text = if (state.lastBackupTime != null) "Latest Backup: ${state.lastBackupTime}" else "No backup found on Drive",
                            style = MaterialTheme.typography.labelMedium.copy(color = appColor.secondaryText)
                        )
                    }

                    Spacer(modifier = Modifier.height(dimens.Space.base))

                    if (state.isConnected) {
                        TextButton(
                            onClick = { 
                                GoogleDriveAuth.signOut(context) {
                                    viewModel.onEvent(DataBackupEvent.DisconnectDrive)
                                    BackupScheduler.cancelNightlyBackup(context)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Disconnect", color = Error, fontWeight = FontWeight.SemiBold)
                        }
                    } else {
                        Button(
                            onClick = { signInLauncher.launch(GoogleDriveAuth.getSignInIntent(context)) },
                            modifier = Modifier.fillMaxWidth().height(dimens.Height.buttonXl),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = appColor.accentBg, 
                                contentColor = Primary
                            ),
                            shape = KarigojobsShapes.medium
                        ) {
                            Text("Connect Google Drive", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            if (state.isConnected) {
                // Auto-Backup Row (Glassmorphic style)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appColor.cardColors, KarigojobsShapes.medium)
                        .padding(dimens.Padding.base),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Automated Backups", 
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = appColor.primaryText
                            )
                        )
                        Text(
                            text = if (state.isAutoBackupEnabled) "Scheduled nightly over Wi-Fi" else "Turn on to keep data safe", 
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = appColor.secondaryText
                            )
                        )
                    }
                    Switch(
                        checked = state.isAutoBackupEnabled,
                        onCheckedChange = { 
                            viewModel.onEvent(DataBackupEvent.ToggleAutoBackup(it))
                            if (it) {
                                BackupScheduler.scheduleNightlyBackup(context)
                            } else {
                                BackupScheduler.cancelNightlyBackup(context)
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = OnPrimary,
                            checkedTrackColor = Primary,
                            uncheckedThumbColor = appColor.secondaryText,
                            uncheckedTrackColor = appColor.divider
                        )
                    )
                }

                // Main Action Buttons
                Column(verticalArrangement = Arrangement.spacedBy(dimens.Space.base)) {
                    Button(
                        onClick = { showBackupDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimens.Height.button2Xl),
                        enabled = !state.isBackingUp && !state.isRestoring,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = KarigojobsShapes.medium
                    ) {
                        if (state.isBackingUp) {
                            CircularProgressIndicator(
                                color = OnPrimary, 
                                modifier = Modifier.size(dimens.Icon.base), 
                                strokeWidth = dimens.Border.thick
                            )
                            Spacer(modifier = Modifier.width(dimens.Space.sm))
                            Text("Uploading securely...", color = OnPrimary, fontWeight = FontWeight.SemiBold)
                        } else {
                            Text("Back Up Now", fontWeight = FontWeight.Bold, color = OnPrimary)
                        }
                    }

                    OutlinedButton(
                        onClick = { showRestoreDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimens.Height.button2Xl),
                        enabled = !state.isBackingUp && !state.isRestoring,
                        shape = KarigojobsShapes.medium,
                        border = BorderStroke(dimens.Border.thin, Primary.copy(alpha=0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                    ) {
                        if (state.isRestoring) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(dimens.Icon.base), 
                                strokeWidth = dimens.Border.thick,
                                color = Primary
                            )
                            Spacer(modifier = Modifier.width(dimens.Space.sm))
                            Text("Restoring Data...")
                        } else {
                            Text("Restore from Drive", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimens.Space.base))
            
            // Premium Explainer Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary.copy(alpha=0.05f), KarigojobsShapes.medium)
                    .padding(dimens.Padding.lg),
                verticalArrangement = Arrangement.spacedBy(dimens.Space.md)
            ) {
                Text(
                    text = "Peace of Mind", 
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                )
                
                Text(
                    text = "Your business data including jobs, clients, materials, and app preferences are securely encrypted and stored in a private, hidden folder on your Google Drive. We cannot see your other files.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = appColor.secondaryText,
                        lineHeight = dimens.Text.lg
                    )
                )
            }
            Spacer(modifier = Modifier.height(dimens.Space._3xl))
        }
    }
}
