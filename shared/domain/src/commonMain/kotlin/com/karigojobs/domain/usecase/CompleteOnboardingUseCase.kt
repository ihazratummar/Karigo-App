package com.karigojobs.domain.usecase

import com.karigojobs.datastore.store.OnboardingStore
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 21/05/26
 */

class CompleteOnboardingUseCase(
    private val onboardingStore: OnboardingStore
) {

    suspend operator fun invoke(trades: Set<TradeType>) {
        onboardingStore.markComplete(trades = trades)
    }

}