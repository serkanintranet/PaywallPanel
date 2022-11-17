package com.intranet.paywallpanel.util

interface IPreferenceHelper {

    fun setToken(userToken: String)
    fun getToken(): String

}