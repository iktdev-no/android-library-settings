package no.iktdev.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import no.iktdev.setting.access.GroupedReactiveSetting
import no.iktdev.setting.access.ReactiveSetting.Companion.ReactiveGroupPassKey
import no.iktdev.setting.access.ReactiveSetting.Companion.ReactiveKeyPassKey
import no.iktdev.setting.access.ReactiveSetting.Companion.ReactivePayloadPassKey
import no.iktdev.setting.access.SingleReactiveSetting

@Suppress("unused")
class ReactiveSettingsReceiver(val context: Context, var listener: Listener?) {
    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras ?: return
            val group: String? = bundle.getString(ReactiveGroupPassKey, null)
            val key : String = bundle.getString(ReactiveKeyPassKey) ?: return
            val payload: Any = bundle.getSerializable(ReactivePayloadPassKey) ?: return

            if (group.isNullOrBlank()) {
                // is single
                listener?.onReactiveSettingsChanged(key, payload)
            } else {
                listener?.onReactiveGroupSettingsChanged(group, key, payload)
            }


        }
    }
    init {
        context.registerReceiver(receiver, IntentFilter().apply {
            addAction(GroupedReactiveSetting.SETTING_INTENT_FILTER)
            addAction(SingleReactiveSetting.SETTING_INTENT_FILTER)
        })
    }

    interface Listener {
        fun onReactiveGroupSettingsChanged(group: String, key: String, payload: Any?) {}
        fun onReactiveSettingsChanged(key: String, payload: Any?) {}
    }
}