package com.karigojobs.presentation.backup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.backup.GetAutoBackupStatusUseCase
import com.karigojobs.domain.usecase.backup.GetLastBackupTimestampUseCase
import com.karigojobs.domain.usecase.backup.RestoreBackupUseCase
import com.karigojobs.domain.usecase.backup.SetAutoBackupStatusUseCase
import com.karigojobs.domain.usecase.backup.UploadBackupUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import com.karigojob.share.utils.formatToLocalizeDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataBackupViewModel(
    private val uploadBackupUseCase: UploadBackupUseCase,
    private val restoreBackupUseCase: RestoreBackupUseCase,
    private val getAutoBackupStatusUseCase: GetAutoBackupStatusUseCase,
    private val setAutoBackupStatusUseCase: SetAutoBackupStatusUseCase,
    private val getLastBackupTimestampUseCase: GetLastBackupTimestampUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DataBackupState())
    val state: StateFlow<DataBackupState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DataBackupEffect>()
    val effect: SharedFlow<DataBackupEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            getAutoBackupStatusUseCase().collectLatest { enabled ->
                _state.update { it.copy(isAutoBackupEnabled = enabled) }
            }
        }
    }

    fun onEvent(event: DataBackupEvent) {
        when (event) {
            is DataBackupEvent.OnAccountConnected -> {
                _state.update { it.copy(isConnected = true, email = event.email) }
                checkLastBackup(event.token)
            }
            is DataBackupEvent.DisconnectDrive -> {
                _state.update { it.copy(isConnected = false, email = null) }
            }
            is DataBackupEvent.ToggleAutoBackup -> {
                viewModelScope.launch {
                    setAutoBackupStatusUseCase(event.enabled)
                }
            }
            is DataBackupEvent.BackUpNow -> performBackup(event.token)
            is DataBackupEvent.RestoreBackup -> performRestore(event.token)
        }
    }

    private fun checkLastBackup(token: String) {
        viewModelScope.launch {
            val result = getLastBackupTimestampUseCase(token)
            val data = (result as? Result.Success)?.data
            if (data != null) {
                val formattedTime = data.formatToLocalizeDate()
                _state.update { it.copy(lastBackupTime = formattedTime) }
            } else {
                _state.update { it.copy(lastBackupTime = "No backup found") }
            }
        }
    }

    private fun performBackup(token: String) {
        viewModelScope.launch {
            _state.update { it.copy(isBackingUp = true) }
            val result = uploadBackupUseCase(token)
            when (result) {
                is Result.Success -> {
                    _state.update { it.copy(isBackingUp = false, lastBackupTime = "Just now") }
                    _effect.emit(DataBackupEffect.ShowToast("Backup successful!"))
                }
                is Result.Error -> {
                    _state.update { it.copy(isBackingUp = false) }
                    val msg = if (result.error is com.karigojobs.domain.result.BackupError.UnknownErrorWithMessage) {
                        "Backup failed: ${(result.error as com.karigojobs.domain.result.BackupError.UnknownErrorWithMessage).message}"
                    } else "Backup failed. Please try again."
                    _effect.emit(DataBackupEffect.ShowToast(msg))
                }
            }
        }
    }

    private fun performRestore(token: String) {
        viewModelScope.launch {
            _state.update { it.copy(isRestoring = true) }
            when (val result = restoreBackupUseCase(token)) {
                is Result.Success -> {
                    _state.update { it.copy(isRestoring = false) }
                    _effect.emit(DataBackupEffect.ShowToast("Restore complete! Check your data."))
                }
                is Result.Error -> {
                    _state.update { it.copy(isRestoring = false) }
                    val msg = if (result.error is com.karigojobs.domain.result.BackupError.UnknownErrorWithMessage) {
                        "Restore failed: ${(result.error as com.karigojobs.domain.result.BackupError.UnknownErrorWithMessage).message}"
                    } else "No backup found or restore failed."
                    _effect.emit(DataBackupEffect.ShowToast(msg))
                }
            }
        }
    }
}
