package no.iktdev.setting.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class ActionableComponentData(
    val target: Class<*>?,
    override var value: Bundle?
) : ComponentData(), Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(target)
        parcel.writeParcelable(value, flags)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<ActionableComponentData> {
        override fun createFromParcel(parcel: Parcel): ActionableComponentData {
            return ActionableComponentData(
                parcel.readSerializable() as Class<*>?,
                parcel.readParcelable<Bundle>(Bundle::class.java.classLoader)
            )
        }

        override fun newArray(size: Int): Array<ActionableComponentData?> {
            return arrayOfNulls(size)
        }
    }


}