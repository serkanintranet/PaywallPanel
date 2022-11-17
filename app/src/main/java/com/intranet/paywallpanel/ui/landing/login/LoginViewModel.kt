package com.intranet.paywallpanel.ui.landing.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.auth.LoginApiInstance
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application): BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()

    val loginResponse = MutableLiveData<GenericResponseModel<String>?>()
    private val loginApiInstance = LoginApiInstance()
    private val disposable = CompositeDisposable()

    fun login(loginRequestModel: LoginRequestModel){
        try {
            Log.d("loginRequestModel", loginRequestModel.toString())
            loading.value = true
            disposable.add(
                loginApiInstance.login(loginRequestModel = loginRequestModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<String>>(){
                        override fun onSuccess(response: GenericResponseModel<String>) {
                            Log.d("loginResponse", response.toString())
                            loading.value = false
                            loginResponse.value = response
                            loginResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("userLoginError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("userLoginError","${e.message.toString()} ")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}