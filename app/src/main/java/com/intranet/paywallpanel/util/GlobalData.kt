package com.intranet.paywallpanel.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.network.merchant.model.PaymentGatewaysBodyModel
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.PaymentGatewayProviderBodyModel
import com.intranet.paywallpanel.ui.main.drawer.paymentgateways.PaymentGatewaysFragment
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.util.*
import kotlin.system.exitProcess


class GlobalData {

    companion object{

        var toProfile: Boolean = false
        var expireDate: String = ""
        var cardNumber: String = ""
        var amount: String = ""
        var url: String = ""
        //For PaymentDetail
        var paymentId: Int = 0
        var transferModel: TransferModel? = null
        //For PaymentGatewayDetail
        var paymentGatewayProviderBodyModel: PaymentGatewayProviderBodyModel? = null

        public fun dateFormat(date: String): String {
            return try {
                var newDateWithoutTime = date.split("T")[0]
                newDateWithoutTime.split("-")[2] + "/" + newDateWithoutTime.split("-")[1] + "/" + newDateWithoutTime.split("-")[0]
            } catch (e: Exception){
                //Firebase.crashlytics.recordException(e)
                ""
            }
        }

        public fun dateFormatWithoutTime(date: String): String {
            return try {
                date.split("-")[2] + "/" + date.split("-")[1] + "/" + date.split("-")[0]
            } catch (e: Exception){
               // Firebase.crashlytics.recordException(e)
                ""
            }
        }

        public fun dateFormatWithTime(date: String): String {
            return try {
                var newDateWithoutTime = date.split("T")[0]
                var newDateWithTime = date.split("T")[1]
                val time = newDateWithTime.split(".")[0]
                newDateWithoutTime.split("-")[2] + "/" + newDateWithoutTime.split("-")[1] + "/" + newDateWithoutTime.split("-")[0] + " " + time
            } catch (e: Exception){
                //Firebase.crashlytics.recordException(e)
                ""
            }
        }

        fun dateFormatToSend(date: String): String {
            return try {
                date.split("/")[2] + "-" + date.split("/")[1] + "-" + date.split("/")[0]
            } catch (e: Exception){
//                Firebase.crashlytics.recordException(e)
                ""
            }
        }

        fun moneyFormatToShow(money: String, currencyId: Int): String {
            try {
                var replacedStrMoney = ""
                var leftDot = money.split(".")[0]
                var rightDot = money.split(".")[1]

                if (leftDot.contains("-")) {
                    leftDot = leftDot.replace("-","")
                    if (leftDot.length == 1 || leftDot.length == 2 || leftDot.length == 3) {
                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + leftDot + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + leftDot + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 4){

                        val firstPart = leftDot.substring(0..0)
                        val secondPart = leftDot.substring(1..3)

                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 5){
                        val firstPart = leftDot.substring(0..1)
                        val secondPart = leftDot.substring(2..4)

                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 6){
                        val firstPart = leftDot.substring(0..2)
                        val secondPart = leftDot.substring(3..5)
                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 7){

                        val firstPart = leftDot.substring(0..0)
                        val secondPart = leftDot.substring(1..3)
                        val thirdPart = leftDot.substring(4..6)

                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 8){

                        val firstPart = leftDot.substring(0..1)
                        val secondPart = leftDot.substring(2..4)
                        val thirdPart = leftDot.substring(5..7)

                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 9){

                        val firstPart = leftDot.substring(0..2)
                        val secondPart = leftDot.substring(3..5)
                        val thirdPart = leftDot.substring(6..8)

                        if (rightDot.length == 1) {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = "-" + firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    }
                } else {
                    if (leftDot.length == 1 || leftDot.length == 2 || leftDot.length == 3) {
                        if (rightDot.length == 1) {
                            replacedStrMoney = leftDot + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = leftDot + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 4){

                        val firstPart = leftDot.substring(0..0)
                        val secondPart = leftDot.substring(1..3)

                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 5){
                        val firstPart = leftDot.substring(0..1)
                        val secondPart = leftDot.substring(2..4)

                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 6){
                        val firstPart = leftDot.substring(0..2)
                        val secondPart = leftDot.substring(3..5)
                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 7){

                        val firstPart = leftDot.substring(0..0)
                        val secondPart = leftDot.substring(1..3)
                        val thirdPart = leftDot.substring(4..6)

                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 8){

                        val firstPart = leftDot.substring(0..1)
                        val secondPart = leftDot.substring(2..4)
                        val thirdPart = leftDot.substring(5..7)

                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    } else if (leftDot.length == 9){

                        val firstPart = leftDot.substring(0..2)
                        val secondPart = leftDot.substring(3..5)
                        val thirdPart = leftDot.substring(6..8)

                        if (rightDot.length == 1) {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "0₺"
                        } else {
                            replacedStrMoney = firstPart + "." + secondPart + "." + thirdPart + "," + rightDot + "₺"
                        }
                    }
                }

                return replacedStrMoney
                /* var main = money.split(".")[0]
                 var afterDot = money.split(".")[1]

                 val numberFormat = NumberFormat.getCurrencyInstance()
                 numberFormat.maximumFractionDigits = 0;
                 //val convert = numberFormat.format(main.toInt())
                 val convert = numberFormat.format(main.toInt())

                 if (currencyId == 1){
                     var result = convert.replace(",", ".").replace("$","").replace("₺","") + "," + afterDot
                     result += "₺"
                     return result
                 } else if (currencyId == 2) {
                     var result = convert.replace(",", ".").replace("$","").replace("₺","") + "," + afterDot
                     result += "$"
                     return result
                 } else {
                     var result = convert.replace(",", ".").replace("$","").replace("₺","") + "," + afterDot
                     result += "₺"
                     return result
                 }*/
            } catch (e: Exception){
                //Firebase.crashlytics.recordException(e)
                return ""
            }
        }

        public fun moneyFormatToSend(money: String): BigDecimal {
            return try {
                money.replace(".", "").replace(",", ".").replace("₺", "").replace("$", "")
                    .toBigDecimal()
            } catch (e: Exception) {
                //Firebase.crashlytics.recordException(e)
                BigDecimal("0.0")
            }
        }

        fun showLoadingDialog(context: Context): Dialog {
            try {
                val progressDialog = Dialog(context)

                progressDialog.let {
                    it.show()
                    it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                    it.setContentView(R.layout.custom_loading)
                    it.setCancelable(false)
                    it.setCanceledOnTouchOutside(true)

                    return it
                }
            } catch (e: Exception){
                //Firebase.crashlytics.recordException(e)

                return Dialog(context)
            }
        }

        fun changeStatusBarColor(context: Context, activity: Activity){
            try {
                val window: Window = activity.window

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

                window.statusBarColor = ContextCompat.getColor(context, R.color.primaryDarkColor)

            } catch (e: Exception) {
               // Firebase.crashlytics.recordException(e)
            }
        }

        fun writePDF(filename: String, base64: String): File? {
            val file:File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "${filename}.pdf")

            try {
                FileOutputStream(file).use { fos ->
                    val decoder: ByteArray = Base64.decode(base64, Base64.DEFAULT)
                    fos.write(decoder)
                }

                return file;
            } catch (e: Exception){
                //Firebase.crashlytics.recordException(e)
            }

            return null;
        }

        fun deletePDF(path: String): Boolean {
            try {
                val file:File = File(path)

                try {
                    if (file.exists()) {
                        return file.delete();
                    }
                    else {
                        return false;
                    }
                }  catch (e: Exception) {}

                return false;
            } catch (e: Exception) {
                //Firebase.crashlytics.recordException(e)
                return false
            }
        }

        fun generateTaskId(): String {
            try {
                val uuid = UUID.randomUUID()
                return uuid.toString()
            }
            catch (e: Exception)
            {
                //Firebase.crashlytics.recordException(e)
            }

            return "";
        }

        fun checkForInternet(context: Context): Boolean {
            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    val network = connectivityManager.activeNetwork ?: return false

                    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                    return when {

                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                        else -> false
                    }
                } else {
                    // if the android version is below M
                    @Suppress("DEPRECATION") val networkInfo =
                        connectivityManager.activeNetworkInfo ?: return false
                    @Suppress("DEPRECATION")
                    return networkInfo.isConnected
                }
            } catch (e: Exception) {
                //Firebase.crashlytics.recordException(e)
                return false
            }
        }

        fun errorHandler(activity: Activity) {
            try {
                Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
                    object : Thread() {
                        override fun run() {
                            Looper.prepare()
                           // Firebase.crashlytics.log(paramThrowable.message.toString())
                            val ex: Exception = Exception(paramThrowable)
                            //Firebase.crashlytics.recordException(ex)
                            //Firebase.crashlytics.recordException(paramThrowable)
                            Looper.loop()
                        }
                    }.start()
                    try {
                        Thread.sleep(2000) // Let the Toast display before app will get shutdown
                    } catch (e: InterruptedException) {
                    }

                    activity.finish()
                    exitProcess(0)
                }
            } catch (e: Exception){
               // Firebase.crashlytics.recordException(e)
            }
        }

        fun expand(v: View) {
            val matchParentMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(
                (v.parent as View).width,
                View.MeasureSpec.EXACTLY
            )
            val wrapContentMeasureSpec: Int =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
            val targetHeight: Int = v.measuredHeight

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    v.layoutParams.height =
                        if (interpolatedTime == 1f) ConstraintLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Expansion speed of 1dp/ms
            a.duration = ((targetHeight / v.context.resources
                .displayMetrics.density).toInt()).toLong()
            v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight: Int = v.measuredHeight
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Collapse speed of 1dp/ms
            a.duration = ((initialHeight / v.context.resources
                .displayMetrics.density).toInt()).toLong()
            v.startAnimation(a)
        }

    }
}