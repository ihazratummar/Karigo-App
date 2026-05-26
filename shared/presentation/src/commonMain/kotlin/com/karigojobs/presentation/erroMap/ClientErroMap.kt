package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.ClientError


/**
 * @author hazratummar
 * Created on 26/05/26
 */





fun ClientError.asString() : String {
    return when(this){
        ClientError.Database -> ""
        ClientError.FailedToDelete -> ""
        ClientError.FailedToInsert -> ""
        ClientError.FailedToUpdate -> ""
        ClientError.UnknownError -> ""

    }
}