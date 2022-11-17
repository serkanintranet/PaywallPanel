package com.intranet.paywallpanel.network.user.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestModel(
    @SerializedName("Email")
    var email: String?
)
