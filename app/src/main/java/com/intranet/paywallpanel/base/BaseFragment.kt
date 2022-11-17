package com.elekse.esnekpos.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.intranet.paywallpanel.base.ViewModelFactory
import com.intranet.paywallpanel.util.GlobalData

abstract class BaseFragment<VM: ViewModel, B: ViewBinding>: Fragment() {


    private var loadingDialog: Dialog? = null

    protected lateinit var binding: B
    protected lateinit var viewModel: VM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    fun hideKeyboard(){
        try {
            if (activity?.currentFocus != null) {
                val inputMethodManager = context?.getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager
                inputMethodManager
                    .hideSoftInputFromWindow(activity?.currentFocus!!.windowToken, 0)
            }
        } catch (e: Exception){
            //Firebase.crashlytics.recordException(e)
        }
    }

    fun displayProgressBar(display: Boolean) {
        try {
            if(display) {
                showLoading()
            } else { hideLoading()}
        } catch (e: Exception){
           // Firebase.crashlytics.recordException(e)
        }
    }

    private fun showLoading() {
        try {
            hideLoading()
            loadingDialog = context?.let { GlobalData.showLoadingDialog(it) }
        } catch (e: Exception){
            //Firebase.crashlytics.recordException(e)
        }
    }

    private fun hideLoading() {
        try {
            loadingDialog?.let { if(it.isShowing)it.cancel() }
        } catch (e: Exception){
            //Firebase.crashlytics.recordException(e)
        }
    }

    fun checkConnectivity(){
        try {
            if (!GlobalData.checkForInternet(requireContext())){
                //activity?.displayDialog(resources.getString(com.elekse.hoppawalletapp.R.string.network_error_message),  StatusType.ERROR,null, null, false)
            }
        } catch (e: Exception){
            //Firebase.crashlytics.recordException(e)
        }
    }

}