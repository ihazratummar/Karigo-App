package com.karigojobs.presentation.backup

sealed class DataBackupEvent {
    data class OnAccountConnected(val email: String, val token: String) : DataBackupEvent()
    object DisconnectDrive : DataBackupEvent()
    data class ToggleAutoBackup(val enabled: Boolean) : DataBackupEvent()
    data class BackUpNow(val token: String) : DataBackupEvent()
    data class RestoreBackup(val token: String) : DataBackupEvent()
}
