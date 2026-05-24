package com.karigo.domain.repository

import com.karigo.share.model.ClientModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 24/05/26
 */

interface ClientRepository {

    fun getAllClients(): Flow<List<ClientModel>>
    suspend fun getClientById(id: String): ClientModel?
    suspend fun getClientByMobile(mobile: String): ClientModel?
    fun searchClients(query: String): Flow<List<ClientModel>>
    suspend fun insertClient(clientModel: ClientModel)
    suspend fun updateClient(clientModel: ClientModel)
    suspend fun deleteClient(id: String)

}