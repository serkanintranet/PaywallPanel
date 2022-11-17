package com.intranet.paywallpanel.network.report.model

import com.google.gson.annotations.SerializedName

data class PeriodicallyTransferModel(
    @SerializedName("PeriodHour")
    var periodHour: Int?,
    @SerializedName("Successful")
    var successful: Int?,
    @SerializedName("Unsuccessful")
    var unsuccessful: Int?
)
