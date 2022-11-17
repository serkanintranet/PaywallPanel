package com.intranet.paywallpanel.network.auth

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import com.intranet.paywallpanel.network.auth.model.OTPRequestModel
import com.intranet.paywallpanel.util.Constants
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val loginApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LoginApi::class.java)

    fun login(
        loginRequestModel: LoginRequestModel
    ): Single<GenericResponseModel<String>> {

        return loginApiBuild.login(
            loginRequestModel
        )
    }

    fun otpConfirm(
        token: String,
        otpRequestModel: OTPRequestModel
    ): Single<GenericResponseModel<String?>> {

        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return loginApiBuild.otpConfirm(bearerToken,
            otpRequestModel
        )
    }
}