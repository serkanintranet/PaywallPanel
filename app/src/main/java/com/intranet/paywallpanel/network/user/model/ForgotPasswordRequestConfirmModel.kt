package com.intranet.paywallpanel.network.user.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequestConfirmModel(
    @SerializedName("NewPassword")
    var newPassword: String?,
    @SerializedName("ReNewPassword")
    var reNewPassword: String?
)
