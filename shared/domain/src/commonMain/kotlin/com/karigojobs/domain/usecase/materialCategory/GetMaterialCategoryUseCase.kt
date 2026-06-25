package com.karigojobs.domain.usecase.materialCategory

import com.karigojobs.domain.repository.MaterialCategoryRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 25/06/26
 */

class GetMaterialCategoryUseCase (
    private val materialCategoryRepository: MaterialCategoryRepository
) {

    operator fun invoke(tradeType: TradeType?) : Flow<Result<List<MaterialCategoryModel>, MaterialError>> {
        return materialCategoryRepository.getCategoryByTrade(tradeType = tradeType)
    }
}