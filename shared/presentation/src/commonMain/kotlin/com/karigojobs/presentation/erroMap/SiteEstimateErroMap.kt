package com.karigojobs.presentation.erroMap

import com.karigojobs.domain.result.SiteEstimateError

fun SiteEstimateError.asString(): String {
    return when (this) {
        SiteEstimateError.DatabaseError -> "Database error while processing estimates. Please try again."
        SiteEstimateError.NotFound -> "Estimate not found."
    }
}
