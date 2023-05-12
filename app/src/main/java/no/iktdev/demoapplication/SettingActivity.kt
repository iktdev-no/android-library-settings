package no.iktdev.demoapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.iktdev.demoapplication.databinding.ActivitySettingBinding
import no.iktdev.setting.ReactiveSettingsReceiver
import no.iktdev.setting.model.*
import no.iktdev.setting.model.builder.ActionableComponentDataBuilder
import no.iktdev.setting.model.builder.LaunchableComponentDataBuilder
import no.iktdev.setting.model.builder.SettingComponentDescriptorBuilder
import no.iktdev.setting.ui.ThemeType
import no.iktdev.setting.ui.Theming
import no.iktdev.setting.ui.activities.SettingsRenderActivity

class SettingActivity : SettingsRenderActivity() {
    lateinit var binding: ActivitySettingBinding
    private var reactiveSettingsReceiver: ReactiveSettingsReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        toolbar = binding.toolbar
        setCollapsingToolbar(binding.appBar, binding.toolbarLayout, binding.toolbarTitle)
        super.onCreate(savedInstanceState)
        reactiveSettingsReceiver = ReactiveSettingsReceiver(this, object : ReactiveSettingsReceiver.Listener {
            override fun onReactiveGroupSettingsChanged(group: String, key: String, payload: Any?) {
                super.onReactiveGroupSettingsChanged(group, key, payload)
                Toast.makeText(this@SettingActivity, "${this@SettingActivity::class.java.simpleName} Setting group: $group got changed value for key $key with value $payload", Toast.LENGTH_LONG).show()
            }
        })
        titleChange.observe(this) {
            title = it
            binding.toolbarLayout.title = title
            binding.toolbarTitle.text = title
            binding.toolbarTitleLarge.text = title
        }
        Settings.SwitchTest.ToggleMe().setObserver(this) { _, _ ->
            GlobalScope.launch {
                delay(1000)
                if (Settings.SwitchTest.ToggleMe().getBoolean(this@SettingActivity, false)) {
                    Settings.SwitchTest.ToggleMe().setBoolean(this@SettingActivity, false)
                }
            }
        }
    }


    override fun preCreatedSettingItems(): List<SettingComponentDescriptorBase> {
        val external = arrayListOf<SettingComponentDescriptorBase>(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Switch One")
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ImFancyNoOne())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Switch Two")
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ImFancyNoTwo())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Switch Three")
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ImFancyNoThree())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Don´t turn me on")
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ToggleMe())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Switch Reactive")
                .setDescription(R.string.setting_description)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ReactiveToggle().asReactive())
                .build(),
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_switchy)
                .setTitle("Switch")
                .setDescription("With no icon")
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ReactiveToggle().asReactive())
                .build(),


            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_launchy)
                .setTitle(R.string.setting_group_title_launchy)
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.LAUNCH)
                .setPayload(LaunchableComponentDataBuilder()
                    .setUri("https://iktdev.no")
                    .build()
                )
                .build(),

            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_slidy)
                .setTitle("Set me to fancy number")
                .setType(SettingComponentType.SLIDER)
                .setSetting(Settings.ValueSelector.PercentageIsFun())
                .build()
        )
        return external + listOf(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(R.string.setting_group_title_launchy)
                .setTitle("I will crash")
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
                .setGroupName(R.string.setting_group_title_launchy)
                .setTitle("Click me")
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.CLICKABLE)
                .setPayload(ActionableComponentDataBuilder()
                    .setTitle("Im Fancy")
                    .setActivityClass(SettingActivity::class.java)
                    .applyPayload(componentPassKey, arrayListOf<SettingComponentDescriptorBase>(
                        SettingComponentDescriptorBuilder(this)
                            .setGroupName(R.string.setting_cant_keep_me_on)
                            .setTitle("Click me")
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
                .setType(SettingComponentType.DROPDOWN)
                .setSetting(Settings.ValueSelector.SelectSomething().asReactive())
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
                .setSetting(Settings.ValueSelector.SelectSomethingElse())
                .setPayload(ComponentData(arrayListOf<DropdownItem>(
                    DropdownItem("Stringy", "Potatis"),
                    DropdownItem("Inti", 0),
                    DropdownItem("Intifffffffffssssssssssf", 1)
                )))
                .build()

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