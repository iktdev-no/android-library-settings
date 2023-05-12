package no.iktdev.setting.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import no.iktdev.setting.access.SettingAccess
import no.iktdev.setting.access.SettingReference
import java.io.Serializable

data class SettingComponentDescriptor(
    @DrawableRes val icon: Int?,
    @StringRes override val groupName: Int,
    override val title: String,
    val description: String? = null,
    val payload: ComponentData? = null,
    val setting: SettingAccess? = null,
    override val type: SettingComponentType = SettingComponentType.CLICKABLE
) : SettingComponentDescriptorBase(groupName, title, type), Parcelable {
    fun hasDescription(): Boolean = description != null


    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readParcelable<ComponentData>(ComponentData::class.java.classLoader),
        (parcel.readSerializable() as? SettingReference)?.trueInstance(),
        SettingComponentType.valueOf(parcel.readString() ?: SettingComponentType.CLICKABLE.name)
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(icon)
        parcel.writeValue(groupName)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(payload, flags)
        parcel.writeSerializable(setting?.toReference())
        parcel.writeString(type.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SettingComponentDescriptor> {
        override fun createFromParcel(parcel: Parcel): SettingComponentDescriptor {
            return SettingComponentDescriptor(parcel)
        }

        override fun newArray(size: Int): Array<SettingComponentDescriptor?> {
            return arrayOfNulls(size)
        }
    }

}

data class SettingComponentSimpleDescriptor(
    @StringRes override val groupName: Int,
    override val title: String,
    override val type: SettingComponentType = SettingComponentType.CLICKABLE
) : SettingComponentDescriptorBase(groupName, title, type)

open class SettingComponentDescriptorBase(
    @StringRes open val groupName: Int,
    open val title: String,
    open val type: SettingComponentType = SettingComponentType.CLICKABLE
) : Serializable

class SettingComponentInvalidValueException(override val message: String) : RuntimeException()
