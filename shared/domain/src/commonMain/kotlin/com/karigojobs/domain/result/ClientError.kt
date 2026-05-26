package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 26/05/26
 */

sealed interface ClientError : RootError {

    data object Database : ClientError
    data object FailedToUpdate: ClientError
    data object FailedToInsert : ClientError
    data object FailedToDelete : ClientError
    data object UnknownError : ClientError

}