package com.intranet.paywallpanel.ui.landing.otp

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.LoginApiInstance
import com.intranet.paywallpanel.network.auth.model.OTPRequestModel
import com.intranet.paywallpanel.network.user.UserApiInstance
import com.intranet.paywallpanel.network.user.model.ForgotPasswordRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class OtpViewModel(application: Application): BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region OTP Confirm Api Instance
    val otpConfirmResponse = MutableLiveData<GenericResponseModel<String?>?>()
    private val loginApiInstance = LoginApiInstance()

    fun otpConfirm(token: String, otpRequestModel: OTPRequestModel){
        try {
            loading.value = true
            disposable.add(
                loginApiInstance.otpConfirm(token, otpRequestModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<String?>>(){
                        override fun onSuccess(response: GenericResponseModel<String?>) {
                            Log.d("otpConfirmResponse", response.toString())
                            loading.value = false
                            otpConfirmResponse.value = response
                            otpConfirmResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("otpConfirmError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("otpConfirmError","${e.message.toString()} ")
        }
    }
    // endregion OTP Confirm

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}