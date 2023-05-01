package no.iktdev.demoapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.iktdev.demoapplication.databinding.ActivitySettingBinding
import no.iktdev.setting.access.ReactiveSettingDefined
import no.iktdev.setting.access.SettingDefined
import no.iktdev.setting.factory.ThemeType
import no.iktdev.setting.model.*
import no.iktdev.setting.model.builder.ActionableComponentDataBuilder
import no.iktdev.setting.model.builder.LaunchableComponentDataBuilder
import no.iktdev.setting.model.builder.SettingComponentDescriptorBuilder
import no.iktdev.setting.ui.activities.SettingsRenderActivity

class SettingActivity : SettingsRenderActivity() {
    lateinit var binding: ActivitySettingBinding

    val cantKeepMeOn = SettingDefined("setting_cant_keep_me_on", "setting_cant_keep_me_on")

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        toolbar = binding.toolbar
        setCollapsingToolbar(binding.appBar, binding.toolbarLayout, binding.toolbarTitle)
        super.onCreate(savedInstanceState)
        titleChange.observe(this) {
            title = it
            binding.toolbarLayout.title = title
            binding.toolbarTitle.text = title
            binding.toolbarTitleLarge.text = title
        }
        cantKeepMeOn.reader(this).registerOnSharedPreferenceChangeListener { _, _ ->
            GlobalScope.launch {
                delay(1000)
                if (cantKeepMeOn.getBoolean(this@SettingActivity, false)) {
                    cantKeepMeOn.setBoolean(this@SettingActivity,false)
                }
            }
        }
    }

    override fun onReactiveSettingsChanged(group: String, key: String, payload: Any) {
        super.onReactiveSettingsChanged(group, key, payload)
    }

    override fun preCreatedSettingItems(): List<SettingComponentDescriptorBase> {
        val external = arrayListOf<SettingComponentDescriptorBase>(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_cant_keep_me_on)
                .setTitle(R.string.setting_cant_keep_me_on)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(cantKeepMeOn)
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_cant_keep_me_on)
                .setTitle("PlayWithMe")
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.CLICKABLE)
                .setPayload(ActionableComponentDataBuilder()
                    .setTitle("Im Fancy")
                    .setActivityClass(SettingActivity::class.java)
                    .applyPayload(componentPassKey, arrayListOf<SettingComponentDescriptorBase>(
                        SettingComponentDescriptorBuilder(this)
                            .setGroupName(R.string.setting_cant_keep_me_on)
                            .setTitle("PlayWithMe")
                            .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                            .setType(SettingComponentType.CLICKABLE)
                            .setPayload(ActionableComponentDataBuilder()
                                .setActivityClass(SettingActivity2::class.java)
                                .build())
                            .build(),
                    ))
                    .build())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(SettingDefined("test", "fancy"))
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title2)
                .setTitle(R.string.setting_title2)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(SettingDefined("test2", "fancy2"))
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.LAUNCH)
                .setPayload(LaunchableComponentDataBuilder()
                    .setUri("https://iktdev.no")
                    .build()
                )
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setType(SettingComponentType.SLIDER)
                .setSetting(SettingDefined("test", "sliding"))
                .build()
        )
        return external + listOf(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title2)
                .setTitle(R.string.setting_title2)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.CLICKABLE)
                .setPayload(ActionableComponentDataBuilder()
                    .setTitle("Fancy Section")
                    .setActivityClass(SettingActivity::class.java)
                    .applyPayload(componentPassKey, external)
                    .build()
                )
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setType(SettingComponentType.DROPDOWN)
                .setSetting(ReactiveSettingDefined("slider", "fancyness"))
                .setPayload(ComponentData(arrayListOf<DropdownItem>(
                    DropdownItem("Stringy", "Potatis"),
                    DropdownItem("Inti", 0)
                )))
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_title)
                .setTitle(R.string.setting_title)
                .setType(SettingComponentType.POPOUT_SELECT)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setSetting(SettingDefined("popout", "fancyness"))
                .setPayload(ComponentData(arrayListOf<DropdownItem>(
                    DropdownItem("Stringy", "Potatis"),
                    DropdownItem("Inti", 0),
                    DropdownItem("Intifffffffffssssssssssf", 1)
                )))
                .build()

        )
    }

    override fun themes(): List<ThemeItem> {
        return listOf(
            ThemeItem(ThemeType.NORMAL, no.iktdev.setting.R.style.SettingComponents),
            ThemeItem(ThemeType.START, no.iktdev.setting.R.style.SettingComponents_Top),
            ThemeItem(ThemeType.END, no.iktdev.setting.R.style.SettingComponents_Bottom),
            ThemeItem(ThemeType.SINGLE, no.iktdev.setting.R.style.SettingComponents_Single),
        )
    }

    override fun getRenderContainer(): LinearLayout {
       return binding.content.renderContainer
    }

}