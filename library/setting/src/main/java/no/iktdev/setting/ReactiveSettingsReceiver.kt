package no.iktdev.setting

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
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
            val payload: Any? = bundle.getSerializable(ReactivePayloadPassKey)

            if (group.isNullOrBlank()) {
                // is single
                listener?.onReactiveSettingsChanged(key, payload)
            } else {
                listener?.onReactiveGroupSettingsChanged(group, key, payload)
            }


        }
    }

    private var isRegistered = false
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    fun register() {
        if (isRegistered) {
            Log.e(this::class.java.simpleName, "Receiver is already registered! ${context::class.java.simpleName}")
            return
        } else if (context is no.iktdev.setting.ui.activities.SettingsActivity) {
            Log.e(this::class.java.simpleName, "Registering receiver when inheriting from SettingActivity is not permitted!\n\tPlease override provided functions instead!\n\t\t ${context::class.java.simpleName}")
            return
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, IntentFilter().apply {
                addAction(GroupedReactiveSetting.SETTING_INTENT_FILTER)
                addAction(SingleReactiveSetting.SETTING_INTENT_FILTER)
            }, Context.RECEIVER_EXPORTED)
        } else {
            context.registerReceiver(receiver, IntentFilter().apply {
                addAction(GroupedReactiveSetting.SETTING_INTENT_FILTER)
                addAction(SingleReactiveSetting.SETTING_INTENT_FILTER)
            })
        }
        isRegistered = true
    }
    fun unregister() {
        context.let {
            if (isRegistered) {
                it.unregisterReceiver(receiver)
            }
        }
    }

    init {
        if (context is AppCompatActivity) {
            context.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    register()
                }

                override fun onPause(owner: LifecycleOwner) {
                    super.onPause(owner)
                    try {
                        unregister()
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            })
        } else {
            register()
        }
    }

    interface Listener {
        fun onReactiveGroupSettingsChanged(group: String, key: String, payload: Any?) {}
        fun onReactiveSettingsChanged(key: String, payload: Any?) {}
    }
}