package com.karigojobs.presentation.settings

import com.karigojobs.share.model.WorkerProfileModel


/**
 * @author hazratummar
 * Created on 24/06/26
 */
 


data class SettingsState(
    val isLoading : Boolean = false,
    val workerProfileModel: WorkerProfileModel? = null
)