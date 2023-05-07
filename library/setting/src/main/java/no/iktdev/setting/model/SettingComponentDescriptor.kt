package no.iktdev.setting.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import no.iktdev.setting.access.SettingDefined
import java.io.Serializable

data class SettingComponentDescriptor(
    @DrawableRes val icon: Int?,
    @StringRes override val groupName: Int,
    override val title: String,
    val description: String? = null,
    val payload: ComponentData? = null,
    val setting: SettingDefined? = null,
    override val type: SettingComponentType = SettingComponentType.CLICKABLE
    ) : SettingComponentDescriptorBase(groupName, title, type) {
        fun hasDescription(): Boolean = description != null
    }

data class SettingComponentSimpleDescriptor(
    @StringRes override val groupName: Int,
    override val title: String,
    override val type: SettingComponentType = SettingComponentType.CLICKABLE
): SettingComponentDescriptorBase(groupName, title, type)

open class SettingComponentDescriptorBase(
    @StringRes open val groupName: Int,
    open val title: String,
    open val type: SettingComponentType = SettingComponentType.CLICKABLE
): Serializable

class SettingComponentInvalidValueException(override val message: String): RuntimeException()
