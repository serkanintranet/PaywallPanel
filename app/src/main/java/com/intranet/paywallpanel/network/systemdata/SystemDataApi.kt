package com.intranet.paywallpanel.network.systemdata

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.systemdata.model.EndOfDayModel
import com.intranet.paywallpanel.network.systemdata.model.IntegrationWorkgroupModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface SystemDataApi {
    @GET("systemdata/endofdays")
    fun endOfDay(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<ArrayList<EndOfDayModel?>>>

    @GET("systemdata/integrationworkgroups")
    fun integrationWorkgroups(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<ArrayList<IntegrationWorkgroupModel?>>>

}