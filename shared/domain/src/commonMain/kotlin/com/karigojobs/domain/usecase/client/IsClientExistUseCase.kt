package com.karigojobs.domain.usecase.client

import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class IsClientExistUseCase(
    private val clientRepository: ClientRepository
) {


    suspend operator  fun invoke(phone: String): Result<ClientModel?, ClientError> {
        return clientRepository.getClientByMobile(mobile = phone)
    }

}