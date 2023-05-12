package no.iktdev.setting.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.iktdev.setting.R
import no.iktdev.setting.access.GroupedReactiveSetting
import no.iktdev.setting.access.ReactiveSetting
import no.iktdev.setting.access.SingleReactiveSetting
import no.iktdev.setting.exception.IncompatibleComponentPassed
import no.iktdev.setting.exception.NoUiComponentsPassed
import no.iktdev.setting.factory.ComponentFactory
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.ui.ThemeType
import no.iktdev.setting.ui.Theming

abstract class SettingsActivity: AppCompatActivity() {
    companion object {
        const val componentPassKey = "SettingComponentItemList"
    }

    abstract fun getRenderContainer(): LinearLayout

    open fun preCreatedSettingItems(): List<SettingComponentDescriptorBase> {
        return emptyList()
    }

    open fun themes(): List<Theming> {
        return listOf(
            Theming(ThemeType.NORMAL, R.style.SettingComponents),
            Theming(ThemeType.START, R.style.SettingComponents_Top),
            Theming(ThemeType.END, R.style.SettingComponents_Bottom),
            Theming(ThemeType.SINGLE, R.style.SettingComponents_Single),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val components: Any? = if (intent.hasExtra(componentPassKey)) intent.getSerializableExtra(componentPassKey) else if (preCreatedSettingItems().isNotEmpty()) preCreatedSettingItems() else throw NoUiComponentsPassed("No Ui Settings Components were passed upon initialization")
        if (components !is List<*> || !components.all { it is SettingComponentDescriptorBase }) { throw IncompatibleComponentPassed("Incompatible Ui Component were passed to SettingsActivity") }

        @Suppress("UNCHECKED_CAST")
        val usable: List<SettingComponentDescriptorBase> = components as List<SettingComponentDescriptorBase>
        usable.groupBy { it.groupName }
            .forEach { (_, list) ->
                lifecycleScope.launch {
                    val manufactured = ComponentFactory(this@SettingsActivity, list, themes()).generate()
                    withContext(Dispatchers.Main) {
                        addAll(manufactured)
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter()
            .apply {
                addAction(GroupedReactiveSetting.SETTING_INTENT_FILTER)
                addAction(SingleReactiveSetting.SETTING_INTENT_FILTER)
            })
    }

    private fun addAll(children: List<View>) {
        children.forEach {
            getRenderContainer().addView(it)
        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras ?: return
            val group: String? = bundle.getString(ReactiveSetting.ReactiveGroupPassKey, null)
            val key : String = bundle.getString(ReactiveSetting.ReactiveKeyPassKey) ?: return
            val payload: Any = bundle.getSerializable(ReactiveSetting.ReactivePayloadPassKey) ?: return

            if (group.isNullOrBlank()) {
                // is single
                onReactiveSettingsChanged(key, payload)
            } else {
                onReactiveSettingsChanged(group, key, payload)
            }
        }
    }

    protected open fun onReactiveSettingsChanged(key: String, payload: Any) {}
    protected open fun onReactiveSettingsChanged(group: String, key: String, payload: Any) {}


    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



}