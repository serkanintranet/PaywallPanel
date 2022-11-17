package com.intranet.paywallpanel.network.paymentgatewayprovider.model

import com.google.gson.annotations.SerializedName

data class CommissionModel(
    @SerializedName("InstallementId")
    var installementId: Int?,
    @SerializedName("InstallementName")
    var installementName: String?,
    @SerializedName("CommissionId")
    var commissionId: Int?,
    @SerializedName("Commission")
    var commission: Float?
)
