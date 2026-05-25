package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.JobError


/**
 * @author hazratummar
 * Created on 25/05/26
 */



fun JobError.asString(): String {
    return when (this) {
        JobError.Database ->
            "Something went wrong while accessing data."

        JobError.DeleteFailed ->
            "Failed to delete. Please try again."

        JobError.NotFound ->
            "The requested job could not be found."

        JobError.SaveFailed ->
            "Failed to save the job. Please try again."

        JobError.Unknown ->
            "Something unexpected happened."

        JobError.UpdateFailed ->
            "Failed to update the job."
    }
}