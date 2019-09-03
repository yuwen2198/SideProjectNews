package com.davidhsu.newssideproject.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * @author : DavidHsu on 2019/09/01
 *
 */
class AccountSharePreference(context: Context) {

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(IS_FIRST_LOGIN, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference.edit()

    companion object{
        private const val IS_FIRST_LOGIN = "is_first_login"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PHOTOURL = "photo_url"
        private const val LOGIN_TYPE = "login_type"
    }

    fun setIsFirstLogin(status: Boolean) {
        editor.putBoolean(IS_FIRST_LOGIN, status).commit()
    }

    fun isFirstLogin(): Boolean  = sharedPreference.getBoolean(IS_FIRST_LOGIN, true)

    fun setName(name: String) {
        editor.putString(NAME, name).commit()
    }

    fun getName(): String = sharedPreference.getString(NAME, "")

    fun setEmail(email: String) {
        editor.putString(EMAIL, email).commit()
    }

    fun getEmail(): String = sharedPreference.getString(EMAIL, "")

    fun setPhoto(url: String) {
        editor.putString(PHOTOURL, url).commit()
    }

    fun getPhoto(): String = sharedPreference.getString(PHOTOURL, "")

    fun setLogInType(type: String) {
        editor.putString(LOGIN_TYPE, type) .commit()
    }

    fun getLogInType(): String = sharedPreference.getString(LOGIN_TYPE, "")

    fun clear() {
        editor.clear().commit()
    }
}