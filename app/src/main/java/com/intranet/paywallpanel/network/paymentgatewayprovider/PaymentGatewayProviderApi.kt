package com.intranet.paywallpanel.network.paymentgatewayprovider

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.merchant.model.PaymentGatewaysBodyModel
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.CommissionsBodyModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.PaymentGatewayProviderBodyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface PaymentGatewayProviderApi {
    @GET("paymentgatewayprovider")
    fun all(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<ArrayList<PaymentGatewayProviderBodyModel?>>>

    @POST("paymentgatewayprovider/connect")
    fun connect(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<String?>>

    @GET("paymentgatewayprovider/commission")
    fun commission(@HeaderMap token: Map<String, String>, @HeaderMap paymentGatewayProviderId: Map<String, String>): Single<GenericResponseModel<ArrayList<CommissionsBodyModel?>?>>

    @POST("paymentgatewayprovider/commission/sync")
    fun commissionSync(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<PaymentGatewaysBodyModel>>
}