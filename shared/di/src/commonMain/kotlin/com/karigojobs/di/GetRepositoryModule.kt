package com.karigojobs.di

import com.karigojobs.data.repository.ClientRepositoryImpl
import com.karigojobs.data.repository.JobRepositoryImpl
import com.karigojobs.data.repository.MaterialCategoryRepositoryImpl
import com.karigojobs.data.repository.MaterialRepositoryImpl
import com.karigojobs.data.repository.SiteEstimateRepositoryImpl
import com.karigojobs.data.repository.WorkerRepositoryImpl
import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.repository.MaterialCategoryRepository
import com.karigojobs.domain.repository.MaterialRepository
import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.repository.WorkerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 22/05/26
 */


fun getRepositoryModule(): Module = module {

    single<CoroutineDispatcher> { Dispatchers.IO }

    single<MaterialRepository> { MaterialRepositoryImpl(database = get(), ioDispatcher = get()) }
    single<JobRepository> { JobRepositoryImpl(karigojobsDatabase = get(), ioDispatcher = get()) }
    single<ClientRepository> { ClientRepositoryImpl(database = get(), ioDispatcher = get()) }
    single<SiteEstimateRepository> {
        SiteEstimateRepositoryImpl(
            database = get(),
            ioDispatcher = get()
        )
    }
    single<WorkerRepository> { WorkerRepositoryImpl(database = get(), ioDispatcher = get()) }
    single<MaterialCategoryRepository> { MaterialCategoryRepositoryImpl(database = get(), ioDispatcher = get()) }
}