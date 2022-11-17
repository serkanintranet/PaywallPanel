package com.intranet.paywallpanel.network.report

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.report.model.DashboardBodyModel
import com.intranet.paywallpanel.network.user.model.*
import io.reactivex.Single
import retrofit2.http.*

interface ReportApi {
    @GET("dashboard/v1")
    fun dashboard(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<DashboardBodyModel?>>
}