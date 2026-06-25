package com.karigojobs.domain.usecase.material

import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 04/06/26
 */

class SearchMaterialsUseCase(
    private val materialRepository: MaterialRepository
) {

    operator fun invoke(
        query: String = "",
        tradeTypes: Set<TradeType>? = null,
        categoryId: String ? = null
    ): Flow<Result<List<MaterialsModel>, MaterialError>> {
        return materialRepository.searchMaterials(query = query, tradeTypes = tradeTypes, categoryId = categoryId)
    }

}