package com.intranet.paywallpanel.network.report.model

import com.google.gson.annotations.SerializedName

data class DashboardBodyModel(
    @SerializedName("v1")
    var v1: DashboardModel?
)
