package no.iktdev.setting.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

open class ComponentData(
    open val value: Any? = null
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(ClassLoader.getSystemClassLoader())) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComponentData> {
        override fun createFromParcel(parcel: Parcel): ComponentData {
            return ComponentData(parcel)
        }

        override fun newArray(size: Int): Array<ComponentData?> {
            return arrayOfNulls(size)
        }
    }
}