package com.intranet.paywallpanel.network.paymentgatewayprovider.model

import com.google.gson.annotations.SerializedName

data class AuthParams(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Name")
    var name: String
)
