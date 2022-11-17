package com.intranet.paywallpanel.ui.main.drawer.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elekse.esnekpos.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentSettingsBinding
import com.intranet.paywallpanel.network.user.model.ChangePasswordRequestModel
import com.intranet.paywallpanel.ui.main.drawer.profile.AttemptsStaticModel
import com.intranet.paywallpanel.ui.main.drawer.profile.LastAttemptsAdapter
import com.intranet.paywallpanel.util.*


class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<SettingsViewModel> = SettingsViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUI()
            handleClick()
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
        apiCalls()
    }

    private fun handleClick() {

    }

    // region apiCalls
    private fun apiCalls() {
        //merchant
        viewModel.profile(preferenceHelper.getToken())
    }
    // endregion apiCalls

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        // region Merchant
        viewModel.profileResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    binding.tvFullName.text = response.body?.title ?: ""
                    binding.tvAddress.text = response.body?.address ?: ""
                    binding.tvMail.text = response.body?.email ?: ""
                    binding.tvPhone.text = response.body?.phone ?: ""
                    binding.tvManagerName.text =
                        (response.body?.managerName ?: "") + " " + (response.body?.managerLastname
                            ?: "")
                    binding.tvManagerMail.text = response.body?.managerEmail ?: ""
                    binding.tvManagerPhone.text = response.body?.managerPhone ?: ""
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
        // endregion Merchant
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