package com.intranet.paywallpanel.ui.main.drawer.transfers

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.TransferBodyModel
import com.intranet.paywallpanel.network.payment.model.TransferModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TransfersViewModel(application: Application): BaseViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region Transfer Api Instance
    val transferResponse = MutableLiveData<GenericResponseModel<TransferBodyModel?>?>()
    private val transferApiInstance = TransferApiInstance()

    fun transfers(token: String, start: Int, length: Int){
        try {
            loading.value = true
            disposable.add(
                transferApiInstance.transfers(token, start.toString(), length.toString())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<TransferBodyModel?>>(){
                        override fun onSuccess(response: GenericResponseModel<TransferBodyModel?>) {
                            Log.d("TrResponse", response.toString())
                            loading.value = false
                            transferResponse.value = response
                            transferResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("TrError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("TrError","${e.message.toString()} ")
        }
    }
    // endregion

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}