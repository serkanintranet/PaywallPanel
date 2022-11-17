package com.intranet.paywallpanel.network.auth

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import com.intranet.paywallpanel.network.auth.model.OTPRequestModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Single<GenericResponseModel<String>>

    @POST("otp/confirm")
    fun otpConfirm(@HeaderMap token: Map<String, String>, @Body otpRequestModel: OTPRequestModel): Single<GenericResponseModel<String?>>
}