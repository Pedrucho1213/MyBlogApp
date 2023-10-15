package com.example.myblogapp.domain.data

import android.content.Context
import androidx.preference.PreferenceManager

object PreferenceManager {

    private const val KEY_NAME = "fullName"
    private const val KEY_UID = "uid"
    private const val KEY_EMAIL = "email"

    fun saveName(context: Context, name: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, name)
        editor.apply()
    }

    fun saveEmail(context: Context, email: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun saveUID(context: Context, uid: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_UID, uid)
        editor.apply()
    }

    fun getName(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getEmail(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getUID(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(KEY_UID, null)
    }


}