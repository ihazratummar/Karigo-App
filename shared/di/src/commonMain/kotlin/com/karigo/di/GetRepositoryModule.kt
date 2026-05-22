package com.karigo.di

import com.karigo.data.repository.MaterialRepositoryImpl
import com.karigo.domain.repository.MaterialRepository
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 22/05/26
 */


fun getRepositoryModule(): Module = module {
    single<MaterialRepository> { MaterialRepositoryImpl(database = get()) }
}