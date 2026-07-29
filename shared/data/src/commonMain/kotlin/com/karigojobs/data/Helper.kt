package com.karigojobs.data

import com.karigojob.share.utils.AppLogger
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.RootError

suspend inline fun <T, E: RootError> safeCall(
    error: E,
    crossinline block : suspend () -> T
) : Result<T, E> {
    return try {
        Result.Success(block())
    } catch (e: Exception){
        AppLogger.e("safeCall caught error: $error", e)
        Result.Error(error)
    }
}