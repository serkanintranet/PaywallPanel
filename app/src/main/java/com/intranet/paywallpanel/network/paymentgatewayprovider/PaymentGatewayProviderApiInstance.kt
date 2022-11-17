package com.intranet.paywallpanel.network.paymentgatewayprovider

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.merchant.model.PaymentGatewaysBodyModel
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.CommissionsBodyModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.PaymentGatewayProviderBodyModel
import com.intranet.paywallpanel.util.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PaymentGatewayProviderApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val paymentGatewayProviderApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PaymentGatewayProviderApi::class.java)

    fun all(token: String): Single<GenericResponseModel<ArrayList<PaymentGatewayProviderBodyModel?>>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return paymentGatewayProviderApiBuild.all(
            bearerToken
        )
    }

    fun connect(token: String): Single<GenericResponseModel<String?>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return paymentGatewayProviderApiBuild.connect(
            bearerToken
        )
    }

    fun commission(token: String, paymentGatewayProviderId: Int): Single<GenericResponseModel<ArrayList<CommissionsBodyModel?>?>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        val paymentGatewayProviderIdMap: Map<String, String> = mapOf(
            Constants.PaymentGatewayProviderId to paymentGatewayProviderId.toString()
        )

        return paymentGatewayProviderApiBuild.commission(
            bearerToken, paymentGatewayProviderIdMap
        )
    }

}