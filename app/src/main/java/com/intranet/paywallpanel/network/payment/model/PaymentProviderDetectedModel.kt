package com.intranet.paywallpanel.network.payment.model

import com.google.gson.annotations.SerializedName

data class PaymentProviderDetectedModel(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("PaymentGatewayProviderName")
    var paymentGatewayProviderName: String?,
    @SerializedName("PaymentGatewayProviderLogo")
    var paymentGatewayProviderLogo: String?,
    @SerializedName("Commission")
    var commission: Float?
)
