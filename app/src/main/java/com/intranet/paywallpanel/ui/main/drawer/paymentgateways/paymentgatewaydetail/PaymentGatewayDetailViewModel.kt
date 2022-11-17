package com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.PaymentActivityModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.PaymentGatewayProviderApiInstance
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.CommissionsBodyModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PaymentGatewayDetailViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region PaymentGatewayProviderApiInstance
    val commissionResponse = MutableLiveData<GenericResponseModel<ArrayList<CommissionsBodyModel?>?>?>()
    private val paymentGatewayProviderApiInstance = PaymentGatewayProviderApiInstance()

    fun getCommission(token: String, paymentGatewayProviderId: Int){
        try {
            loading.value = true
            disposable.add(
                paymentGatewayProviderApiInstance.commission(token, paymentGatewayProviderId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<ArrayList<CommissionsBodyModel?>?>>(){
                        override fun onSuccess(response: GenericResponseModel<ArrayList<CommissionsBodyModel?>?>) {
                            Log.d("commissionResponse", response.toString())
                            loading.value = false
                            commissionResponse.value = response
                            commissionResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("commissionError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("commissionError","${e.message.toString()} ")
        }
    }
    // endregion PaymentGatewayProviderApiInstance
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}