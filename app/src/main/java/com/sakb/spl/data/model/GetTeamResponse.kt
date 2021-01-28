package com.sakb.spl.data.model

import android.os.Parcel
import android.os.Parcelable

data class GetTeamResponse(
    var Message: String? = "",
    var StatusCode: Int? = 0,
    var `data`: MutableList<Data?>? = mutableListOf(),
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    data class Data(
        var link: String? = "",
        var name: String? = "",
        var isChoice: Boolean = false,
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(link)
            parcel.writeString(name)
            parcel.writeByte(if (isChoice) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Data> {
            override fun createFromParcel(parcel: Parcel): Data {
                return Data(parcel)
            }

            override fun newArray(size: Int): Array<Data?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Message)
        parcel.writeValue(StatusCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GetTeamResponse> {
        override fun createFromParcel(parcel: Parcel): GetTeamResponse {
            return GetTeamResponse(parcel)
        }

        override fun newArray(size: Int): Array<GetTeamResponse?> {
            return arrayOfNulls(size)
        }
    }
}