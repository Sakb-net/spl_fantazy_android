package com.sakb.spl.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransfersData (
    var transferFree:Int? =null,
    var changePoint:Int? =null,
    var moneyRemaining:Double? = null,
    var daweryLink:String? = null
):Parcelable