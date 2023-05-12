package no.iktdev.setting.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class LaunchableComponentData(
    val uri: String?,
    override var value: Bundle?
): ComponentData(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable<Bundle>(Bundle::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeParcelable(value, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaunchableComponentData> {
        override fun createFromParcel(parcel: Parcel): LaunchableComponentData {
            return LaunchableComponentData(parcel)
        }

        override fun newArray(size: Int): Array<LaunchableComponentData?> {
            return arrayOfNulls(size)
        }
    }
}