package no.iktdev.setting.access

import android.content.Context
import android.content.Intent
import android.os.Bundle
import no.iktdev.setting.access.ReactiveSetting.Companion.ReactivePayloadPassKey
import java.io.Serializable

open class GroupedStaticSetting(group: String, key: String): GroupBasedSetting(group, key)
open class SingleStaticSetting(key: String): KeyBasedSetting(key)

open class SingleReactiveSetting(key: String): KeyBasedSetting(key), ReactiveSetting {
    companion object {
        const val SETTING_INTENT_FILTER = "K_SETTING_ACTION"
    }

    private var payload: Serializable? = null
    override fun setPayload(data: Serializable) {
        payload = data
    }


    override fun setString(context: Context, value: String) {
        super.setString(context, value)
        onChange(context)
    }

    override fun setBoolean(context: Context, value: Boolean) {
        super.setBoolean(context, value)
        onChange(context)
    }

    override fun setInt(context: Context, value: Int) {
        super.setInt(context, value)
        onChange(context)
    }

    override fun setFloat(context: Context, value: Float) {
        super.setFloat(context, value)
        onChange(context)
    }


    override fun onChange(context: Context) {
        val bundle = Bundle()
        bundle.putString("key", key)
        payload?.let {
            bundle.putSerializable(ReactivePayloadPassKey, it)
        }

        val i = Intent(SETTING_INTENT_FILTER)
        i.putExtras(bundle)
        context.applicationContext.sendBroadcast(i)
    }
}

open class GroupedReactiveSetting(group: String, key: String): GroupBasedSetting(group, key), ReactiveSetting {
    companion object {
        const val SETTING_INTENT_FILTER = "G_SETTING_ACTION"
    }

    private var payload: Serializable? = null
    override fun setPayload(data: Serializable) {
        payload = data
    }

    override fun setString(context: Context, value: String) {
        super.setString(context, value)
        onChange(context)
    }

    override fun setBoolean(context: Context, value: Boolean) {
        super.setBoolean(context, value)
        onChange(context)
    }

    override fun setInt(context: Context, value: Int) {
        super.setInt(context, value)
        onChange(context)
    }

    override fun setFloat(context: Context, value: Float) {
        super.setFloat(context, value)
        onChange(context)
    }


    override fun onChange(context: Context) {
        val bundle = Bundle()
        bundle.putString("group", group)
        bundle.putString("key", key)
        payload?.let {
            bundle.putSerializable(ReactivePayloadPassKey, it)
        }

        val i = Intent(SETTING_INTENT_FILTER)
        i.putExtras(bundle)
        context.applicationContext.sendBroadcast(i)
    }
}