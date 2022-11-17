package com.intranet.paywallpanel.network.systemdata.model

import com.google.gson.annotations.SerializedName

data class IntegrationWorkgroupModel(
    @SerializedName("Id")
    var id: Int,
    @SerializedName("Name")
    var name: String
)
