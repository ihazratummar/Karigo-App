package com.karigo.domain.usecase

import com.karigo.datastore.store.OnboardingStore
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 21/05/26
 */

class GetOnboardingStatusUseCase(
    private val onboardingStore: OnboardingStore
) {

    operator fun invoke() : Flow<Boolean> {
        return onboardingStore.isComplete
    }

}