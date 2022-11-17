package com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elekse.esnekpos.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentTransferDetailBinding
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import com.intranet.paywallpanel.network.user.model.ChangePasswordRequestModel
import com.intranet.paywallpanel.ui.landing.login.LoginFragment
import com.intranet.paywallpanel.ui.landing.login.LoginFragmentDirections
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardAdapter
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardLastTransactionsAdapter
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.adapters.PaymentProviderDetectedAdapter
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.adapters.ProcessStepsAdapter
import com.intranet.paywallpanel.util.*

class TransferDetailFragment :
    BaseFragment<TransferDetailViewModel, FragmentTransferDetailBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private lateinit var processStepsAdapter: ProcessStepsAdapter
    private lateinit var paymentProviderDetectedAdapter: PaymentProviderDetectedAdapter
    private val TAG: String = TransferDetailFragment::class.java.simpleName
    private var dialog: BottomSheetDialog? = null
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<TransferDetailViewModel> =
        TransferDetailViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransferDetailBinding =
        FragmentTransferDetailBinding.inflate(inflater, container, false)

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
        dialog = BottomSheetDialog(requireContext())
        binding.include.tvHeader.text = resources.getString(R.string.transfer_detail)
        getPaymentActivities()
        getPaymentProviderDetected()

        binding.tvCardOwner.text = GlobalData.transferModel?.cardOwnerName
        binding.tvCardNumber.text = GlobalData.transferModel?.cardNumber
        binding.tvInstallmentCount.text = GlobalData.transferModel?.installement
        binding.tvPaymentMethod.text = GlobalData.transferModel?.paymentMethod
        binding.tvPaymentDate.text =
            GlobalData.dateFormatWithTime(GlobalData.transferModel?.dateTime.toString())
        binding.tvPaymentAmount.text =
            GlobalData.moneyFormatToShow(GlobalData.transferModel?.amount.toString(), 1)
        binding.tvPaymentStatus.text = GlobalData.transferModel?.status
    }

    private fun handleClick() {
        binding.include.rlBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    // region PaymentActivities
    private fun getPaymentActivities() {
        viewModel.getPaymentActivities(preferenceHelper.getToken(), GlobalData.paymentId)
    }
    // endregion PaymentActivities

    // region PaymentCommunication
    private fun getPaymentCommunication(paymentActivityId: Int) {
        viewModel.getPaymentCommunication(preferenceHelper.getToken(), paymentActivityId)
    }
    // endregion PaymentCommunication

    // region PaymentProviderDetected
    private fun getPaymentProviderDetected() {
        viewModel.getPaymentProviderDetected(preferenceHelper.getToken(), GlobalData.paymentId)
    }
    // endregion PaymentProviderDetected

    // region setupRecyclerView
    private fun initRecyclerView() {
        // region PaymentActivities
        processStepsAdapter = ProcessStepsAdapter(
            ProcessStepsAdapter.OnClickListener { click ->
                click.paymentStatusId?.let { openProviderCommunicationBottomSheet(it) }
            },
            requireContext(), arrayListOf()
        )

        binding.rvProcessSteps.apply {
            setHasFixedSize(true)
            adapter = processStepsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        // endregion PaymentActivities

        // region PaymentProviderDetected
        paymentProviderDetectedAdapter = PaymentProviderDetectedAdapter(
            PaymentProviderDetectedAdapter.OnClickListener { click ->

            },
            requireContext(), arrayListOf()
        )

        binding.rvCommissionDetected.apply {
            setHasFixedSize(true)
            adapter = paymentProviderDetectedAdapter
            layoutManager =
                LinearLayoutManager(requireContext())
        }
        // endregion PaymentProviderDetected
    }
    // endregion setupRecyclerView

    // region Provider Communication Bottom Sheet
    private fun openProviderCommunicationBottomSheet(paymentStatusId: Int) {

        viewModel.getPaymentCommunication(preferenceHelper.getToken(), paymentStatusId)

        val view = layoutInflater.inflate(R.layout.bottomsheet_comminication, null)

        val back = view?.findViewById<RelativeLayout>(R.id.rl_back)
        val tvRequestDate = view?.findViewById<TextView>(R.id.tv_request_date)
        val tvRequestContent = view?.findViewById<TextView>(R.id.tv_request_content)
        val tvResponseDate = view?.findViewById<TextView>(R.id.tv_response_date)
        val tvResponseContent = view?.findViewById<TextView>(R.id.tv_response_content)
        val tvExecuteTime = view?.findViewById<TextView>(R.id.tv_execute_time)

        viewModel.paymentCommunicationResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    tvRequestDate?.text = response.body?.requestTime?.let { it1 ->
                        GlobalData.dateFormatWithTime(
                            it1
                        )
                    }
                    tvRequestContent?.text = response.body?.request ?: ""
                    tvResponseDate?.text = response.body?.responseTime?.let { it1 ->
                        GlobalData.dateFormatWithTime(
                            it1
                        )
                    }
                    tvResponseContent?.text = response.body?.response ?: ""
                    tvExecuteTime?.text = response.body?.executiveSecond.toString()
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

        back?.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setCancelable(true)

        view?.let { dialog?.setContentView(it) }

        dialog?.show()
    }
    // endregion Provider Communication Bottom Sheet

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region PaymentActivities
        viewModel.paymentActivitiesResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    if (response.body.isNullOrEmpty()) {
                        binding.rvProcessSteps.visibility = View.GONE
                        binding.tvHeader.visibility = View.GONE
                    } else {
                        binding.rvProcessSteps.visibility = View.VISIBLE
                        binding.tvHeader.visibility = View.VISIBLE
                        processStepsAdapter.updatePaymentActivityModels(response.body!!)
                    }
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
        // endregion PaymentActivities

        // region PaymentProviderDetected
        viewModel.paymentProviderDetectedResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    if (response.body.isNullOrEmpty()) {
                        binding.rvCommissionDetected.visibility = View.GONE
                        binding.tvHeader2.visibility = View.GONE
                    } else {
                        binding.rvCommissionDetected.visibility = View.VISIBLE
                        binding.tvHeader2.visibility = View.VISIBLE
                        paymentProviderDetectedAdapter.updatePaymentProviderDetectedModels(response.body!!)
                    }
                } else {
                    binding.rvCommissionDetected.visibility = View.GONE
                    binding.tvHeader2.visibility = View.GONE
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
        // endregion PaymentProviderDetected
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