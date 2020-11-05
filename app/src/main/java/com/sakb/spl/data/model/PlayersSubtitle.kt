package com.sakb.spl.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayersSubtitle(
    var oldPlayerLink: String = "",
    var newPlayerLink: String = "",
    var parentPosition: Int = 0,
    var childPosition: Int = 0,
    var oldPlayerId: String = "",
    var newPlayerId: String = "",
    var oldPlayerCost: String = "",
): Parcelable