package com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.elekse.esnekpos.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentPaymentGatewayDetailBinding
import com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail.adapters.CommissionInformationAdapter
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.TransferDetailFragment
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.adapters.PaymentProviderDetectedAdapter
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.adapters.ProcessStepsAdapter
import com.intranet.paywallpanel.util.*


class PaymentGatewayDetailFragment :
    BaseFragment<PaymentGatewayDetailViewModel, FragmentPaymentGatewayDetailBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private val TAG: String = PaymentGatewayDetailFragment::class.java.simpleName
    private lateinit var commissionInformationAdapter: CommissionInformationAdapter
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<PaymentGatewayDetailViewModel> =
        PaymentGatewayDetailViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentGatewayDetailBinding =
        FragmentPaymentGatewayDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUI()
            handleClick()
            observeLoading()
            observeResponses()
            observeNetworkErrors()
            initRecyclerView()
        } catch (ex: Exception) {
            Log.e(TAG, ex.message.toString())
        }
    }
    // endregion android lifecycle

    // region UI Process
    private fun setupUI() {
        binding.include.tvHeader.text = resources.getString(R.string.transfer_detail)
        getCommissions()

        binding.tvCompanyName.text = GlobalData.paymentGatewayProviderBodyModel?.name
        Glide.with(requireActivity()).load(GlobalData.paymentGatewayProviderBodyModel?.logoUrl)
            .into(binding.ivCompany)

        if (GlobalData.paymentGatewayProviderBodyModel?.cardStorage == true) {
            binding.tvCardStorage.text = resources.getString(R.string.active)
        } else {
            binding.tvCardStorage.text = resources.getString(R.string.inactive)
        }

        if (GlobalData.paymentGatewayProviderBodyModel?.threeDPayment == true) {
            binding.tv3DPayment.text = resources.getString(R.string.active)
        } else {
            binding.tv3DPayment.text = resources.getString(R.string.inactive)
        }

        if (GlobalData.paymentGatewayProviderBodyModel?.directPayment == true) {
            binding.tvDirectPayment.text = resources.getString(R.string.active)
        } else {
            binding.tvDirectPayment.text = resources.getString(R.string.inactive)
        }
    }

    private fun handleClick() {
        binding.include.rlBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    // region Commissions
    private fun getCommissions() {
        GlobalData.paymentGatewayProviderBodyModel?.id?.let {
            viewModel.getCommission(preferenceHelper.getToken(),
                it
            )
        }
    }
    // endregion Commissions

    // region setupRecyclerView
    private fun initRecyclerView() {
        // region Commission
        commissionInformationAdapter = CommissionInformationAdapter(
            CommissionInformationAdapter.OnClickListener { click ->

            },
            requireContext(), arrayListOf()
        )

        binding.rvCommissionInformation.apply {
            setHasFixedSize(true)
            adapter = commissionInformationAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        // endregion Commission
    }
    // endregion setupRecyclerView

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region Commission
        viewModel.commissionResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    if (response.body.isNullOrEmpty()) {
                        println("Here1")
                        binding.rvCommissionInformation.visibility = View.GONE
                        binding.tvHeader.visibility = View.GONE
                    } else {
                        println("Here2")
                        if (!response.body!![0]?.commissions.isNullOrEmpty()) {
                            println("Here3")
                            binding.rvCommissionInformation.visibility = View.VISIBLE
                            binding.tvHeader.visibility = View.VISIBLE
                            response.body!![0]?.commissions?.let { it1 ->
                                commissionInformationAdapter.updateCommissionModels(
                                    it1
                                )
                            }
                        } else {
                            println("Here4")
                            binding.rvCommissionInformation.visibility = View.GONE
                            binding.tvHeader.visibility = View.GONE
                        }
                    }
                } else {
                    binding.rvCommissionInformation.visibility = View.GONE
                    binding.tvHeader.visibility = View.GONE
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
        // endregion Commission

    }

    private fun observeNetworkErrors() {
        viewModel.networkError.observe(viewLifecycleOwner) { error ->
            error?.let {
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