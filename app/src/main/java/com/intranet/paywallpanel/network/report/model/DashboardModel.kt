package com.intranet.paywallpanel.network.report.model

import com.google.gson.annotations.SerializedName

data class DashboardModel(
    @SerializedName("PeriodicallyTransfers")
    var periodicallyTransfers: ArrayList<PeriodicallyTransferModel>?,
    @SerializedName("DailyTransferAmount")
    var dailyTransferAmount: Float?,
    @SerializedName("DailyTransferCount")
    var dailyTransferCount: Int?,
    @SerializedName("DailySuccessfulTransferAmount")
    var dailySuccessfulTransferAmount: Float?,
    @SerializedName("DailySuccessfulTransferCount")
    var dailySuccessfulTransferCount: Int?,
    @SerializedName("DailyUnsuccessfulTransferAmount")
    var dailyUnsuccessfulTransferAmount: Float?,
    @SerializedName("DailyUnsuccessfulTransferCount")
    var dailyUnsuccessfulTransferCount: Int?,
    @SerializedName("DailyRefundCount")
    var dailyRefundCount: Int?,
    @SerializedName("DailyCancelCount")
    var dailyCancelCount: Int?
)
