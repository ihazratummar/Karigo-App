package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 05/06/26
 */

sealed interface SiteEstimateError : RootError {

    data object DatabaseError : SiteEstimateError

}