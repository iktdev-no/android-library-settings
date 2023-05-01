package no.iktdev.setting.access

import android.content.Context
import android.content.SharedPreferences
import java.io.Serializable
import java.lang.IllegalStateException

abstract class SettingAccessor: Serializable {



    fun singleReader(context: Context, key: String): SharedPreferences {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    fun groupReader(context: Context, group: String): SharedPreferences {
        return context.getSharedPreferences(group, Context.MODE_PRIVATE)
    }

    protected fun singleEditor(context: Context, key: String): SharedPreferences.Editor {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE).edit()
    }

    @Throws(IllegalStateException::class)
    protected fun groupEditor(context: Context, group: String): SharedPreferences.Editor {
        if (group.isEmpty()) {
            throw IllegalStateException("Attempted to create Preference editor on a Instance of Setting where settingGroup was null!")
        }
        return context.getSharedPreferences(group, Context.MODE_PRIVATE).edit()
    }
}