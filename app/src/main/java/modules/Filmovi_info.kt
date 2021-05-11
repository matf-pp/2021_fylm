package modules

import android.os.Parcel
import android.os.Parcelable

data class Filmovi_info(
        val id: Int,
        val ime_filma: String?,
        val deskripcija: String?,
        val image: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(ime_filma)
        parcel.writeString(deskripcija)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Filmovi_info> {
        override fun createFromParcel(parcel: Parcel): Filmovi_info {
            return Filmovi_info(parcel)
        }

        override fun newArray(size: Int): Array<Filmovi_info?> {
            return arrayOfNulls(size)
        }
    }
}
