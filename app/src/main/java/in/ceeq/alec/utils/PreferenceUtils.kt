package `in`.ceeq.alec.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceUtils {
    private val mSharedPreference: SharedPreferences

    var selectedLanguagePosition: Int
        get() = getIntegerPrefs(SELECTED_LANGUAGE_POSITION)
        set(position) = set(SELECTED_LANGUAGE_POSITION, position)

    fun setForceUpgradeStatus(updateAvailable: Boolean, newVersion: Int) {
        set(UPDATE_AVAILABLE, updateAvailable)
        set(UPDATE_VERSION, newVersion)
    }

    val isUpdateAvailable: Boolean
        get() = getBooleanPrefs(UPDATE_AVAILABLE)

    val updateVersion: Int
        get() = getIntegerPrefs(UPDATE_VERSION)


    private constructor(context: Context) {
        mSharedPreference = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    constructor(context: Context, preferenceFileName: String) {
        mSharedPreference = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE)
    }

    constructor(context: Context, dummy: Int) {
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @JvmOverloads fun getBooleanPrefs(key: String, defaultValue: Boolean? = false): Boolean {
        return mSharedPreference.getBoolean(key, defaultValue!!)
    }

    fun getStringPrefs(key: String): String {
        return mSharedPreference.getString(key, "")
    }

    fun getStringPrefs(key: String, defaultValue: String): String {
        return mSharedPreference.getString(key, defaultValue)
    }

    fun getIntegerPrefs(key: String): Int {
        return mSharedPreference.getInt(key, 0)
    }

    fun getIntegerPrefs(key: String, defaultValue: Int): Int {
        return mSharedPreference.getInt(key, defaultValue)
    }

    fun getLongPrefs(key: String): Long {
        return mSharedPreference.getLong(key, 0L)
    }

    fun getLongPrefs(key: String, defaultValue: Long): Long {
        return mSharedPreference.getLong(key, defaultValue)
    }


    fun getFloatPrefs(key: String): Float {
        return mSharedPreference.getFloat(key, 0f)
    }

    operator fun set(key: String, value: Any) {
        mSharedPreference.edit()?.let {
            when (value) {
                is String -> it.putString(key, value)
                is Long -> it.putLong(key, value)
                is Int -> it.putInt(key, value)
                is Boolean -> it.putBoolean(key, value)
                is Float -> it.putFloat(key, value)
            }
            it.apply()
        }
    }

    fun clear() {
        mSharedPreference.edit().clear().commit()
    }

    fun isKeyPresent(key: String): Boolean {
        return mSharedPreference.contains(key)
    }

    var uniqueId: String
        get() = getStringPrefs(UNIQUE_ID)
        set(uniqueId) = set(UNIQUE_ID, uniqueId)

    companion object {

        private val PREFERENCE_FILE_NAME = "common_preferences"

        private val SELECTED_LANGUAGE_POSITION = "selected_language_position"
        val UPDATE_AVAILABLE = "update_available"
        val UPDATE_VERSION = "update_version"
        val UNIQUE_ID = "unique_id"

        fun newInstance(context: Context): PreferenceUtils {
            return PreferenceUtils(context)
        }
    }
}
