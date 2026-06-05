package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.HomeError


/**
 * @author hazratummar
 * Created on 05/06/26
 */


fun HomeError.asString() : String {
    return when(this){
        HomeError.TRADE_LOAD_ERROR -> "Failed to load trade categories. Please try again."
        HomeError.UNKNOWN_ERROR -> "An unexpected error occurred. Please try again later."
        HomeError.FAILED_TO_LOAD_JOBS -> "We couldn't load the jobs at the moment."
    }
}