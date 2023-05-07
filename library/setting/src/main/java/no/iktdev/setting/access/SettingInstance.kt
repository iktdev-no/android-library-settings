package no.iktdev.setting.access

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import java.io.Serializable

class SimpleAccessor(name: String, key: String): SettingAccess(name, key) {
    private var listener: OnSharedPreferenceChangeListener? = null
    fun setObserver(context: Context, listener: OnSharedPreferenceChangeListener) {
        this.listener = listener
        read(context).registerOnSharedPreferenceChangeListener(listener)
    }
    fun removeObserver(context: Context, listener: OnSharedPreferenceChangeListener) {
        read(context).unregisterOnSharedPreferenceChangeListener(listener)
    }
}

open class SettingAccess(val name: String, val key: String) : Serializable {

    fun toAccessor(): SimpleAccessor {
        return SimpleAccessor(name, key)
    }

    /**
     * Sets a string type setting
     * @param context Any valid context
     * @param value Non-null value
     * If you want to set "null" delete the setting instead.
     */
    open fun setString(context: Context, value: String) {
        edit(context).putString(key, value).apply()
    }

    open fun setBoolean(context: Context, value: Boolean) {
        edit(context).putBoolean(key, value).apply()
    }

    open fun setFloat(context: Context, value: Float) {
        edit(context).putFloat(key, value).apply()
    }

    open fun setInt(context: Context, value: Int) {
        edit(context).putInt(key, value).apply()
    }

    open fun getString(context: Context, default: String): String {
        return read(context).getString(key, default) ?: default
    }

    /**
     * @return string null if not found
     */
    open fun getString(context: Context): String? {
        return read(context).getString(key, null)
    }

    open fun getInt(context: Context, default: Int = 0): Int {
        return read(context).getInt(key, default)
    }

    /**
     * @return int 0 if not found
     */
    open fun getInt(context: Context): Int {
        return read(context).getInt(key, 0)
    }

    open fun getBoolean(context: Context, default: Boolean = false): Boolean {
        return read(context).getBoolean(key, default)
    }

    /**
     * @return boolean false if not found
     */
    open fun getBoolean(context: Context): Boolean {
        return read(context).getBoolean(key, false)
    }

    open fun getFloat(context: Context, default: Float = 0f): Float {
        return read(context).getFloat(key, default)
    }

    /**
     * @return float 0 if not found
     */
    open fun getFloat(context: Context): Float {
        return read(context).getFloat(key, 0f)
    }

    fun getSettings(context: Context): MutableMap<String, *>? {
        return read(context).all
    }


    fun read(context: Context): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
    protected fun edit(context: Context): SharedPreferences.Editor {
        return getPreference(context, name).edit()
    }
    private fun getPreference(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}


open class GroupBasedSetting(val group: String, key: String): SettingAccess(group, key) {


    fun asStatic(): GroupedStaticSetting {
        return GroupedStaticSetting(group, key)
    }
    fun asReactive(): GroupedReactiveSetting {
        return GroupedReactiveSetting(group, key)
    }

    /**
     * This will request deletion of setting group
     * @param context Any valid context
     * @param now true = commit, false = apply
     * If now is false, deletion will be up to system
     */
    fun deleteGroup(context: Context, now: Boolean = true) {
        val rm = edit(context).clear()
        if (now) rm.commit() else rm.apply()
    }

    /**
     * This will request deletion of setting group
     * @param context Any valid context
     * @param now true = commit, false = apply
     * If now is false, deletion will be up to system
     */
    fun deleteSetting(context: Context, now: Boolean = true) {
        val rm = edit(context).remove(key)
        if (now) rm.commit() else rm.apply()
    }
}

open class KeyBasedSetting(key: String): SettingAccess(key, key) {

    /**
     * This will request deletion of setting group
     * @param context Any valid context
     * @param now true = commit, false = apply
     * If now is false, deletion will be up to system
     */
    fun deleteSetting(context: Context, now: Boolean = true) {
        val rm = edit(context).clear()
        if (now) rm.commit() else rm.apply()
    }
}

interface ReactiveSetting: Serializable {
    companion object {
        const val ReactiveGroupPassKey = "group"
        const val ReactiveKeyPassKey = "key"
        const val ReactivePayloadPassKey = "ReactivePayloadValuePassKey"
        const val ReactiveValuePassKey = "ReactiveValuePassKey"
    }

    fun onChange(context: Context)
    fun setPayload(data: Serializable)
}
