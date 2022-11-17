package com.intranet.paywallpanel.ui.landing.forgotpassword

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.user.UserApiInstance
import com.intranet.paywallpanel.network.user.model.ForgotPasswordRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ForgotPasswordViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region ForgotPasswordRequest Api Instance
    val forgetPasswordRequestResponse = MutableLiveData<GenericResponseModel<String?>?>()
    private val userApiInstance = UserApiInstance()

    fun forgetPasswordRequest(token: String, forgotPasswordRequestModel: ForgotPasswordRequestModel){
        try {
            loading.value = true
            disposable.add(
                userApiInstance.forgotPasswordRequest(token, forgotPasswordRequestModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<String?>>(){
                        override fun onSuccess(response: GenericResponseModel<String?>) {
                            Log.d("frgtPwdResponse", response.toString())
                            loading.value = false
                            forgetPasswordRequestResponse.value = response
                            forgetPasswordRequestResponse.value = null
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
    // endregion ForgotPasswordRequest

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}