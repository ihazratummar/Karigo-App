package com.karigojobs.presentation.backup

sealed class DataBackupEffect {
    data class ShowToast(val message: String) : DataBackupEffect()
}
