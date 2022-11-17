package com.intranet.paywallpanel.network.merchant

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.merchant.model.PaymentGatewaysBodyModel
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface MerchantApi {
    @GET("merchant/profile")
    fun profile(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<ProfileBodyModel>>

    @GET("merchant/paymentgateways")
    fun paymentGateways(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<ArrayList<PaymentGatewaysBodyModel>>>
}