package com.intranet.paywallpanel.ui.main.drawer.transfers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentTransfersBinding
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardAdapter
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardLastTransactionsAdapter
import com.intranet.paywallpanel.util.*

class TransfersFragment : BaseFragment<TransfersViewModel, FragmentTransfersBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private lateinit var dashboardLastTransactionsAdapter: DashboardLastTransactionsAdapter
    private var callApi = true
    private var startCall = 0
    private var clear = false
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<TransfersViewModel> = TransfersViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransfersBinding = FragmentTransfersBinding.inflate(inflater, container, false)

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
        getLastTransactions(start = startCall, length = 20)
        binding.swipeRefresh.setOnRefreshListener {
            callApi = true
            startCall = 0
            clear = true
            getLastTransactions(start = startCall, length = 20)
            scrollRecyclerAndGetItems()
        }
        scrollRecyclerAndGetItems()
    }

    private fun handleClick() {

    }

    // region DashboardLastTransactions
    private fun getLastTransactions(start: Int, length: Int) {
        viewModel.transfers(preferenceHelper.getToken(), start, length)
    }

    private fun scrollRecyclerAndGetItems() {
        binding.rvLastTransactions.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (callApi) {
                        startCall += 20
                        getLastTransactions(start = startCall, 20)
                    }
                }
            }
        })
    }
    // endregion DashboardLastTransactions

    // region setupRecyclerView
    private fun initRecyclerView() {

        // region LastTransactions
        dashboardLastTransactionsAdapter = DashboardLastTransactionsAdapter(
            DashboardLastTransactionsAdapter.OnClickListener { click ->
                GlobalData.paymentId = click?.id ?: 0
                activity?.navigateToTransferDetail()
            },
            requireContext(), arrayListOf()
        )

        binding.rvLastTransactions.apply {
            setHasFixedSize(true)
            adapter = dashboardLastTransactionsAdapter
            layoutManager =
                LinearLayoutManager(requireContext())
        }
        // endregion LastTransactions
    }
    // endregion setupRecyclerView

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region LastTransactions
        viewModel.transferResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.rvLastTransactions.visibility = View.VISIBLE
                    binding.swipeRefresh.isRefreshing = false
                    response.body?.let { it1 ->
                        if ((it1.data?.count()?.rem(20) ?: 0) != 0) {
                            callApi = false
                        }
                        it1.data?.let { it2 ->
                            dashboardLastTransactionsAdapter.updateDashboardLastTransactionModels(
                                it2, clear
                            )
                        }
                        clear = false
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
        // endregion DashboardLastTransactions
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