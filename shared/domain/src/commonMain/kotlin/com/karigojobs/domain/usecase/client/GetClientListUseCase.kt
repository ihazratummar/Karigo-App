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

class GetClientListUseCase (
    private val clientRepository: ClientRepository
) {

    operator fun invoke (query : String) : Flow<Result<List<ClientModel>, ClientError>> {
        return clientRepository.searchClients(query = query)
    }

}