package com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.intranet.paywallpanel.R

class PaymentGatewayDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway_detail)
        changeStatusBarColor()
    }

    private fun changeStatusBarColor() {
        val window: Window = window

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryDarkColor)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.nothing, R.anim.bottom_down)
    }
}