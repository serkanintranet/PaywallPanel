package com.intranet.paywallpanel.network.user

import com.intranet.paywallpanel.network.GenericResponseModel
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

class UserApiInstance {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    private val userApiBuild =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UserApi::class.java)

    fun userProfile(token: String): Single<GenericResponseModel<UserProfileModel?>> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return userApiBuild.userProfile(
            bearerToken
        )
    }

    fun lastAttempts(token: String): Single<GenericResponseModel<LastAttemptsBodyModel?>> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return userApiBuild.lastAttempts(
            bearerToken
        )
    }

    fun changePassword(token: String, changePasswordRequestModel: ChangePasswordRequestModel): Single<GenericResponseModel<String?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return userApiBuild.changePassword(
            bearerToken, changePasswordRequestModel
        )
    }

    fun forgotPasswordRequest(token: String, forgotPasswordRequestModel: ForgotPasswordRequestModel): Single<GenericResponseModel<String?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return userApiBuild.forgotPasswordRequest(
            bearerToken, forgotPasswordRequestModel
        )
    }

    fun forgotPasswordRequestConfirm(token: String, forgotPasswordRequestConfirmModel: ForgotPasswordRequestConfirmModel): Single<GenericResponseModel<String?>?> {
        val bearerToken: Map<String, String> = mapOf(
            Constants.Authorization to Constants.Bearer + " " + token
        )

        return userApiBuild.forgotPasswordRequestConfirm(
            bearerToken, forgotPasswordRequestConfirmModel
        )
    }

}