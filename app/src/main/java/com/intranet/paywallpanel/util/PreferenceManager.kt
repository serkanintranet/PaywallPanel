package com.intranet.paywallpanel.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

open class PreferenceManager constructor(context: Context) : IPreferenceHelper {

    private val PREFS_NAME = "com.intranet.paywall.pref"
    private var preferences: SharedPreferences

    companion object {
        const val TOKEN = "token"
    }

    init {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun setToken(userToken: String) {
        try {
            preferences[TOKEN] = userToken
        } catch (e: Exception) {
            //  Firebase.crashlytics.recordException(e)
        }
    }

    override fun getToken(): String {
        return try {
            preferences[TOKEN] ?: ""
        } catch (e: Exception) {
            //   Firebase.crashlytics.recordException(e)
            ""
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val encodedString: String =
                        Base64.getEncoder().encodeToString(value?.toByteArray())
                    it.putString(key, encodedString)
                } else {
                    it.putString(key, value)
                }
            }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val decodedBytes =
                        Base64.getDecoder().decode(getString(key, defaultValue as? String))
                    val decodedString = String(decodedBytes)
                    decodedString as T?
                } else {
                    getString(key, defaultValue as? String) as T?
                }

            }


            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}