package com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intranet.paywallpanel.base.BaseViewModel
import com.intranet.paywallpanel.network.GenericResponseModel
import com.intranet.paywallpanel.network.payment.TransferApiInstance
import com.intranet.paywallpanel.network.payment.model.PaymentActivityModel
import com.intranet.paywallpanel.network.payment.model.PaymentCommunicationModel
import com.intranet.paywallpanel.network.payment.model.PaymentProviderDetectedModel
import com.intranet.paywallpanel.network.report.ReportApiInstance
import com.intranet.paywallpanel.network.report.model.DashboardBodyModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TransferDetailViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val networkError = MutableLiveData<Throwable>()
    private val disposable = CompositeDisposable()

    // region PaymentActivities Api Instance
    val paymentActivitiesResponse = MutableLiveData<GenericResponseModel<ArrayList<PaymentActivityModel>?>?>()
    private val transferApiInstance = TransferApiInstance()

    fun getPaymentActivities(token: String, paymentId: Int){
        try {
            loading.value = true
            disposable.add(
                transferApiInstance.getPaymentActivities(token, paymentId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<ArrayList<PaymentActivityModel>?>>(){
                        override fun onSuccess(response: GenericResponseModel<ArrayList<PaymentActivityModel>?>) {
                            Log.d("activitiesResponse", response.toString())
                            loading.value = false
                            paymentActivitiesResponse.value = response
                            paymentActivitiesResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("activitiesError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("activitiesError","${e.message.toString()} ")
        }
    }
    // endregion PaymentActivities Api Instance

    // region PaymentCommunication Api Instance
    val paymentCommunicationResponse = MutableLiveData<GenericResponseModel<PaymentCommunicationModel?>?>()

    fun getPaymentCommunication(token: String, paymentActivityId: Int){
        try {
            loading.value = true
            disposable.add(
                transferApiInstance.getPaymentCommunication(token, paymentActivityId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<PaymentCommunicationModel?>>(){
                        override fun onSuccess(response: GenericResponseModel<PaymentCommunicationModel?>) {
                            Log.d("communicationResponse", response.toString())
                            loading.value = false
                            paymentCommunicationResponse.value = response
                            paymentCommunicationResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("communicationError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("communicationError","${e.message.toString()} ")
        }
    }
    // endregion PaymentCommunication Api Instance

    // region PaymentCommunication Api Instance
    val paymentProviderDetectedResponse = MutableLiveData<GenericResponseModel<ArrayList<PaymentProviderDetectedModel>?>?>()

    fun getPaymentProviderDetected(token: String, paymentId: Int){
        try {
            loading.value = true
            disposable.add(
                transferApiInstance.getPaymentProviderDetected(token, paymentId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<GenericResponseModel<ArrayList<PaymentProviderDetectedModel>?>>(){
                        override fun onSuccess(response: GenericResponseModel<ArrayList<PaymentProviderDetectedModel>?>) {
                            Log.d("providersResponse", response.toString())
                            loading.value = false
                            paymentProviderDetectedResponse.value = response
                            paymentProviderDetectedResponse.value = null
                        }
                        override fun onError(e: Throwable) {
                            loading.value = false
                            networkError.value = e
                            Log.d("providersError","${e.message.toString()} ")
                        }
                    })
            )
        } catch (e: Exception){
            loading.value = false
            networkError.value = e
            Log.d("providersError","${e.message.toString()} ")
        }
    }
    // endregion PaymentCommunication Api Instance

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}