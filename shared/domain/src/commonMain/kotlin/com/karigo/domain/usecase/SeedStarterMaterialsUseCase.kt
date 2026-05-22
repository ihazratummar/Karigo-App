package com.karigo.domain.usecase

import com.karigo.domain.repository.MaterialRepository
import com.karigo.share.model.StarterMaterial
import com.karigo.share.model.TradeType


/**
 * @author hazratummar
 * Created on 22/05/26
 */

class SeedStarterMaterialsUseCase (
    private val materialRepository: MaterialRepository
) {


    suspend operator fun invoke(trade: Set<TradeType>) : Int {
        val materials = trade
            .flatMap { it.starterMaterial }
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