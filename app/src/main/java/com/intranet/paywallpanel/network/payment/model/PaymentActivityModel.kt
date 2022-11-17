package com.intranet.paywallpanel.network.payment.model

import com.google.gson.annotations.SerializedName

data class PaymentActivityModel(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("PaymentGatewayId")
    var paymentGatewayId: Int?,
    @SerializedName("PaymentGatewayProviderId")
    var paymentGatewayProviderId: Int?,
    @SerializedName("PaymentGatewayProviderName")
    var paymentGatewayProviderName: String?,
    @SerializedName("PaymentGatewayProviderLogo")
    var paymentGatewayProviderLogo: String?,
    @SerializedName("ProvisionNumber")
    var provisionNumber: String?,
    @SerializedName("PaymentStatusId")
    var paymentStatusId: Int?,
    @SerializedName("PaymentStatusName")
    var paymentStatusName: String?,
    @SerializedName("DateTime")
    var dateTime: String?
)
