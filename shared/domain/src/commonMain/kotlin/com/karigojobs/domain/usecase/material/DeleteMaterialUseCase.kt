package com.karigojobs.domain.usecase.material

import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result


/**
 * @author hazratummar
 * Created on 04/06/26
 */

class DeleteMaterialUseCase(
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(materialId: String) : Result<Unit, MaterialError> {
        return materialRepository.deleteMaterial(id = materialId)
    }

}