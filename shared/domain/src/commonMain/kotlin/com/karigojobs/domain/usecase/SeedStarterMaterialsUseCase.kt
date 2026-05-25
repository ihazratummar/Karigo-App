package com.karigojobs.domain.usecase

import com.karigojobs.domain.repository.MaterialRepository
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


    suspend operator fun invoke(trade: Set<TradeType>) : Int {
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
        materialRepository.insertMaterial(materials)
        return materials.size
    }

}