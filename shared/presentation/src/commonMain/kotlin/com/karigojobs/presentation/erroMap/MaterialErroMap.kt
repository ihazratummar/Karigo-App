package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.MaterialError


/**
 * @author hazratummar
 * Created on 26/05/26
 */
 

fun MaterialError.asString(): String {
    return when(this){
        MaterialError.DatabaseError -> "Operation failed due to database error."
        MaterialError.FailedUpdate -> "Update failed"
        MaterialError.UnknownError -> "An unknown error occurred."
    }
}