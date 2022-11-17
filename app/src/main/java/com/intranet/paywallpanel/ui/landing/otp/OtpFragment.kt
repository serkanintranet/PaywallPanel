package com.intranet.paywallpanel.ui.landing.otp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentOtpBinding
import com.intranet.paywallpanel.network.auth.model.OTPRequestModel
import com.intranet.paywallpanel.network.user.model.ForgotPasswordRequestModel
import com.intranet.paywallpanel.ui.landing.forgotpassword.ForgotPasswordFragmentDirections
import com.intranet.paywallpanel.ui.landing.login.LoginFragment
import com.intranet.paywallpanel.util.*

class OtpFragment : BaseFragment<OtpViewModel, FragmentOtpBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private val TAG: String = OtpFragment::class.java.simpleName
    private val args: OtpFragmentArgs by navArgs()
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<OtpViewModel> = OtpViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOtpBinding = FragmentOtpBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupUI()
            handleClick()
            observeLoading()
            observeResponses()
            observeNetworkErrors()
        } catch (ex: Exception) {
            Log.e(TAG, ex.message.toString())
        }
    }
// endregion android lifecycle

    // region UI Process
    private fun setupUI() {
        income()
        binding.firstPinView.addTextChangedListener(pinWatcher)
    }

    private fun handleClick() {

    }

    // region Pin Watch
    private var pinWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val pin = binding.firstPinView.text.toString().trim()
            val textLength = pin.length

            if (textLength == 6) {
                hideKeyboard()
                val otpConfirmRequestModel =
                    OTPRequestModel(args.captcha, pin)
                viewModel.otpConfirm(preferenceHelper.getToken(), otpConfirmRequestModel)
                binding.firstPinView.setText("")

            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }
    // endregion Pin Watch

    //region Animations
    private fun income() {
        binding.rlIll.visibility = View.VISIBLE
        val animation: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        binding.rlIll.startAnimation(animation)

        binding.tvHeaderLogin.visibility = View.VISIBLE
        val animation3: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
        binding.tvHeaderLogin.startAnimation(animation3)

        binding.llInputs.visibility = View.VISIBLE
        val animation4: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        binding.llInputs.startAnimation(animation4)
    }

    private fun outcome() {
        binding.rlIll.visibility = View.INVISIBLE
        val animation: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right)
        binding.rlIll.startAnimation(animation)

        binding.tvHeaderLogin.visibility = View.INVISIBLE
        val animation3: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left)
        binding.tvHeaderLogin.startAnimation(animation3)

        binding.llInputs.visibility = View.INVISIBLE
        val animation4: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_right)
        binding.llInputs.startAnimation(animation4)
    }
    //endregion Animations

    // endregion UI Process

    // region ObservableData
    private fun observeResponses() {
        viewModel.otpConfirmResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    if (args.isLogin) {
                        preferenceHelper.setToken(response.body!!)
                        activity?.navigateToMain()
                    } else {
                        outcome()
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({
                            navigate(
                                OtpFragmentDirections.actionOtpFragmentToChangePasswordForgetFragment(response.body ?: "")
                            )
                        }, 500)
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
    }

    private fun observeNetworkErrors() {
        viewModel.networkError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it.message.toString().contains("401")) {
                    activity?.displayAlert(
                        resources.getString(R.string.unauthorized),
                        StatusType.ERROR,
                        false,
                        object : AlertCallback {
                            override fun proceed() {

                            }

                            override fun cancel() {

                            }

                        })
                }
                //Log.e("userLoginError", error.message.toString())
                //ValidationHandler.handleApiErrors(error, requireActivity(), requireContext())
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