package com.intranet.paywallpanel.ui.main.drawer.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.merchant.MerchantApiInstance
import com.intranet.paywallpanel.network.merchant.model.ProfileBodyModel
import com.intranet.paywallpanel.network.payment.TransferApi
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.TransferBodyModel
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.network.report.ReportApiInstance
import com.intranet.paywallpanel.network.report.model.DashboardBodyModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DashboardViewModel(application: Application) : BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region Dashboard Api Instance
    val dashboardResponse = MutableLiveData<GenericResponseModel<DashboardBodyModel?>?>()
    private val reportApiInstance = ReportApiInstance()

    fun dashboard(token: String){
        try {
            loading.value = true
            disposable.add(
                reportApiInstance.dashboard(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<DashboardBodyModel?>>(){
                        override fun onSuccess(response: GenericResponseModel<DashboardBodyModel?>) {
                            Log.d("dashResponse", response.toString())
                            loading.value = false
                            dashboardResponse.value = response
                            dashboardResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("dashError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("dashError","${e.message.toString()} ")
        }
    }
    // endregion

    // region Transfer Api Instance
    val transferResponse = MutableLiveData<GenericResponseModel<TransferBodyModel?>?>()
    private val transferApiInstance = TransferApiInstance()

    fun transfers(token: String){
        try {
            loading.value = true
            disposable.add(
                transferApiInstance.transfers(token, "0", "20")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<TransferBodyModel?>?>(){
                        override fun onSuccess(response: GenericResponseModel<TransferBodyModel?>) {
                            Log.d("dashTrResponse", response.toString())
                            loading.value = false
                            transferResponse.value = response
                            transferResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("dashTrError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("dashTrError","${e.message.toString()} ")
        }
    }
    // endregion

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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}