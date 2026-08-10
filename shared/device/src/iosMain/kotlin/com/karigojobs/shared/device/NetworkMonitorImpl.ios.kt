package com.karigojobs.shared.device

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * iOS stub — always online.
 * Will be replaced with NWPathMonitor (Network framework) when iOS UI is implemented.
 */
class NetworkMonitorImpl : NetworkMonitor {
    private val _isOnline = MutableStateFlow(true)
    override val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()
}
