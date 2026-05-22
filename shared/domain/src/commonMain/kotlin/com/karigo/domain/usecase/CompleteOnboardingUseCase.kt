package com.karigo.domain.usecase

import com.karigo.datastore.store.OnboardingStore
import com.karigo.share.model.TradeType


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