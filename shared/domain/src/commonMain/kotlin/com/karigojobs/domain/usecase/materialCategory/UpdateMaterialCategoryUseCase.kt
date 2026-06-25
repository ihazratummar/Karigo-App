package com.karigojobs.domain.usecase.materialCategory

import com.karigojobs.domain.repository.MaterialCategoryRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.MaterialCategoryModel

class UpdateMaterialCategoryUseCase(
    private val repository: MaterialCategoryRepository
) {
    suspend operator fun invoke(category: MaterialCategoryModel): Result<Unit, MaterialError> {
        return repository.updateCategory(category)
    }
}
