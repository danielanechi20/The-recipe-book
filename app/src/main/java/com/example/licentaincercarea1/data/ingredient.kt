package com.example.licentaincercarea1.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ingredient (
    @SerializedName(value = "Nume")
    var nume: String = "",
    @SerializedName(value = "Cantitate")
    var cantitate: String = ""
): Parcelable