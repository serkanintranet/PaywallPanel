package com.intranet.paywallpanel.network.payment

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.model.*
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

class TransferApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val transferApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TransferApi::class.java)

    fun transfers(token: String, start: String, length: String): Single<GenericResponseModel<TransferBodyModel?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        val startMap: Map<String, String> = mapOf(
            Constants.Start to start
        )

        val lengthMap: Map<String, String> = mapOf(
            Constants.Length to length
        )

        return transferApiBuild.transfers(
            bearerToken, startMap, lengthMap
        )
    }

    fun getPaymentProviderDetected(token: String, paymentId: Int): Single<GenericResponseModel<ArrayList<PaymentProviderDetectedModel>?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        val paymentIdMap: Map<String, String> = mapOf(
            Constants.PaymentId to paymentId.toString()
        )

        return transferApiBuild.getPaymentProviderDetected(
            bearerToken, paymentIdMap
        )
    }

    fun getPaymentCommunication(token: String, paymentActivityId: Int): Single<GenericResponseModel<PaymentCommunicationModel?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        val paymentActivityIdMap: Map<String, String> = mapOf(
            Constants.PaymentActivityId to paymentActivityId.toString()
        )

        return transferApiBuild.getPaymentCommunication(
            bearerToken, paymentActivityIdMap
        )
    }

    fun getPaymentActivities(token: String, paymentId: Int): Single<GenericResponseModel<ArrayList<PaymentActivityModel>?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        val paymentIdMap: Map<String, String> = mapOf(
            Constants.PaymentId to paymentId.toString()
        )

        return transferApiBuild.getPaymentActivities(
            bearerToken, paymentIdMap
        )
    }

}