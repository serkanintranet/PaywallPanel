package com.intranet.paywallpanel.network.user.model

import com.google.gson.annotations.SerializedName

data class AttemptsModel(
    @SerializedName("Date")
    var date: String?,
    @SerializedName("IPAdrdress")
    var ipAdrdress: String?
)
