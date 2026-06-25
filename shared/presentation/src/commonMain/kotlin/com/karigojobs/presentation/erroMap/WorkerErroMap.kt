package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.WorkerProfileError


/**
 * @author hazratummar
 * Created on 24/06/26
 */

fun WorkerProfileError.asString() : String {
    return when(this){
        WorkerProfileError.DatabaseError -> "Database error. Please try again later."
        WorkerProfileError.FailedToInsert -> "Failed to save the worker profile."
        WorkerProfileError.FailedToUpdate -> "Failed to update the worker profile."
        WorkerProfileError.NotFound -> "Worker profile not found."
        WorkerProfileError.UnknownError -> "An unexpected error occurred."
    }
}