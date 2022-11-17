package com.intranet.paywallpanel.ui.main.drawer.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.databinding.FragmentDashboardBinding
import com.intranet.paywallpanel.util.*


class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var dashboardLastTransactionsAdapter: DashboardLastTransactionsAdapter
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<DashboardViewModel> = DashboardViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

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
        getDashboard()
        getDashboardLastTransactions()
        getMerchantProfile()

        binding.swipeRefresh.setOnRefreshListener {
            getDashboard()
            getDashboardLastTransactions()
            getMerchantProfile()
        }
    }

    private fun handleClick() {

    }

    // region Chart
    /*private fun createChart() {
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .title("title")
            .subtitle("subtitle")
            .backgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#005c82"))
            .legendEnabled(false)
            .series(
                arrayOf(
                    AASeriesElement()
                        .data(
                            arrayOf(
                                3.9,
                                4.2,
                                5.7,
                                8.5,
                                11.9,
                                15.2,
                                17.0,
                                16.6,
                                14.2,
                                10.3,
                                6.6,
                                4.8
                            )
                        )
                )
            )

        binding.aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }*/
    // endregion Chart

    // region Dashboard
    private fun getDashboard() {
        viewModel.dashboard(preferenceHelper.getToken())
    }
    // endregion Dashboard

    // region DashboardLastTransactions
    private fun getDashboardLastTransactions() {
        viewModel.transfers(preferenceHelper.getToken())
    }
    // endregion DashboardLastTransactions

    // region getMerchantProfile
    private fun getMerchantProfile() {
        viewModel.profile(preferenceHelper.getToken())
    }
    // endregion getMerchantProfile

    // region setupRecyclerView
    private fun initRecyclerView() {
        // region Dashboard
        dashboardAdapter = DashboardAdapter(
            DashboardAdapter.OnClickListener { click ->
                //Click
            },
            requireContext(), arrayListOf()
        )

        binding.rvDashboard.apply {
            setHasFixedSize(true)
            adapter = dashboardAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        // endregion Dashboard

        // region DashboardLastTransactions
        dashboardLastTransactionsAdapter = DashboardLastTransactionsAdapter(
            DashboardLastTransactionsAdapter.OnClickListener { click ->
                GlobalData.paymentId = click?.id ?: 0
                GlobalData.transferModel = click
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
        // endregion DashboardLastTransactions
    }
    // endregion setupRecyclerView

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region Dashboard
        viewModel.dashboardResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvDashboard.visibility = View.VISIBLE
                    var dashboardList: ArrayList<DashboardStaticModel>? = null

                    dashboardList = ArrayList<DashboardStaticModel>()

                    val dashboardModel1 = DashboardStaticModel(
                        "Transfer Tutarı",
                        "#90BE6D",
                        GlobalData.moneyFormatToShow(
                            (response.body?.v1?.dailyTransferAmount ?: "0.0").toString(), 1
                        )
                    )
                    val dashboardModel2 = DashboardStaticModel(
                        "Transfer Adedi",
                        "#43AA8B",
                        (response.body?.v1?.dailyTransferCount ?: 0).toString()
                    )
                    val dashboardModel3 = DashboardStaticModel(
                        "Başarılı Transfer Tutarı",
                        "#4D908E",
                        GlobalData.moneyFormatToShow(
                            (response.body?.v1?.dailyTransferAmount ?: "0.0").toString(), 1
                        )
                    )
                    val dashboardModel4 = DashboardStaticModel(
                        "Başarılı Transfer Adedi",
                        "#4D6490",
                        (response.body?.v1?.dailySuccessfulTransferCount ?: 0).toString()
                    )
                    val dashboardModel5 = DashboardStaticModel(
                        "Başarısız Transfer Tutarı",
                        "#F8961E",
                        GlobalData.moneyFormatToShow(
                            (response.body?.v1?.dailyUnsuccessfulTransferAmount
                                ?: "0.0").toString(), 1
                        )
                    )
                    val dashboardModel6 = DashboardStaticModel(
                        "Başarısız Transfer Adedi",
                        "#F9844A",
                        (response.body?.v1?.dailyUnsuccessfulTransferCount ?: 0).toString()
                    )
                    val dashboardModel7 = DashboardStaticModel(
                        "İptal Adedi",
                        "#F03A33",
                        (response.body?.v1?.dailyCancelCount ?: 0).toString()
                    )
                    val dashboardModel8 = DashboardStaticModel(
                        "İade Adedi",
                        "#F9724A",
                        (response.body?.v1?.dailyRefundCount ?: 0).toString()
                    )

                    dashboardList.add(dashboardModel1)
                    dashboardList.add(dashboardModel2)
                    dashboardList.add(dashboardModel3)
                    dashboardList.add(dashboardModel4)
                    dashboardList.add(dashboardModel5)
                    dashboardList.add(dashboardModel6)
                    dashboardList.add(dashboardModel8)
                    dashboardList.add(dashboardModel7)
                    dashboardAdapter.updateDashboardModels(dashboardList)
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
        // endregion Dashboard

        // region DashboardLastTransactions
        viewModel.transferResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvLastTransactions.visibility = View.VISIBLE
                    response.body?.let { it1 ->
                        it1.data?.let { it2 ->
                            dashboardLastTransactionsAdapter.updateDashboardLastTransactionModels(
                                it2, true
                            )
                        }
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

        // region merchantProfile
        viewModel.profileResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.swipeRefresh.isRefreshing = false
                    binding.tvHeader.text = response.body?.name ?: ""
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
        // endregion merchantProfile
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