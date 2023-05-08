package no.iktdev.setting.model.builder

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import no.iktdev.setting.access.SettingAccess
import no.iktdev.setting.model.ComponentData
import no.iktdev.setting.model.SettingComponentDescriptor
import no.iktdev.setting.model.SettingComponentInvalidValueException
import no.iktdev.setting.model.SettingComponentType

class SettingComponentDescriptorBuilder(val context: Context) {

    @StringRes private var groupName: Int? = null
    @DrawableRes private var icon: Int? = null
    private var title: String? = null
    private var description: String? = null
    private var type: SettingComponentType = SettingComponentType.CLICKABLE
    private var setting: SettingAccess? = null
    private var payload: ComponentData? = null

    fun setGroupName(@StringRes title: Int): SettingComponentDescriptorBuilder {
        this.groupName = title
        return this
    }

    fun setIcon(@DrawableRes icon: Int): SettingComponentDescriptorBuilder {
        this.icon = icon
        return this
    }

    fun setTitle(@StringRes title: Int): SettingComponentDescriptorBuilder {
        this.title = context.getString(title)
        return this
    }

    fun setTitle(title: String): SettingComponentDescriptorBuilder {
        this.title = title
        return this
    }

    fun setDescription(@StringRes description: Int): SettingComponentDescriptorBuilder {
        this.description = context.getString(description)
        return this
    }

    fun setDescription(description: String): SettingComponentDescriptorBuilder {
        this.description = description
        return this
    }

    fun setType(type: SettingComponentType): SettingComponentDescriptorBuilder {
        this.type = type
        return this
    }

    fun setSetting(setting: SettingAccess): SettingComponentDescriptorBuilder {
        this.setting = setting
        return this
    }

    fun setPayload(payload: ComponentData): SettingComponentDescriptorBuilder {
        this.payload = payload
        return this
    }

    private fun requiresIcon(type: SettingComponentType): Boolean {
        return listOf<SettingComponentType>(
            SettingComponentType.CLICKABLE,
            SettingComponentType.LAUNCH,
            SettingComponentType.SWITCH
        ).find { it == type } != null
    }

    private fun requiresSetting(type: SettingComponentType): Boolean {
        return listOf<SettingComponentType>(
            SettingComponentType.POPOUT_SELECT,
            SettingComponentType.DROPDOWN,
            SettingComponentType.SLIDER,
            SettingComponentType.SWITCH
        ).find { it == type } != null
    }

    fun build(): SettingComponentDescriptor {
        return SettingComponentDescriptor(
            icon = icon ?: if (!requiresIcon(type)) null else throw SettingComponentInvalidValueException("No icon was provided to builder"),
            title = title ?: throw SettingComponentInvalidValueException("No title was provided to builder"),
            description = description,
            type = type,
            groupName = groupName ?: throw SettingComponentInvalidValueException("No title was provided to builder"),
            setting = setting ?: if (!requiresSetting(type)) null else throw SettingComponentInvalidValueException("Expected Setting Instance, this was not provided"),
            payload = payload
        )
    }
}