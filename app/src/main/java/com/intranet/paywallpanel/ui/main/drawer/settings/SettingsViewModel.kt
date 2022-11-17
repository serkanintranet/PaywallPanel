package com.intranet.paywallpanel.ui.main.drawer.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.merchant.MerchantApiInstance
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import com.intranet.paywallpanel.network.user.UserApiInstance
import com.intranet.paywallpanel.network.user.model.UserProfileModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SettingsViewModel(application: Application): BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region Merchant Api Instance
    val profileResponse = MutableLiveData<GenericResponseModel<ProfileBodyModel>?>()
    private val merchantApiInstance = MerchantApiInstance()

    fun profile(token: String){
        try {
            loading.value = true
            disposable.add(
                merchantApiInstance.profile(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<ProfileBodyModel>>(){
                        override fun onSuccess(response: GenericResponseModel<ProfileBodyModel>) {
                            Log.d("MProfileResponse", response.toString())
                            loading.value = false
                            profileResponse.value = response
                            profileResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("MProfileError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("MProfileError","${e.message.toString()} ")
        }
    }
    // endregion
}