package com.intranet.paywallpanel.network.payment

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.model.*
import com.intranet.paywallpanel.network.report.model.DashboardBodyModel
import com.intranet.paywallpanel.network.user.model.*
import io.reactivex.Single
import retrofit2.http.*

interface TransferApi {
    @GET("payment/report")
    fun transfers(@HeaderMap token: Map<String, String>, @HeaderMap start: Map<String, String>, @HeaderMap length: Map<String, String>): Single<GenericResponseModel<TransferBodyModel?>?>

    @GET("paymentgateway/detected")
    fun getPaymentProviderDetected(@HeaderMap token: Map<String, String>, @HeaderMap paymentId: Map<String, String>): Single<GenericResponseModel<ArrayList<PaymentProviderDetectedModel>?>?>

    @GET("paymentcommunication")
    fun getPaymentCommunication(@HeaderMap token: Map<String, String>, @HeaderMap paymentActivityId: Map<String, String>): Single<GenericResponseModel<PaymentCommunicationModel?>?>

    @GET("paymentactivity")
    fun getPaymentActivities(@HeaderMap token: Map<String, String>, @HeaderMap paymentId: Map<String, String>): Single<GenericResponseModel<ArrayList<PaymentActivityModel>?>?>
}