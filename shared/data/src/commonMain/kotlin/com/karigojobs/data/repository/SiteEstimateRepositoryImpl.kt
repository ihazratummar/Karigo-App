package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.karigojobs.data.dto.toEstimateListModel
import com.karigojobs.data.dto.toEstimateMaterialModelList
import com.karigojobs.data.dto.toOneEstimateModel
import com.karigojobs.data.safeCall
import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 05/06/26
 */

class SiteEstimateRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : SiteEstimateRepository {

    override fun getAllSiteEstimates(query: String): Flow<Result<List<SiteEstimateModel>, SiteEstimateError>> {
        return database.siteEstimateQueries
            .getAllEstimate(query = query)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { data ->
                Result.Success(data.toEstimateListModel())
            }.catch {
                Result.Error(SiteEstimateError.DatabaseError)
            }
    }

    override fun getSiteEstimate(id: String): Flow<Result<SiteEstimateModel, SiteEstimateError>> {
        return database.siteEstimateQueries
            .getEstimateById(id = id)
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { siteEstimate ->
                if (siteEstimate != null) {
                    Result.Success(siteEstimate.toOneEstimateModel())
                } else {
                    Result.Error(SiteEstimateError.DatabaseError)
                }
            }.catch {
                Result.Error(SiteEstimateError.DatabaseError)
            }
    }

    override fun getEstimateMaterials(estimateId: String): Flow<Result<List<SiteEstimateMaterial>, SiteEstimateError>> {
        return database.siteEstimateMaterialQueries
            .getMaterialsForEstimate(estimateId = estimateId)
            .asFlow()
            .mapToList(ioDispatcher)
            .map { data ->
                Result.Success(data.toEstimateMaterialModelList())
            }.catch {
                Result.Error(SiteEstimateError.DatabaseError)
            }
    }

    override suspend fun insertSiteEstimate(
        siteEstimateModel: SiteEstimateModel,
        siteEstimateMaterials: List<SiteEstimateMaterial>
    ): Result<Unit, SiteEstimateError> {

        return safeCall(SiteEstimateError.DatabaseError) {
            database.siteEstimateQueries.transaction {
                database.siteEstimateQueries.insertEstimate(
                    id = siteEstimateModel.id,
                    client_id = siteEstimateModel.clientId,
                    project_title = siteEstimateModel.projectTitle,
                    date = siteEstimateModel.date,
                    site_notes = siteEstimateModel.siteNote,
                    show_rate = siteEstimateModel.showRate,
                    total = siteEstimateModel.total,
                    create_at = EpochUtils.now(),
                    updated_at = EpochUtils.now()
                )

                siteEstimateMaterials.forEach { siteMaterials ->
                    database.siteEstimateMaterialQueries.insertMaterial(
                        id = UuidGenerator.generate(),
                        estimate_id = siteMaterials.estimateId,
                        material_id = siteMaterials.materialId,
                        material_name = siteMaterials.materialName,
                        quantity = siteMaterials.quantity,
                        unit = siteMaterials.unit,
                        rate = siteMaterials.rate,
                        amount = siteMaterials.total,
                        created_at = EpochUtils.now()
                    )
                }
            }
        }
    }

    override suspend fun updateSiteEstimate(
        siteEstimateModel: SiteEstimateModel,
        siteEstimateMaterial: List<SiteEstimateMaterial>
    ): Result<Unit, SiteEstimateError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEstimate(estimateId: String): Result<Unit, SiteEstimateError> {
        return safeCall(SiteEstimateError.DatabaseError) {
            database.siteEstimateQueries.deleteEstimate(id = estimateId)
        }
    }
}