package com.intranet.paywallpanel.ui.main.drawer.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.network.user.UserApi
import com.intranet.paywallpanel.network.user.UserApiInstance
import com.intranet.paywallpanel.network.user.model.ChangePasswordRequestModel
import com.intranet.paywallpanel.network.user.model.LastAttemptsBodyModel
import com.intranet.paywallpanel.network.user.model.UserProfileModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region Profile Api Instance
    val profileResponse = MutableLiveData<GenericResponseModel<UserProfileModel?>?>()
    private val userApiInstance = UserApiInstance()

    fun profile(token: String){
        try {
            loading.value = true
            disposable.add(
                userApiInstance.userProfile(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<UserProfileModel?>>(){
                        override fun onSuccess(response: GenericResponseModel<UserProfileModel?>) {
                            Log.d("ProfileResponse", response.toString())
                            loading.value = false
                            profileResponse.value = response
                            profileResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("ProfileError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("ProfileError","${e.message.toString()} ")
        }
    }
    // endregion

    // region LastAttempts Api Instance
    val lastAttemptsResponse = MutableLiveData<GenericResponseModel<LastAttemptsBodyModel?>?>()

    fun lastAttempts(token: String){
        try {
            loading.value = true
            disposable.add(
                userApiInstance.lastAttempts(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<LastAttemptsBodyModel?>>(){
                        override fun onSuccess(response: GenericResponseModel<LastAttemptsBodyModel?>) {
                            Log.d("LastAttResponse", response.toString())
                            loading.value = false
                            lastAttemptsResponse.value = response
                            lastAttemptsResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("LastAttError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("LastAttError","${e.message.toString()} ")
        }
    }
    // endregion

    // region ChangePassword Api Instance
    val changePasswordResponse = MutableLiveData<GenericResponseModel<String?>?>()

    fun changePassword(token: String, changePasswordRequestModel: ChangePasswordRequestModel){
        try {
            loading.value = true
            disposable.add(
                userApiInstance.changePassword(token, changePasswordRequestModel)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<String?>>(){
                        override fun onSuccess(response: GenericResponseModel<String?>) {
                            loading.value = false
                            changePasswordResponse.value = response
                            changePasswordResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
        }
    }
    // endregion

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}