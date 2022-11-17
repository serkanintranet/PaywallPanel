package com.intranet.paywallpanel.ui.landing.changepasswordforget

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.LoginApiInstance
import com.intranet.paywallpanel.network.auth.model.OTPRequestModel
import com.intranet.paywallpanel.network.user.UserApiInstance
import com.intranet.paywallpanel.network.user.model.ForgotPasswordRequestConfirmModel
import com.intranet.paywallpanel.network.user.model.ForgotPasswordRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ChangePasswordForgetViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region forgotPasswordRequestConfirm Api Instance
    val forgotPasswordRequestConfirmResponse = MutableLiveData<GenericResponseModel<String?>?>()
    private val userApiInstance = UserApiInstance()

    fun forgetPasswordconfirm(token: String, forgotPasswordRequestConfirmModel: ForgotPasswordRequestConfirmModel){
        try {
            loading.value = true
            disposable.add(
                userApiInstance.forgotPasswordRequestConfirm(token, forgotPasswordRequestConfirmModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<String?>>(){
                        override fun onSuccess(response: GenericResponseModel<String?>) {
                            Log.d("frgtPwdResponse", response.toString())
                            loading.value = false
                            forgotPasswordRequestConfirmResponse.value = response
                            forgotPasswordRequestConfirmResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("frgtPwdError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("frgtPwdError","${e.message.toString()} ")
        }
    }
    // endregion forgotPasswordRequestConfirm

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}