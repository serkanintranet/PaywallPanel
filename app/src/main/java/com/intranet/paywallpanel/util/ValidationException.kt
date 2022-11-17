package com.intranet.paywallpanel.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.intranet.paywallpanel.R

class ValidationException(message: String) : Exception(message)

object ValidationHandler {

    fun errorMessages(context: Context, errorCode: Int): String {
        try {
            when (errorCode) {
                1 -> return context.resources.getString(R.string.error_code_1)

                else -> return context.resources.getString(R.string.error_code_1)
            }
        } catch (e: Exception) {
           // Firebase.crashlytics.recordException(e)
            return ""
        }
    }

    fun handleApiErrors(e: Throwable, activity: Activity, context: Context) {
        try {
            if (e.message.toString().contains("401") || e.message.toString().contains("403")) {
                val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(context) }
                preferenceHelper.setToken("")
                Log.d("Network Error - ",  e.message.toString())
                activity.navigateToSplash()
            }
        } catch (e: Exception){

        }
    }

}