package com.karigojobs.data.analytics

import com.karigojobs.domain.analytics.AnalyticsLogger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics

class FirebaseAnalyticsLogger : AnalyticsLogger {

    override fun logEvent(eventName: String, params: Map<String, Any>) {
        Firebase.analytics.logEvent(eventName, params)
    }

    override fun logScreenView(screenName: String, screenClass: String?) {
        val params = mutableMapOf<String, Any>()
        params["screen_name"] = screenName
        if (screenClass != null) {
            params["screen_class"] = screenClass
        }
        Firebase.analytics.logEvent("screen_view", params)
    }
}
