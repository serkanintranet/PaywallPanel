package com.intranet.paywallpanel.ui.landing.splash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elekse.esnekpos.base.BaseFragment
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.FragmentSplashBinding
import com.intranet.paywallpanel.ui.landing.login.LoginFragment
import com.intranet.paywallpanel.util.IPreferenceHelper
import com.intranet.paywallpanel.util.PreferenceManager
import com.intranet.paywallpanel.util.navigateToLanding
import com.intranet.paywallpanel.util.navigateToMain

class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {
    // region global initial
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(requireContext()) }
    private val TAG: String = SplashFragment::class.java.simpleName
    // endregion global initial

    // region android lifecycle
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            checkToken()
        } catch (ex: Exception) {
            Log.e(TAG, ex.message.toString())
        }
    }
    // endregion android lifecycle

    // region UI Process
    private fun checkToken() {
        println("Token - " + preferenceHelper.getToken())
        if (preferenceHelper.getToken().isNotEmpty()) {
            activity?.navigateToMain()
        } else {
            activity?.navigateToLanding()
        }
    }
    // endregion UI Process

}