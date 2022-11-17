package com.intranet.paywallpanel.network.merchant

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import com.intranet.paywallpanel.network.merchant.model.PaymentGatewaysBodyModel
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import com.intranet.paywallpanel.util.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MerchantApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val merchantApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MerchantApi::class.java)

    fun profile(token: String): Single<GenericResponseModel<ProfileBodyModel>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return merchantApiBuild.profile(
            bearerToken
        )
    }

    fun paymentGateways(token: String): Single<GenericResponseModel<ArrayList<PaymentGatewaysBodyModel>>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return merchantApiBuild.paymentGateways(
            bearerToken
        )
    }

}