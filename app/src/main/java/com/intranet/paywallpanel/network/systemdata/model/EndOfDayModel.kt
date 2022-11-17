package com.intranet.paywallpanel.network.systemdata.model

import com.google.gson.annotations.SerializedName

data class EndOfDayModel(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Name")
    var name: String,
    @SerializedName("Day")
    var day: Int
)