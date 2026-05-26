package com.karigojobs.data

import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.RootError


/**
 * @author hazratummar
 * Created on 25/05/26
 */



suspend inline fun <T, E: RootError> safeCall(
    error: E,
    crossinline  block : suspend () -> T
) : Result<T, E> {
    return try {
        Result.Success(block())
    }catch (_: Exception){
        Result.Error(error)
    }
}