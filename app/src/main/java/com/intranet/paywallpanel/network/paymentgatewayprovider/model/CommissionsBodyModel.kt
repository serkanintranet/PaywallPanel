package com.intranet.paywallpanel.network.paymentgatewayprovider.model

import com.google.gson.annotations.SerializedName

data class CommissionsBodyModel(
    @SerializedName("CardFamilyId")
    var cardFamilyId: Int?,
    @SerializedName("CardFamilyName")
    var cardFamilyName: String?,
    @SerializedName("Commissions")
    var commissions: ArrayList<CommissionModel>?
)
