package com.karigojobs.domain.analytics

/**
 * Defines all valid analytics events and screen names.
 * Centralized here to avoid messy hardcoded strings throughout the app.
 */
object AnalyticsEvent {

    object Screen {
        const val ONBOARDING = "screen_onboarding"
        const val WORKER_PROFILE = "screen_worker_profile"
        const val HOME = "screen_home"
        const val SETTINGS = "screen_settings"
        const val DATA_BACKUP = "screen_data_backup"
        
        const val JOB_LIST = "screen_job_list"
        const val ADD_JOB = "screen_add_job"
        const val JOB_DETAILS = "screen_job_details"
        
        const val ESTIMATE_LIST = "screen_estimate_list"
        const val ADD_ESTIMATE = "screen_add_estimate"
        const val ESTIMATE_DETAILS = "screen_estimate_details"
        
        const val CLIENT_LIST = "screen_client_list"
        const val CLIENT_DETAILS = "screen_client_details"
        
        const val MATERIAL_LIST = "screen_material_list"
        const val MATERIAL_CATEGORY = "screen_material_category"
    }

    object Event {
        const val APP_LAUNCHED = "event_app_launched"
        
        const val ONBOARDING_STARTED = "event_onboarding_started"
        const val ONBOARDING_COMPLETED = "event_onboarding_completed"
        const val WORKER_PROFILE_SAVED = "event_worker_profile_saved"
        
        const val JOB_CREATED = "event_job_created"
        const val ESTIMATE_CREATED = "event_estimate_created"
        const val CLIENT_ADDED = "event_client_added"
        
        const val BACKUP_TRIGGERED = "event_backup_triggered"
        const val THEME_CHANGED = "event_theme_changed"
        const val LANGUAGE_CHANGED = "event_language_changed"
    }

    object Param {
        const val THEME_NAME = "param_theme_name"
        const val LANGUAGE_CODE = "param_language_code"
        const val IS_AUTO_BACKUP = "param_is_auto_backup"
    }
}
