package com.karigo.domain.usecase

import com.karigo.domain.repository.ClientRepository
import com.karigo.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class IsClientExistUseCase(
    private val clientRepository: ClientRepository
) {


    suspend operator  fun invoke(phone: String): ClientModel?{
        return clientRepository.getClientByMobile(mobile = phone)
    }

}