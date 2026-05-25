package com.karigojobs.domain.result


/**
 * @author hazratummar
 * Created on 25/05/26
 */

sealed interface JobError : RootError {

    data object NotFound : JobError

    data object SaveFailed : JobError

    data object UpdateFailed : JobError

    data object DeleteFailed : JobError

    data object Database : JobError

    data object Unknown : JobError
}