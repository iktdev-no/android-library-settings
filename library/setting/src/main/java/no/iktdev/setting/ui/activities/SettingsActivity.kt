package no.iktdev.setting.ui.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import no.iktdev.setting.access.ReactiveSettingDefined
import no.iktdev.setting.exception.IncompatibleComponentPassed
import no.iktdev.setting.exception.NoUiComponentsPassed
import no.iktdev.setting.factory.ComponentFactory
import no.iktdev.setting.factory.ThemeType
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.ThemeItem

abstract class SettingsActivity: AppCompatActivity() {
    companion object {
        const val componentPassKey = "SettingComponentItemList"
    }

    abstract fun getRenderContainer(): LinearLayout

    open fun preCreatedSettingItems(): List<SettingComponentDescriptorBase> {
        return emptyList()
    }

    open fun themes(): List<ThemeItem> {
        return listOf(
            ThemeItem(ThemeType.NORMAL, no.iktdev.setting.R.style.SettingComponents),
            ThemeItem(ThemeType.START, no.iktdev.setting.R.style.SettingComponents_Top),
            ThemeItem(ThemeType.END, no.iktdev.setting.R.style.SettingComponents_Bottom),
            ThemeItem(ThemeType.SINGLE, no.iktdev.setting.R.style.SettingComponents_Single),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val components: Any? = if (intent.hasExtra(componentPassKey)) intent.getSerializableExtra(componentPassKey) else if (preCreatedSettingItems().isNotEmpty()) preCreatedSettingItems() else throw NoUiComponentsPassed("No Ui Settings Components were passed upon initialization")
        if (components !is List<*> || !components.all { it is SettingComponentDescriptorBase }) { throw IncompatibleComponentPassed("Incompatible Ui Component were passed to SettingsActivity") }
        val usable: List<SettingComponentDescriptorBase> = components as List<SettingComponentDescriptorBase>
        usable.groupBy { it.groupName }
            .forEach { (s, list) ->
                val manufactured = ComponentFactory(this, list, themes()).generate()
                addAll(manufactured)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(ReactiveSettingDefined.SETTING_INTENT_FILTER))
    }

    private fun addAll(children: List<View>) {
        children.forEach {
            getRenderContainer().addView(it)
        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras ?: return
            val group: String = bundle.getString(ReactiveSettingDefined.ReactiveGroupPassKey) ?: return
            val key : String = bundle.getString(ReactiveSettingDefined.ReactiveKeyPassKey) ?: return
            val payload: Any = bundle.getSerializable(ReactiveSettingDefined.ReactivePayloadPassKey) ?: return
            onReactiveSettingsChanged(group, key, payload)
        }
    }

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