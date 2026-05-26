package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.karigojobs.data.dto.toModel
import com.karigojobs.data.dto.toModelList
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.result.ClientError
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.ClientModel
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 24/05/26
 */

class ClientRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : ClientRepository {


    override fun getAllClients(): Flow<Result<List<ClientModel>, ClientError>> {
        return database.clientQueries.getAllClients()
            .asFlow()
            .mapToList(ioDispatcher)
            .map { clients ->
                Result.Success(clients.toModelList())
            }.catch {
                Result.Error(ClientError.Database)
            }
    }

    override suspend fun getClientById(id: String): Result<ClientModel?, ClientError> {
        return safeCall(ClientError.Database){
            database.clientQueries
                .getClientById(id = id)
                .executeAsOneOrNull()?.toModel()
        }
    }

    override suspend fun getClientByMobile(mobile: String): Result<ClientModel?, ClientError> {
        return safeCall(ClientError.Database){
            database.clientQueries
                .getClientByPhone(phone = mobile)
                .executeAsOneOrNull()?.toModel()
        }
    }

    override fun searchClients(query: String): Flow<Result<List<ClientModel>, ClientError>> {
        return database.clientQueries
            .searchClient(query = query)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { clients ->
                Result.Success(clients.toModelList())
            }.catch {
                Result.Error(ClientError.Database)
            }
    }

    override suspend fun insertClient(clientModel: ClientModel) : Result<Unit, ClientError> {
        return safeCall(ClientError.FailedToInsert ){
            database.clientQueries.insertClient(
                id = clientModel.id,
                name = clientModel.name,
                phone = clientModel.phone,
                email = clientModel.email,
                address = clientModel.address,
                total_jobs = clientModel.totalJob.toLong(),
                created_at = EpochUtils.now(),
                updated_at = EpochUtils.now()
            )
        }
    }

    override suspend fun updateClient(clientModel: ClientModel) : Result<Unit, ClientError> {
        return safeCall(ClientError.FailedToUpdate){
            database.clientQueries.updateClient(
                id = clientModel.id,
                name = clientModel.name,
                phone = clientModel.phone,
                email = clientModel.email,
                address = clientModel.address,
                updated_at = EpochUtils.now()
            )
        }
    }

    override suspend fun deleteClient(id: String) : Result<Unit, ClientError> {
        return safeCall(ClientError.FailedToDelete){
            database.clientQueries
                .deleteClient(id = id)
        }
    }
}