package com.intranet.paywallpanel.network

import com.google.gson.annotations.SerializedName

data class GenericResponseModel<T>(
    @SerializedName("ErrorCode")
    var errorCode: Int?,
    @SerializedName("Result")
    var result: Boolean?,
    @SerializedName("Message")
    var message: String?,
    @SerializedName("Body")
    var body: T?
)
