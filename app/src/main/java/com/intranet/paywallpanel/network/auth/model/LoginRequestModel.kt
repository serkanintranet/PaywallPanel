package com.intranet.paywallpanel.network.auth.model

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("Email")
    var email: String?,
    @SerializedName("Password")
    var password: String?
)
