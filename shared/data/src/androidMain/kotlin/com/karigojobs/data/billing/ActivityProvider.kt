package com.karigojobs.data.billing

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import java.lang.ref.WeakReference

object ActivityProvider : Application.ActivityLifecycleCallbacks {
    private var activeActivity: WeakReference<Activity>? = null

    val currentActivity: Activity?
        get() {
            val activity = activeActivity?.get()
            return if (activity != null && !activity.isFinishing && !activity.isDestroyed) {
                activity
            } else {
                null
            }
        }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activeActivity = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        activeActivity = WeakReference(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        activeActivity = WeakReference(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        // Do not clear activeActivity on pause — activity is still valid for billing flow dialogs
    }

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (activeActivity?.get() === activity) {
            activeActivity = null
        }
    }
}

fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity && !ctx.isFinishing && !ctx.isDestroyed) {
            return ctx
        }
        ctx = ctx.baseContext
    }
    return null
}
