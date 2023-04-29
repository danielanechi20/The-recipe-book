package com.example.licentaincercarea1.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class reteta (
    @SerializedName(value="id")
    var id:String="",
    @SerializedName(value="Nume")
    var Nume:String="",
    @SerializedName(value="Thumb")
    var Thumb:String="",
    @SerializedName(value="In")
    var In:String="",
    @SerializedName(value="P")
    var P:String="",
    @SerializedName(value="Favourite")
    var Favourite:Boolean=false
        ):Parcelable

