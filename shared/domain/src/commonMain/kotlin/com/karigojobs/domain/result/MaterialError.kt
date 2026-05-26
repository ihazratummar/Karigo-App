package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 26/05/26
 */

sealed interface MaterialError : RootError {

    data object DatabaseError: MaterialError
    data object FailedUpdate : MaterialError
    data object UnknownError : MaterialError
}