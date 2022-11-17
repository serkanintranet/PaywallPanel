package com.intranet.paywallpanel.network.user.model

import com.google.gson.annotations.SerializedName

data class LastAttemptsBodyModel(
    @SerializedName("Success")
    var success: ArrayList<AttemptsModel>?,
    @SerializedName("Unsuccess")
    var unsuccess: ArrayList<AttemptsModel>?
)