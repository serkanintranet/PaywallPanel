package com.intranet.paywallpanel.ui.main.drawer.paymentgateways

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentPaymentGatewaysBinding
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardLastTransactionsAdapter
import com.intranet.paywallpanel.util.*


class PaymentGatewaysFragment :
    BaseFragment<PaymentGatewaysViewModel, FragmentPaymentGatewaysBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private lateinit var gatewaysAdapter: GatewaysAdapter
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<PaymentGatewaysViewModel> =
        PaymentGatewaysViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentGatewaysBinding =
        FragmentPaymentGatewaysBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUI()
            handleClick()
            initRecyclerView()
            observeLoading()
            observeNetworkErrors()
            observeResponses()
        } catch (ex: Exception) {
            //Ex
        }
    }
    // endregion android lifecycle

    // region UI Process
    private fun setupUI() {
        getGateways()
    }

    private fun handleClick() {

    }

    // region Gateways
    private fun getGateways() {
        viewModel.gateways(preferenceHelper.getToken())
    }

    // endregion Gateways

    // region setupRecyclerView
    private fun initRecyclerView() {

        // region Gateways
        gatewaysAdapter = GatewaysAdapter(
            GatewaysAdapter.OnClickListener { click ->
                if (click?.isConnected == true) {
                    GlobalData.paymentGatewayProviderBodyModel = click
                    activity?.navigateToPaymentGatewayDetail()
                } else {
                    activity?.displayAlert(
                        resources.getString(R.string.go_website_to_connection), StatusType.INFO, false, object : AlertCallback {
                            override fun proceed() {

                            }

                            override fun cancel() {

                            }

                        })
                }
            },
            requireContext(), arrayListOf()
        )

        binding.rvCompanies.apply {
            setHasFixedSize(true)
            adapter = gatewaysAdapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        // endregion Gateways
    }
    // endregion setupRecyclerView

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region Gateways
        viewModel.gatewaysResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.rvCompanies.visibility = View.VISIBLE
                    response.body?.let { it1 -> gatewaysAdapter.updateGatewayProviderModels(it1) }
                } else {
                    activity?.displayAlert(
                        ValidationHandler.errorMessages(
                            requireContext(),
                            response.errorCode ?: 1
                        ), StatusType.ERROR, false, object : AlertCallback {
                            override fun proceed() {

                            }

                            override fun cancel() {

                            }

                        })
                }
            }
        })
        // endregion Gateways
    }

    private fun observeNetworkErrors() {
        viewModel.networkError.observe(viewLifecycleOwner) { error ->
            error?.let {
                // Log.e("userLoginError", error.message.toString())
                ValidationHandler.handleApiErrors(error, requireActivity(), requireContext())
            }
        }
    }

    private fun observeLoading() {
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (loading) {
                    displayProgressBar(true)
                } else {
                    displayProgressBar(false)
                }
            }
        }
    }
    // endregion ObservableData
}