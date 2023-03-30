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
    @SerializedName(value="In1")
    var In1:String="",
    @SerializedName(value="In2")
    var In2:String="",
    @SerializedName(value="In3")
    var In3:String="",
    @SerializedName(value="P1")
    var P1:String="",
    @SerializedName(value="P2")
    var P2:String="",
    @SerializedName(value="P3")
    var P3:String="",
    @SerializedName(value="P4")
    var P4:String="",
    @SerializedName(value="P5")
    var P5:String="",
    @SerializedName(value="P6")
    var P6:String="",
    @SerializedName(value="P7")
    var P7:String="",
        ):Parcelable

