package no.iktdev.setting.model

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class DropdownComponentItems(
    override val value: ArrayList<DropdownItem>
) : ComponentData(value), Parcelable {

    constructor(vararg items: DropdownItem) : this(ArrayList(items.asList()))

    constructor(items: List<DropdownItem>) : this(ArrayList(items))

    constructor(items: Array<DropdownItem>) : this(ArrayList(items.asList()))

    constructor(parcel: Parcel) : this(
        ArrayList<DropdownItem>().apply {
            parcel.readList(this, DropdownItem::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DropdownComponentItems> {
        override fun createFromParcel(parcel: Parcel): DropdownComponentItems {
            return DropdownComponentItems(parcel)
        }

        override fun newArray(size: Int): Array<DropdownComponentItems?> {
            return arrayOfNulls(size)
        }
    }

}