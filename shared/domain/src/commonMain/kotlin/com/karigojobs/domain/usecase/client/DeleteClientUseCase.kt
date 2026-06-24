package com.karigojobs.domain.usecase.client

import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class DeleteClientUseCase(
    private val clientRepository: ClientRepository
) {

    suspend operator fun invoke(clientId: String) : Result<Unit, ClientError> {
        return clientRepository.deleteClient(id = clientId)
    }

}