package com.karigojobs.domain.usecase.material

import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialsModel


/**
 * @author hazratummar
 * Created on 05/06/26
 */

class AddMaterialUseCase(
    private val materialRepository: MaterialRepository
) {

    suspend operator fun invoke(material: MaterialsModel) : Result<Unit, MaterialError> {
        return materialRepository.insertMaterial(material = material)
    }

}