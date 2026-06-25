package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 24/06/26
 */

sealed interface WorkerProfileError : RootError {

    data object DatabaseError: WorkerProfileError
    data object NotFound : WorkerProfileError
    data object FailedToInsert : WorkerProfileError
    data object FailedToUpdate : WorkerProfileError
    data object UnknownError : WorkerProfileError

}