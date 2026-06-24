package com.karigojobs.domain.repository

import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.ClientModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 24/05/26
 */

interface ClientRepository {

    fun getAllClients(): Flow<Result<List<ClientModel>, ClientError>>
    fun getClient(clientId: String) : Flow<Result<ClientModel?, ClientError>>
    suspend fun getClientById(id: String): Result<ClientModel?, ClientError>
    suspend fun getClientByMobile(mobile: String): Result<ClientModel?, ClientError>
    fun searchClients(query: String): Flow<Result<List<ClientModel>, ClientError>>
    suspend fun insertClient(clientModel: ClientModel) : Result<Unit, ClientError>
    suspend fun updateClient(clientModel: ClientModel) : Result<Unit, ClientError>
    suspend fun deleteClient(id: String) : Result<Unit, ClientError>

}