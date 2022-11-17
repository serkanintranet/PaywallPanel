package com.intranet.paywallpanel.ui.main.drawer.paymentgateways

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.PaymentGatewayProviderApiInstance
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.PaymentGatewayProviderBodyModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PaymentGatewaysViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region Gateways Api Instance
    val gatewaysResponse = MutableLiveData<GenericResponseModel<ArrayList<PaymentGatewayProviderBodyModel?>>?>()
    private val paymentGatewayProviderApiInstance = PaymentGatewayProviderApiInstance()

    fun gateways(token: String){
        try {
            loading.value = true
            disposable.add(
                paymentGatewayProviderApiInstance.all(token)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<ArrayList<PaymentGatewayProviderBodyModel?>>>(){
                        override fun onSuccess(response: GenericResponseModel<ArrayList<PaymentGatewayProviderBodyModel?>>) {
                            Log.d("GateResponse", response.toString())
                            loading.value = false
                            gatewaysResponse.value = response
                            gatewaysResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("GateError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("GateError","${e.message.toString()} ")
        }
    }
    // endregion

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}