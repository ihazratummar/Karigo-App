package com.karigojobs.domain.usecase.client

import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class InsertClientUseCase(
    private val clientRepository: ClientRepository
) {

    suspend operator fun invoke(clientModel: ClientModel) {
        clientRepository.insertClient(clientModel = clientModel)
    }
}