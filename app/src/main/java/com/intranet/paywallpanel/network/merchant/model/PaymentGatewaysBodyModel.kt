package com.intranet.paywallpanel.network.merchant.model

import com.google.gson.annotations.SerializedName

data class PaymentGatewaysBodyModel(
    @SerializedName("PaymentGatewayId")
    var paymentGatewayId: Int?,
    @SerializedName("PaymentGatewayLogo")
    var paymentGatewayLogo: String?,
    @SerializedName("PaymentGatewayName")
    var paymentGatewayName: String?,
    @SerializedName("ConnectedDateTime")
    var connectedDateTime: String?
)
