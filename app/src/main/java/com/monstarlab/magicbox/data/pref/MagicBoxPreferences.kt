package com.monstarlab.magicbox.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.monstarlab.magicbox.utils.Constants

class MagicBoxPreferences (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)

    fun setBoolean(key: String?, booleanToSave: Boolean) {
        preferences.edit().putBoolean(key, booleanToSave).apply()
    }

    fun getBoolean(key: String?, defaulValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaulValue)
    }

    fun setString(key: String?, stringToSave: String?) {
        preferences.edit().putString(key, stringToSave).apply()
    }

    fun getString(key: String?, defaulValue: String?): String? {
        return preferences.getString(key, defaulValue)
    }

    fun setInteger(key: String?, intToSave: Int) {
        preferences.edit().putInt(key, intToSave).apply()
    }

    fun getInteger(key: String?, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun getFloat(key: String?, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }

    fun setFloat(key: String?, floatToSave: Float) {
        preferences.edit().putFloat(key, floatToSave).apply()
    }

    fun setLong(key: String?, floatToSave: Long) {
        preferences.edit().putLong(key, floatToSave).apply()
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    operator fun contains(key: String?): Boolean {
        return preferences.contains(key)
    }

    /************* REMOVE PREFERENCES  */
    fun removeAPreferences(key: String?) {
        preferences.edit().remove(key).apply()
    }

    fun removeAllPreferences() {
        preferences.edit().clear().apply()
    }

}