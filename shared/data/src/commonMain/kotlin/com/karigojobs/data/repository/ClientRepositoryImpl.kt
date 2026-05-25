package com.karigojobs.data.repository

import com.karigojobs.data.dto.toModel
import com.karigojobs.data.dto.toModelList
import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.share.model.ClientModel
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class ClientRepositoryImpl (
    private val database: KarigojobsDatabase
): ClientRepository  {


    override fun getAllClients(): Flow<List<ClientModel>> = flow<List<ClientModel>> {
        val clients = database.clientQueries.getgAallClients()
            .executeAsList()
            .toModelList()
        emit(clients)
    }.flowOn(Dispatchers.IO)

    override suspend fun getClientById(id: String): ClientModel? {
        return database.clientQueries.getClientById(id = id).executeAsOneOrNull()?.toModel()
    }

    override suspend fun getClientByMobile(mobile: String): ClientModel? {
        return database.clientQueries.getClientByPhone(phone = mobile).executeAsOneOrNull()?.toModel()
    }

    override fun searchClients(query: String): Flow<List<ClientModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertClient(clientModel: ClientModel) {
        database.clientQueries.insertClient(
            id = UuidGenerator.generate(),
            name = clientModel.name,
            phone = clientModel.phone,
            email = clientModel.email,
            address =clientModel.address,
            total_jobs =clientModel.totalJob.toLong(),
            created_at = EpochUtils.now(),
            updated_at = EpochUtils.now()
        )
    }

    override suspend fun updateClient(clientModel: ClientModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteClient(id: String) {
        TODO("Not yet implemented")
    }
}