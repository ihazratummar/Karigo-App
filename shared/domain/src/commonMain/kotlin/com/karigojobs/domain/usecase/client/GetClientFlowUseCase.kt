package com.karigojobs.domain.usecase.client

import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.ClientModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 23/06/26
 */

class GetClientFlowUseCase (
    private val clientRepository: ClientRepository
) {


    operator fun invoke(clientId: String) : Flow<Result<ClientModel?, ClientError>> {
        return clientRepository.getClient(clientId = clientId)
    }

}