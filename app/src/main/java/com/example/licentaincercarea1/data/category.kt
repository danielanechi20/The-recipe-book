package com.example.licentaincercarea1.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class category (
    @SerializedName(value="id")
    var id:String="",
    @SerializedName(value="name")
    var name:String="",
    @SerializedName(value="image")
    var image:String="",
    @SerializedName(value="desc")
    var description:String="",
):Parcelable