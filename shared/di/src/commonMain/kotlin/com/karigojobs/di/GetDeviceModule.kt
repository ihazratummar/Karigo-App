package com.karigojobs.di

import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 24/05/26
 */

expect fun getDeviceModule(): Module
expect fun getAnalyticsLogger(): com.karigojobs.domain.analytics.AnalyticsLogger