package com.karigojobs.shared.device

import kotlinx.coroutines.flow.StateFlow

/**
 * Monitors real-time network connectivity.
 * Emits `true` when internet is available, `false` when offline.
 */
interface NetworkMonitor {
    val isOnline: StateFlow<Boolean>
}
