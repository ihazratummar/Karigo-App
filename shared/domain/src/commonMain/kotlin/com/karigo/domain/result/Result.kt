package com.karigo.domain.result


/**
 * @author hazratummar
 * Created on 24/05/26
 */


interface RootError

sealed interface Result <out D, out E: RootError> {

    data class Success<out D, out  E: RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E: RootError>(val error: E) : Result<D, E>
}