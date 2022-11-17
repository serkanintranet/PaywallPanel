package com.intranet.paywallpanel.network.paymentgatewayprovider.model

import com.google.gson.annotations.SerializedName

data class PaymentGatewayProviderBodyModel(
    @SerializedName("Id")
    var id: Int?,
    @SerializedName("IsConnected")
    var isConnected: Boolean?,
    @SerializedName("Name")
    var name: String?,
    @SerializedName("LogoUrl")
    var logoUrl: String?,
    @SerializedName("CardStorage")
    var cardStorage: Boolean?,
    @SerializedName("DirectPayment")
    var directPayment: Boolean?,
    @SerializedName("ThreeDPayment")
    var threeDPayment: Boolean?,
    @SerializedName("Introduction")
    var introduction: String?,
    @SerializedName("AuthParams")
    var authParams: ArrayList<AuthParams>?
)
