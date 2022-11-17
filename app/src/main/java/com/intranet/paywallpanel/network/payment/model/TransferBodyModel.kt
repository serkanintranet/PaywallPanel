package com.intranet.paywallpanel.network.payment.model

import com.google.gson.annotations.SerializedName

data class TransferBodyModel(
    @SerializedName("Data")
    var data: ArrayList<TransferModel>?,
    @SerializedName("TotalRecord")
    var totalRecord: Int?
)
