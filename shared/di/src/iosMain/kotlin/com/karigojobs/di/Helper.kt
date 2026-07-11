@file:OptIn(ExperimentalForeignApi::class)
package com.karigojobs.di

import com.karigojobs.presentation.dashboard.HomeViewModel
import com.karigojobs.presentation.dashboard.HomeState
import com.karigojobs.presentation.dashboard.HomeEffect
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface Closeable { fun close() }

class CFlow<T: Any>(private val origin: Flow<T>) {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        scope.launch {
            origin.collect { block(it) }
        }
        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}

// Wrapper to avoid extending androidx.lifecycle.ViewModel in exported iOS API
class Helper : KoinComponent {
    fun getHomeViewModel(): HomeViewModel = get()
    
    fun initKoinIos() {
        initKoin {
            modules(org.koin.dsl.module {
                single<String> {
                    val url = platform.Foundation.NSFileManager.defaultManager.URLForDirectory(
                        directory = platform.Foundation.NSDocumentDirectory,
                        inDomain = platform.Foundation.NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null
                    )
                    url?.path ?: ""
                }
            })
        }
    }
}

class IosHomeViewModelWrapper : KoinComponent {
    private val viewModel: HomeViewModel = get()
    
    val initialState: HomeState = viewModel.state.value
    
    fun watchState(block: (HomeState) -> Unit): Closeable {
        return CFlow(viewModel.state).watch(block)
    }
    
    fun watchEffect(block: (HomeEffect) -> Unit): Closeable {
        return CFlow(viewModel.effect).watch(block)
    }
}

