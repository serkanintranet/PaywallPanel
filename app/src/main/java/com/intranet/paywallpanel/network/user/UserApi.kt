package com.intranet.paywallpanel.network.user

import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.user.model.*
import io.reactivex.Single
import retrofit2.http.*

interface UserApi {
    @GET("user/profile")
    fun userProfile(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<UserProfileModel?>>

    @GET("user/lastattempts")
    fun lastAttempts(@HeaderMap token: Map<String, String>): Single<GenericResponseModel<LastAttemptsBodyModel?>>

    @PUT("user/changepassword")
    fun changePassword(@HeaderMap token: Map<String, String>, @Body changePasswordRequestModel: ChangePasswordRequestModel): Single<GenericResponseModel<String?>?>

    @POST("user/forgotpassword/request")
    fun forgotPasswordRequest(@HeaderMap token: Map<String, String>, @Body forgotPasswordRequestModel: ForgotPasswordRequestModel): Single<GenericResponseModel<String?>?>

    @PUT("user/forgotpassword/confirm")
    fun forgotPasswordRequestConfirm(@HeaderMap token: Map<String, String>, @Body forgotPasswordRequestConfirmModel: ForgotPasswordRequestConfirmModel): Single<GenericResponseModel<String?>?>
}