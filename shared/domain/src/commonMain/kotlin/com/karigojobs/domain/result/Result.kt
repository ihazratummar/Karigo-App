package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 24/05/26
 */


interface RootError

sealed interface Result<out D, out E> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E>(val error: E) : Result<Nothing, E>
}