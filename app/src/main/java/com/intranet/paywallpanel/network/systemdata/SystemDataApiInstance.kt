package com.intranet.paywallpanel.network.systemdata

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.systemdata.model.EndOfDayModel
import com.intranet.paywallpanel.network.systemdata.model.IntegrationWorkgroupModel
import com.intranet.paywallpanel.util.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SystemDataApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val systemDataApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SystemDataApi::class.java)

    fun endOfDay(token: String): Single<GenericResponseModel<ArrayList<EndOfDayModel?>>> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return systemDataApiBuild.endOfDay(
            bearerToken
        )
    }

    fun integrationWorkgroups(token: String): Single<GenericResponseModel<ArrayList<IntegrationWorkgroupModel?>>> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return systemDataApiBuild.integrationWorkgroups(
            bearerToken
        )
    }

}