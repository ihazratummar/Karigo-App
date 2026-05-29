package com.karigojobs.domain.usecase.client

import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 29/05/26
 */

class GetClientUseCase (
    private val clientRepository: ClientRepository
) {


    suspend operator fun invoke(clientId: String) : Result<ClientModel? , ClientError> {
        return clientRepository.getClientById(id = clientId)
    }

}