package com.intranet.paywallpanel.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intranet.paywallpanel.ui.TestViewModel
import com.intranet.paywallpanel.ui.landing.changepasswordforget.ChangePasswordForgetViewModel
import com.intranet.paywallpanel.ui.landing.forgotpassword.ForgotPasswordViewModel
import com.intranet.paywallpanel.ui.landing.login.LoginViewModel
import com.intranet.paywallpanel.ui.landing.otp.OtpViewModel
import com.intranet.paywallpanel.ui.landing.splash.SplashViewModel
import com.intranet.paywallpanel.ui.main.MainViewModel
import com.intranet.paywallpanel.ui.main.drawer.dashboard.DashboardViewModel
import com.intranet.paywallpanel.ui.main.drawer.paymentgateways.PaymentGatewaysViewModel
import com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail.PaymentGatewayDetailViewModel
import com.intranet.paywallpanel.ui.main.drawer.profile.ProfileViewModel
import com.intranet.paywallpanel.ui.main.drawer.settings.SettingsViewModel
import com.intranet.paywallpanel.ui.main.drawer.testtool.TestToolViewModel
import com.intranet.paywallpanel.ui.main.drawer.transfers.TransfersViewModel
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.TransferDetailViewModel

import java.lang.IllegalArgumentException

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(application = Application()) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(application = Application()) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application = Application()) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(application = Application()) as T
            modelClass.isAssignableFrom(PaymentGatewaysViewModel::class.java) -> PaymentGatewaysViewModel(application = Application()) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(application = Application()) as T
            modelClass.isAssignableFrom(TestToolViewModel::class.java) -> TestToolViewModel(application = Application()) as T
            modelClass.isAssignableFrom(TransfersViewModel::class.java) -> TransfersViewModel(application = Application()) as T
            modelClass.isAssignableFrom(TestViewModel::class.java) -> TestViewModel(application = Application()) as T
            modelClass.isAssignableFrom(OtpViewModel::class.java) -> OtpViewModel(application = Application()) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(application = Application()) as T
            modelClass.isAssignableFrom(ChangePasswordForgetViewModel::class.java) -> ChangePasswordForgetViewModel(application = Application()) as T
            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel(application = Application()) as T
            modelClass.isAssignableFrom(TransferDetailViewModel::class.java) -> TransferDetailViewModel(application = Application()) as T
            modelClass.isAssignableFrom(PaymentGatewayDetailViewModel::class.java) -> PaymentGatewayDetailViewModel(application = Application()) as T

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}