package com.karigojobs.domain.result

sealed interface BackupError : RootError {
    data object NetworkError : BackupError
    data object AuthError : BackupError
    data object FileSystemError : BackupError
    data object UnknownError : BackupError
    data class UnknownErrorWithMessage(val message: String) : BackupError
}
