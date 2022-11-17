package com.intranet.paywallpanel.ui.main.drawer.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elekse.esnekpos.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentProfileBinding
import com.intranet.paywallpanel.network.user.model.AttemptsModel
import com.intranet.paywallpanel.network.user.model.ChangePasswordRequestModel
import com.intranet.paywallpanel.network.user.model.LastAttemptsBodyModel
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardLastTransactionsAdapter
import com.intranet.paywallpanel.util.*

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private lateinit var lastAttemptsAdapter: LastAttemptsAdapter
    private var lastAttemptsList = ArrayList<AttemptsStaticModel>()
    private var dialog: BottomSheetDialog? = null
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

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
        dialog = BottomSheetDialog(requireContext())
        apiCalls()
    }

    private fun handleClick() {
        binding.btnChangePassword.setOnClickListener {
            openChangePasswordBottomSheet()
        }
    }

    // region apiCalls
    private fun apiCalls() {
        //profile
        viewModel.profile(preferenceHelper.getToken())
        //lastAttempts
        viewModel.lastAttempts(preferenceHelper.getToken())
    }
    // endregion apiCalls

    // region setupRecyclerView
    private fun initRecyclerView() {
        // region LastAttempts
        lastAttemptsAdapter = LastAttemptsAdapter(
            LastAttemptsAdapter.OnClickListener { click ->
                //Click
            },
            requireContext(), arrayListOf()
        )

        binding.rvLastAttempts.apply {
            setHasFixedSize(true)
            adapter = lastAttemptsAdapter
            layoutManager =
                LinearLayoutManager(requireContext())
        }
        // endregion LastAttempts
    }
    // endregion setupRecyclerView

    // region Change Password Bottom Sheet
    private fun openChangePasswordBottomSheet() {

        val view = layoutInflater.inflate(R.layout.bottomsheet_change_password, null)

        val back = view.findViewById<RelativeLayout>(R.id.rl_back)
        val etOldPassword = view.findViewById<EditText>(R.id.et_old_password)
        val etNewPassword = view.findViewById<EditText>(R.id.et_new_password)
        val etRenewPassword = view.findViewById<EditText>(R.id.et_renew_password)
        val btnChangePassword = view.findViewById<Button>(R.id.btn_change_password_bs)

        back.setOnClickListener {
            dialog?.dismiss()
        }

        btnChangePassword.setOnClickListener {
            if (etOldPassword.text.toString().isNotEmpty() && etNewPassword.text.toString()
                    .isNotEmpty() && etRenewPassword.text.toString().isNotEmpty()
            ) {
                val changePasswordRequestModel = ChangePasswordRequestModel(
                    etOldPassword.text.toString().trim(),
                    etNewPassword.text.toString().trim(),
                    etRenewPassword.text.toString().trim()
                )
                viewModel.changePassword(preferenceHelper.getToken(), changePasswordRequestModel)
            }
        }

        dialog?.setCancelable(true)

        dialog?.setContentView(view)

        dialog?.show()
    }
    // endregion Change Password Bottom Sheet

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region Profile
        viewModel.profileResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.tvFullName.text =
                        (response.body?.name ?: "") + " " + (response.body?.lastname ?: "")
                    binding.tvUsername.text = response.body?.username ?: ""
                    binding.tvMail.text = response.body?.email ?: ""
                    binding.tvPhone.text = response.body?.phone ?: ""
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
        // endregion Profile

        // region LastAttempts
        viewModel.lastAttemptsResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    lastAttemptsList.clear()
                    if (response.body != null) {
                        if (response.body?.success != null) {
                            for (item in response.body!!.success!!) {
                                val attemptModel = AttemptsStaticModel(1, item)
                                lastAttemptsList.add(attemptModel)
                            }
                        }
                        if (response.body?.unsuccess != null) {
                            for (item in response.body!!.unsuccess!!) {
                                val attemptModel = AttemptsStaticModel(2, item)
                                lastAttemptsList.add(attemptModel)
                            }
                        }
                    }
                    lastAttemptsAdapter.updateAttemptModels(lastAttemptsList)
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
        // endregion LastAttempts

        // region ChangePassword
        viewModel.changePasswordResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    dialog?.dismiss()
                    activity?.displayAlert(
                        resources.getString(R.string.change_password_success),
                        StatusType.SUCCESS,
                        false,
                        object : AlertCallback {
                            override fun proceed() {

                            }

                            override fun cancel() {

                            }

                        })
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
        // endregion ChangePassword
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