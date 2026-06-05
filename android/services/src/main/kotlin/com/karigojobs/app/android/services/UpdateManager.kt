package com.karigojobs.app.android.services

import android.app.Activity


/**
 * @author hazratummar
 * Created on 05/06/26
 */



interface UpdateManager  {


    fun checkForAppUpdates(activity: Activity)

    fun onResume(activity: Activity)

    fun onDestroy()


}