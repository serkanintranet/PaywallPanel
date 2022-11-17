package com.intranet.paywallpanel.network.payment.model

import com.google.gson.annotations.SerializedName

data class TransferModel(
    @SerializedName("Id")
    var id: Int?,
    @SerializedName("Amount")
    var amount: Float?,
    @SerializedName("Status")
    var status: String?,
    @SerializedName("MerchantUniqueKey")
    var merchantUniqueKey: String?,
    @SerializedName("SuccessCallback")
    var successCallback: String?,
    @SerializedName("UnsuccessCallback")
    var unsuccessCallback: String?,
    @SerializedName("CardNumber")
    var cardNumber: String?,
    @SerializedName("CardOwnerName")
    var cardOwnerName: String?,
    @SerializedName("Installement")
    var installement: String?,
    @SerializedName("PaymentMethod")
    var paymentMethod: String?,
    @SerializedName("Currency")
    var currency: String?,
    @SerializedName("DateTime")
    var dateTime: String?
)
