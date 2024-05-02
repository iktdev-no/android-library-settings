package no.iktdev.demoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.iktdev.demoapplication.databinding.ActivitySetting2Binding
import no.iktdev.demoapplication.databinding.ActivitySettingBinding
import no.iktdev.setting.ReactiveSettingsReceiver
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.DropdownComponentItems
import no.iktdev.setting.model.DropdownItem
import no.iktdev.setting.model.SettingComponentDescriptorBase
import no.iktdev.setting.model.SettingComponentType
import no.iktdev.setting.model.builder.ActionableComponentDataBuilder
import no.iktdev.setting.model.builder.LaunchableComponentDataBuilder
import no.iktdev.setting.model.builder.SettingComponentDescriptorBuilder
import no.iktdev.setting.ui.ThemeType
import no.iktdev.setting.ui.Theming
import no.iktdev.setting.ui.activities.SettingsActivity
import no.iktdev.setting.ui.activities.SettingsRenderActivity

class SettingActivity2 : SettingsRenderActivity() {
    lateinit var binding: ActivitySetting2Binding
    private var reactiveSettingsReceiver: ReactiveSettingsReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySetting2Binding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        toolbar = binding.toolbar
        setCollapsingToolbar(binding.appBar, binding.toolbarLayout, binding.toolbarTitle)
        super.onCreate(savedInstanceState)
        reactiveSettingsReceiver =
            ReactiveSettingsReceiver(this, object : ReactiveSettingsReceiver.Listener {
                override fun onReactiveGroupSettingsChanged(
                    group: String,
                    key: String,
                    payload: Any?
                ) {
                    super.onReactiveGroupSettingsChanged(group, key, payload)
                    Toast.makeText(
                        this@SettingActivity2,
                        "${this@SettingActivity2::class.java.simpleName} Setting group: $group got changed value for key $key with value $payload",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        titleChange.observe(this) {
            title = it
            binding.toolbarLayout.title = title
            binding.toolbarTitle.text = title
            binding.toolbarTitleLarge.text = title
        }
    }


    override fun preCreatedSettingItems(): List<SettingComponentDescriptorBase> {
        val external = arrayListOf<SettingComponentDescriptorBase>(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setType(SettingComponentType.POPOUT_SELECT)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setSetting(Settings.ValueSelector.SelectSomethingElse())
                .setPayload(
                    DropdownComponentItems(
                        arrayListOf(
                            DropdownItem("Stringy", "Potatis"),
                            DropdownItem("Inti", 0),
                            DropdownItem("Intifffffffffssssssssssf", 1)
                        )
                    )
                )
                .build()
        )
        return external + listOf(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_launchy)
                .setTitle("I will crash")
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.CLICKABLE)
                .setPayload(
                    ActionableComponentDataBuilder()
                        .setTitle("Fancy Section")
                        .setActivityClass(SettingActivity::class.java)
                        .applyPayload(SettingsActivity.componentPassKey, external)
                        .build()
                )
                .build(),
        )
    }

    override fun themes(): List<Theming> {
        return listOf(
            Theming(ThemeType.NORMAL, no.iktdev.setting.R.style.SettingComponents),
            Theming(ThemeType.START, no.iktdev.setting.R.style.SettingComponents_Top),
            Theming(ThemeType.END, no.iktdev.setting.R.style.SettingComponents_Bottom),
            Theming(ThemeType.SINGLE, no.iktdev.setting.R.style.SettingComponents_Single),
        )
    }

    override fun getRenderContainer(): LinearLayout {
        return binding.content.renderContainer
    }
}