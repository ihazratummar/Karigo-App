package com.karigojobs.presentation.backup

data class DataBackupState(
    val isConnected: Boolean = false,
    val email: String? = null,
    val isAutoBackupEnabled: Boolean = false,
    val lastBackupTime: String? = null,
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false
)
