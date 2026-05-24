package com.karigo.domain.usecase

import com.karigo.datastore.store.OnboardingStore
import com.karigo.share.model.TradeType
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class GetSelectedTradeTypeUseCase(
    private val onboardingStore: OnboardingStore
) {

    operator fun invoke() : Flow<Set<TradeType>> {
        return onboardingStore.selectedTrades
    }

}