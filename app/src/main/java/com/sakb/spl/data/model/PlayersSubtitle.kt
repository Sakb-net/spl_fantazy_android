package com.sakb.spl.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayersSubtitle(
    var oldPlayerLink:String,
    var newPlayerLink:String
): Parcelable