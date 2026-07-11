package com.karigojobs.domain.analytics

/**
 * A strict contract for logging analytics events.
 * Crucially omits user-identifiable tracking methods (no PII).
 */
interface AnalyticsLogger {
    fun logEvent(eventName: String, params: Map<String, Any> = emptyMap())
    fun logScreenView(screenName: String, screenClass: String? = null)
}
