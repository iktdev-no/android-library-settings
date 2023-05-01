package no.iktdev.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import no.iktdev.setting.access.ReactiveSettingDefined

@Suppress("unused")
class ReactiveSettingsReceiver(val context: Context, var listener: Listener?) {
    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras ?: return
            val group: String = bundle.getString(ReactiveSettingDefined.ReactiveGroupPassKey) ?: return
            val key : String = bundle.getString(ReactiveSettingDefined.ReactiveKeyPassKey) ?: return
            val payload: Any = bundle.getSerializable(ReactiveSettingDefined.ReactivePayloadPassKey) ?: return
            listener?.onReactiveSettingsChanged(group, key, payload)
        }
    }
    init {
        context.registerReceiver(receiver, IntentFilter(ReactiveSettingDefined.SETTING_INTENT_FILTER))
    }

    interface Listener {
        fun onReactiveSettingsChanged(group: String, key: String, payload: Any)
    }
}