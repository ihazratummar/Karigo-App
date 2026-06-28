package com.karigojobs.domain.usecase.trade

import com.karigojobs.datastore.store.OnboardingStore
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 27/06/26
 */

class SaveTradesUseCase(
    private val onboardingStore: OnboardingStore
) {

    suspend operator fun invoke(trades: Set<TradeType>) {
        onboardingStore.saveTrades(trades = trades)
    }

}