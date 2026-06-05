package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.ClientError


/**
 * @author hazratummar
 * Created on 26/05/26
 */





fun ClientError.asString() : String {
    return when(this){
        ClientError.Database -> "Database error. Please try again later."
        ClientError.FailedToDelete -> "Failed to delete the client."
        ClientError.FailedToInsert -> "Failed to save the client."
        ClientError.FailedToUpdate -> "Failed to update client information."
        ClientError.UnknownError -> "An unexpected error occurred."
    }
}