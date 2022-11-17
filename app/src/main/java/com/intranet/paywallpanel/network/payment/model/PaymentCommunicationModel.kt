package com.intranet.paywallpanel.network.payment.model

import com.google.gson.annotations.SerializedName

data class PaymentCommunicationModel(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Request")
    var request: String?,
    @SerializedName("RequestTime")
    var requestTime: String?,
    @SerializedName("Response")
    var response: String?,
    @SerializedName("ResponseTime")
    var responseTime: String?,
    @SerializedName("ExecutiveSecond")
    var executiveSecond: Float?,
    @SerializedName("Result")
    var result: Boolean?
)
