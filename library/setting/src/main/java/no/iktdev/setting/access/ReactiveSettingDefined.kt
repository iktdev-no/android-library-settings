package no.iktdev.setting.access

import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.io.Serializable

class ReactiveSettingDefined(settingGroup: String? = null, settingKey: String) : SettingDefined(settingGroup, settingKey) {
    companion object {
        const val SETTING_INTENT_FILTER = "SETTING_ACTION"
        const val ReactiveGroupPassKey = "group"
        const val ReactiveKeyPassKey = "key"
        const val ReactivePayloadPassKey = "ReactivePayloadValuePassKey"
        const val ReactiveValuePassKey = "ReactiveValuePassKey"
    }

    var reactivePayload: Serializable? = null


    override fun setString(context: Context, value: String) {
        super.setString(context, value)
        reactive(context)
    }

    override fun setBoolean(context: Context, value: Boolean) {
        super.setBoolean(context, value)
        reactive(context)
    }

    override fun setInt(context: Context, value: Int) {
        super.setInt(context, value)
        reactive(context)
    }

    override fun setFloat(context: Context, value: Float) {
        super.setFloat(context, value)
        reactive(context)
    }


    private fun reactive(context: Context) {
        val bundle = Bundle()
        bundle.putString("group", settingGroup)
        bundle.putString("key", settingKey)
        if (reactivePayload != null)
            bundle.putSerializable(ReactivePayloadPassKey, reactivePayload)

        val i = Intent(SETTING_INTENT_FILTER)
        i.putExtras(bundle)
        context.applicationContext.sendBroadcast(i)
    }


}