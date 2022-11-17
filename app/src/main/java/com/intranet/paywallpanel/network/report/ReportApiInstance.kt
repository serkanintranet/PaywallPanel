package com.intranet.paywallpanel.network.report

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.report.model.DashboardBodyModel
import com.intranet.paywallpanel.network.systemdata.model.EndOfDayModel
import com.intranet.paywallpanel.network.systemdata.model.IntegrationWorkgroupModel
import com.intranet.paywallpanel.network.user.model.*
import com.intranet.paywallpanel.util.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ReportApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val reportApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ReportApi::class.java)

    fun dashboard(token: String): Single<GenericResponseModel<DashboardBodyModel?>> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return reportApiBuild.dashboard(
            bearerToken
        )
    }

}