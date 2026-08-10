package com.karigojobs.app.feature.job.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.karigojobs.share.model.JobLabourLogModel
import com.karigojobs.ui.theme.appColor
import com.karigojobs.ui.theme.dimens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import karigojobs.shared.resources.generated.resources.Res
import karigojobs.shared.resources.generated.resources.common_labour_history
import karigojobs.shared.resources.generated.resources.common_no_history
import karigojobs.shared.resources.generated.resources.common_added
import karigojobs.shared.resources.generated.resources.common_removed
import karigojobs.shared.resources.generated.resources.common_close
import karigojobs.shared.resources.generated.resources.common_workers
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.platform.LocalLocale
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabourLogsModal(
    logs: List<JobLabourLogModel>,
    itemUnit: String = "point",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f),
            shape = MaterialTheme.shapes.large,
            color = appColor.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimens.Padding.base)
            ) {
                Text(
                    text = stringResource(Res.string.common_labour_history),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = appColor.primaryText,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = dimens.Padding.sm)
                )

                if (logs.isEmpty()) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.common_no_history),
                            style = MaterialTheme.typography.bodyMedium.copy(color = appColor.secondaryText)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    ) {
                        items(logs.sortedByDescending { it.createdAt }) { log ->
                            val isAddition = log.changeAmount > 0
                            val formatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", LocalLocale.current.platformLocale)
                            val dateString = formatter.format(Date(log.createdAt))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimens.Padding.xs),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    val unitLabel = if (log.logType == "WORKERS") {
                                        stringResource(Res.string.common_workers)
                                    } else {
                                        itemUnit
                                    }
                                    val logText = if (isAddition) {
                                        "${stringResource(Res.string.common_added)} ${log.changeAmount} $unitLabel"
                                    } else {
                                        "${stringResource(Res.string.common_removed)} ${abs(log.changeAmount)} $unitLabel"
                                    }
                                    Text(
                                        text = logText,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = if (isAddition) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = dateString,
                                        style = MaterialTheme.typography.labelSmall.copy(color = appColor.secondaryText)
                                    )
                                }
                            }
                            HorizontalDivider(color = appColor.cardColors)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimens.Space.base))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(Res.string.common_close))
                }
            }
        }
    }
}
