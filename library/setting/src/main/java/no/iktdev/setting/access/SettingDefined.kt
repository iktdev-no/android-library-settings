package no.iktdev.setting.access

import android.content.Context
import android.content.SharedPreferences

open class SettingDefined(val settingGroup: String? = null, val settingKey: String): SettingAccessor() {

    open fun setString(context: Context, value: String) {
        editor(context).putString(settingKey, value).apply()
    }

    open fun setBoolean(context: Context, value: Boolean) {
        editor(context).putBoolean(settingKey, value).apply()
    }

    open fun setFloat(context: Context, value: Float) {
        editor(context).putFloat(settingKey, value).apply()
    }

    open fun setInt(context: Context, value: Int) {
        editor(context).putInt(settingKey, value).apply()
    }



    fun getString(context: Context, default: String? = null): String? {
        return reader(context).getString(settingKey, default)
    }

    fun getInt(context: Context, default: Int = 0): Int {
        return reader(context).getInt(settingKey, default)
    }

    fun getBoolean(context: Context, default: Boolean = false): Boolean {
        return reader(context).getBoolean(settingKey, default)
    }

    fun getFloat(context: Context, default: Float = 0F): Float {
        return reader(context).getFloat(settingKey, default)
    }

    fun getSettings(context: Context): MutableMap<String, *>? {
        return reader(context).all
    }



    open fun removeSetting(context: Context) {
        editor(context).remove(settingKey).apply()
    }

    open fun deleteSetting(context: Context) {
        editor(context).clear().apply()
    }

    fun reader(context: Context): SharedPreferences {
        return if (settingGroup.isNullOrEmpty()) singleReader(context, settingKey) else groupReader(context, settingGroup)
    }

    fun editor(context: Context): SharedPreferences.Editor {
        return if (settingGroup.isNullOrEmpty()) singleEditor(context, settingKey) else groupEditor(context, settingGroup)
    }

}