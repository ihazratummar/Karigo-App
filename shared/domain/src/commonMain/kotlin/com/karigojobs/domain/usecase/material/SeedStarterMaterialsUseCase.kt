package com.karigojobs.domain.usecase.material

import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.result.MaterialError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.StarterMaterial
import com.karigojobs.share.model.TradeSeeds
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 22/05/26
 */

class SeedStarterMaterialsUseCase (
    private val materialRepository: MaterialRepository
) {


    suspend operator fun invoke(trade: Set<TradeType>) : Result<Int, MaterialError> {
        val materials = trade
            .flatMap { TradeSeeds.seeds[it].orEmpty() }
            .map { material ->
                StarterMaterial(
                    name = material.name,
                    unit = material.unit,
                    price = material.price,
                    tradeType = material.tradeType,
                )
            }
        val result = materialRepository.insertMaterial(materials)
        return when(result){
            is Result.Success -> {
                Result.Success(materials.size)
            }
            is Result.Error -> {
                Result.Error(MaterialError.DatabaseError)
            }
        }
    }

}