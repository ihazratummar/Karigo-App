package com.karigojobs.di

import com.karigojobs.data.repository.ClientRepositoryImpl
import com.karigojobs.data.repository.JobRepositoryImpl
import com.karigojobs.data.repository.MaterialRepositoryImpl
import com.karigojobs.domain.repository.ClientRepository
import com.karigojobs.domain.repository.JobRepository
import com.karigojobs.domain.repository.MaterialRepository
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

    single <CoroutineDispatcher>{ Dispatchers.IO }

    single<MaterialRepository> { MaterialRepositoryImpl(database = get(), ioDispatcher = get()) }
    single<JobRepository> { JobRepositoryImpl(karigojobsDatabase = get(), ioDispatcher = get()) }
    single<ClientRepository> { ClientRepositoryImpl(database = get(), ioDispatcher = get()) }
}