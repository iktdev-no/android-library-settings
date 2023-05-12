Settings library

# Building Settings
In this library, settings are composed in Groups, then Key and Value
To keep the settings organized and easily accessible, the setting is encapsulated by a sealed class, which is the group (you can also use KeyBasedSetting as well).
In order to compose a setting using this library you will start with this:
```kotlin
sealed class MySetting(key: String) : GroupBasedSetting(group = "MyGroup", key = key)
```
This will let you access your settings by `MySetting.<your setting class>` <br />
So to create a setting that you can use, access and read:
```kotlin
sealed class MySetting(key: String) : GroupBasedSetting(group = "MyGroup", key = key) {
    
    class FancyMode : MySetting("Enable fancy mode")

}
```
In this case we create a setting FancyMode with the `SharedPreference` key "Enable fancy mode" <br />

You can now access the setting by `MySetting.FancyMode()` </br>
From there you can decide what you want it to be. <br />
For now you can get the values directly, or you can make it return a <strong>SettingObject</strong> that you can share with the library or among your own components. This can be done by `.asReactive()` or `.asStatic()` 


<br />
<br />

# Automatically generate setting components

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