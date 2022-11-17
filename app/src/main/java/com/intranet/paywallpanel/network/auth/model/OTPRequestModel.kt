package com.intranet.paywallpanel.network.auth.model

import com.google.gson.annotations.SerializedName

data class OTPRequestModel(
    @SerializedName("OTPCaptcha")
    var OTPCaptcha: String?,
    @SerializedName("OTP")
    var OTP: String?
)
