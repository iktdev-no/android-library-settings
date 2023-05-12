package no.iktdev.setting.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * @param value Should only be value of primitive type (int, string, boolean, float etc..)
 * @param payload Value that can be assigned if you want to inform host activity of the item selected
 */
class DropdownItem(val displayValue: String, override var value: Any?, val payload: Serializable? = null) : ComponentData(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readValue(ClassLoader.getSystemClassLoader()),
        parcel.readSerializable()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(displayValue)
        parcel.writeValue(value)
        parcel.writeSerializable(payload)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DropdownItem> {
        override fun createFromParcel(parcel: Parcel): DropdownItem {
            return DropdownItem(parcel)
        }

        override fun newArray(size: Int): Array<DropdownItem?> {
            return arrayOfNulls(size)
        }
    }
}