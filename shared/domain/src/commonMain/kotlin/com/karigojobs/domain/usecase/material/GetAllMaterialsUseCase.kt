package com.karigojobs.domain.usecase.material

import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialsModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 26/05/26
 */

class GetAllMaterialsUseCase(
    private val materialRepository: MaterialRepository
) {

    operator fun invoke () : Flow<Result<List<MaterialsModel>, MaterialError>> {
        return materialRepository.getAllMaterials()
    }
}