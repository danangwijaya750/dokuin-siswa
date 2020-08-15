package id.infiniteuny.dokuin.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPref(ctx: Context) {
    private val PREF_NAME = "id.infiniteuny.dokuin"

    private val EMAIL = "USER_EMAIL"
    private val PASSWORD = "USER_PASS"
    private val USERNAME = "USER_NAME"
    private val ROLE = "USER_ROLE"
    private val KEY = "KEY"

    private val prefs: SharedPreferences = ctx.getSharedPreferences(PREF_NAME, 0)

    var userRole: String
        get() = prefs.getString(ROLE, "").toString()
        set(value) = prefs.edit().putString(ROLE, value).apply()

    var userEmail: String
        get() = prefs.getString(EMAIL, "").toString()
        set(value) = prefs.edit().putString(EMAIL, value).apply()

    var userPass: String
        get() = prefs.getString(PASSWORD, "").toString()
        set(value) = prefs.edit().putString(PASSWORD, value).apply()

    var userName: String
        get() = prefs.getString(USERNAME, "").toString()
        set(value) = prefs.edit().putString(USERNAME, value).apply()
    var key: String
        get() = prefs.getString(KEY, "").toString()
        set(value) = prefs.edit().putString(KEY, value).apply()
}