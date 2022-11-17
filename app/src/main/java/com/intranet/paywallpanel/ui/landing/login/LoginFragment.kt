package com.intranet.paywallpanel.ui.landing.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentLoginBinding
import com.intranet.paywallpanel.network.auth.model.LoginRequestModel
import com.intranet.paywallpanel.util.*
import kotlinx.coroutines.delay
import java.util.*

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private val TAG: String = LoginFragment::class.java.simpleName
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

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
    }

    private fun handleClick() {
        binding.btnLogin.setOnClickListener {
            if (!binding.etUsername.text.toString().isEmpty() && !binding.etPassword.text.toString()
                    .isEmpty()
            ) {
                val loginRequestModel = LoginRequestModel(
                    binding.etUsername.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )
                viewModel.login(loginRequestModel)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            outcome()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
            }, 500)
        }
    }

    //region Animations
    private fun income() {
        binding.rlIll.visibility = View.VISIBLE
        val animation: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        binding.rlIll.startAnimation(animation)

        binding.ivHeader.visibility = View.VISIBLE
        val animation2: Animation? =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_left)
        binding.ivHeader.startAnimation(animation2)

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
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                if (response.result == true) {
                    if (!response.body.isNullOrEmpty()) {
                        if (response.errorCode == 41) {
                            outcome()
                            navigate(
                                LoginFragmentDirections.actionLoginFragmentToOtpFragment(
                                    response.body ?: "",
                                    true
                                )
                            )
                        } else {
                            preferenceHelper.setToken(response.body!!)
                            activity?.navigateToMain()
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