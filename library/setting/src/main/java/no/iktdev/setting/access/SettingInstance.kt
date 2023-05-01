package no.iktdev.setting.access

import android.content.Context
import android.content.SharedPreferences
import no.iktdev.setting.model.SettingKey

class SettingInstance<T: SettingKey>(val context: Context, val group: String? = null) : SettingAccessor() {

    fun setString(key: T, value: String) {
        editor(context, key.value).putString(key.value, value).apply()
    }

    fun setBoolean(key: T, value: Boolean) {
        editor(context, key.value).putBoolean(key.value, value).apply()
    }

    fun setFloat(key: T, value: Float) {
        editor(context, key.value).putFloat(key.value, value).apply()
    }

    fun setInt(key: T, value: Int) {
        editor(context, key.value).putInt(key.value, value).apply()
    }

    fun getString(key: T): String? {
        return reader(context, key.value).getString(key.value, null)
    }
    fun getString(key: T, default: String): String {
        return reader(context, key.value).getString(key.value, default) ?: default
    }

    fun getInt(key: T, default: Int = 0): Int {
        return reader(context, key.value).getInt(key.value, default)
    }

    fun getBoolean(key: T, default: Boolean = false): Boolean {
        return reader(context, key.value).getBoolean(key.value, default)
    }

    fun getFloat(key: T, default: Float = 0F): Float {
        return reader(context, key.value).getFloat(key.value, default)
    }

    fun getSettings(key: String): MutableMap<String, *>? {
        return reader(context, key).all
    }



    fun removeSetting(key: String) {
        editor(context, key).remove(key).apply()
    }

    fun removeSettingNow(key: String) {
        editor(context, key).remove(key).commit()
    }

    fun deleteSetting(key: String) {
        editor(context, key).clear().apply()
    }

    /**
     * Deletes the setting group immediately
     */
    fun deleteSettingNow(key: String) {
        editor(context, key).clear().commit()
    }

    fun deleteGroup() {
        if (group.isNullOrEmpty())
            throw IllegalStateException("Attempted to delete setting on null or empty")
        groupEditor(context, group).clear().apply()
    }

    /**
     * Deletes the setting group immediately
     */
    fun deleteGroupNow() {
        if (group.isNullOrEmpty())
            throw IllegalStateException("Attempted to delete setting on null or empty")
        groupEditor(context, group).clear().commit()
    }



    protected fun reader(context: Context, key: String): SharedPreferences {
        return if (group.isNullOrEmpty()) singleReader(context, key) else groupReader(context, group)
    }

    protected fun editor(context: Context, key: String): SharedPreferences.Editor {
        return if (group.isNullOrEmpty()) singleEditor(context, key) else groupEditor(context, group)
    }
}