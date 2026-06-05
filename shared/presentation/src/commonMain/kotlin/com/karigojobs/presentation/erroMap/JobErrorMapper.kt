package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.JobError


/**
 * @author hazratummar
 * Created on 25/05/26
 */



fun JobError.asString(): String {
    return when (this) {
        JobError.Database -> "Something went wrong while accessing data. Please try again."
        JobError.DeleteFailed -> "Failed to delete the job. Please try again."
        JobError.NotFound -> "The requested job could not be found."
        JobError.SaveFailed -> "Failed to save the job. Please check your input."
        JobError.Unknown -> "An unexpected error occurred. Please try again."
        JobError.UpdateFailed -> "Failed to update the job details."
    }
}