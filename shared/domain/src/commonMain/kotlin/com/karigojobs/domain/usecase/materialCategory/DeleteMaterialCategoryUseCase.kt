package com.karigojobs.domain.usecase.materialCategory

import com.karigojobs.domain.repository.MaterialCategoryRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result

class DeleteMaterialCategoryUseCase(
    private val repository: MaterialCategoryRepository
) {
    suspend operator fun invoke(id: String): Result<Unit, MaterialError> {
        return repository.deleteCategory(id)
    }
}
