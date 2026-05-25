package com.karigojobs.domain.usecase

import com.karigojobs.datastore.store.OnboardingStore
import com.karigojobs.domain.result.HomeError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class GetSelectedTradeTypeUseCase(
    private val onboardingStore: OnboardingStore,
    private val ioDispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<Result<Set<TradeType>, HomeError>> {
        return onboardingStore.selectedTrades
            .map { tradeTypes ->
                Result.Success(tradeTypes) as Result<Set<TradeType>, HomeError>
            }
            .catch {
                emit(Result.Error(HomeError.TRADE_LOAD_ERROR))
            }.flowOn(ioDispatcher)
    }

}