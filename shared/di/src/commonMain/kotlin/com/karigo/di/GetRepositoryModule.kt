package com.karigo.di

import com.karigo.data.repository.ClientRepositoryImpl
import com.karigo.data.repository.JobRepositoryImpl
import com.karigo.data.repository.MaterialRepositoryImpl
import com.karigo.domain.repository.ClientRepository
import com.karigo.domain.repository.JobRepository
import com.karigo.domain.repository.MaterialRepository
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

    single<MaterialRepository> { MaterialRepositoryImpl(database = get()) }
    single<JobRepository> { JobRepositoryImpl(karigoDatabase = get(), ioDispatcher = get()) }
    single<ClientRepository> { ClientRepositoryImpl(database = get()) }
}