Settings library

# Automatically generate settings components

## Setup Activity
1. Make the activity extend: SettingsRenderActivity
```kotlin
class SettingsActivity : SettingsRenderActivity()
```
SettingsRenderActivity is based on AppCompatActivity

2. Add a Linear Layout in the layout file
3. return the Linear Layout in the `getRenderContainer()` function
If you want to make the layout render your created setting components, override the function `preCreatedSettingItems()` and make it return your list of settings


## Automatic render
`preCreatedSettingItems` requires that you return an arrayListOf `SettingComponentDescriptorBase` <br>
All automatically generated and rendered settings ui components are based upon a `SettingComponentDescriptorBuilder`
and is created like this
```kotlin
    SettingComponentDescriptorBuilder(this)
        .setGroupName(no.iktdev.demoapplication.R.string.setting_group_title_switchy)
        .setTitle("Switch 1")
        .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
        .setType(SettingComponentType.SWITCH)
        .setSetting(Settings.SwitchTest.ImFancyNoOne())
        .build()
```
About `SettingComponentDescriptorBuilder`
```python
SettingComponentDescriptorBuilder(this): where "this" is context
GroupName: is String Resource
Title: is String Resource or String
Icon: is Drawable Resource Id
Type: is the type to be generated
Setting: is either StaticSetting or ReactiveSetting. default is StaticSetting
```
Current types that are supported for auto-gen is:
- Clickable
- Switch (toggle switch)
- Launch (starts external page)
- Dropdown (creates a spinner)
- Popout (creates a dropdown)
- Slider (numeric)



### Single level

```kotlin
    arrayListOf<SettingComponentDescriptorBase>(
            SettingComponentDescriptorBuilder(this)
                .setGroupName(no.iktdev.demoapplication.R.string.setting_group_title_switchy)
                .setTitle("Switch 1")
                .setIcon(no.iktdev.setting.R.drawable.ic_android_black_24dp)
                .setType(SettingComponentType.SWITCH)
                .setSetting(Settings.SwitchTest.ImFancyNoOne())
                .build()
    )

```

### Multi level
```kotlin
    arrayListOf<SettingComponentDescriptorBase>(
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
                .build()
    )

```