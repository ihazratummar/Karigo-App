package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.MaterialError


/**
 * @author hazratummar
 * Created on 26/05/26
 */
 

fun MaterialError.asString(): String {
    return when(this){
        MaterialError.DatabaseError -> "Database error while processing materials. Please try again."
        MaterialError.FailedUpdate -> "Failed to update material details. Please try again."
        MaterialError.UnknownError -> "An unexpected error occurred while managing materials."
    }
}