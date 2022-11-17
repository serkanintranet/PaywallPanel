package com.intranet.paywallpanel.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.content.res.Resources
import android.graphics.*
import android.media.Image
import android.net.Uri
import android.provider.OpenableColumns
import android.text.InputFilter
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.ui.landing.LandingActivity
import com.intranet.paywallpanel.ui.landing.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt
import com.intranet.paywallpanel.ui.main.MainActivity
import com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail.PaymentGatewayDetailActivity
import com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.TransferDetailActivity
import java.io.File

/*fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
    try {
        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
    } catch (e: Exception){
        Firebase.crashlytics.recordException(e)
    }
}*/

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.navigate(direction: NavDirections) {
    try {
        findNavController().navigate(direction)
    } catch (e: Exception){
        //Firebase.crashlytics.recordException(e)
    }
}
fun Activity.navigateToSplash() {
    try {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        this.finish()
    } catch (e: Exception) {
        //Firebase.crashlytics.recordException(e)
    }
}

fun Activity.navigateToMain() {
    try {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        //this.finish()
    } catch (e: Exception){
        //Firebase.crashlytics.recordException(e)
    }
}

fun Activity.navigateToLanding() {
    try {
        val intent = Intent(this, LandingActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        //this.finish()
    } catch (e: Exception){
        //Firebase.crashlytics.recordException(e)
    }
}

fun Activity.navigateToTransferDetail() {
    try {
        val intent = Intent(this, TransferDetailActivity::class.java)
        startActivity(intent)
        this.overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
    } catch (e: Exception) {

    }
}

fun Activity.navigateToPaymentGatewayDetail() {
    try {
        val intent = Intent(this, PaymentGatewayDetailActivity::class.java)
        startActivity(intent)
        this.overridePendingTransition(R.anim.bottom_up, R.anim.nothing)
    } catch (e: Exception) {

    }
}

fun Float.toDp(): Float = this / Resources.getSystem().displayMetrics.density

fun Float.toPx(): Float = this * Resources.getSystem().displayMetrics.density

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun Activity.requestPermissionWithRationale(
    permission: String,
    requestCode: Int,
    rationaleStr: String
) {
    val provideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    if (provideRationale) {
        AlertDialog.Builder(this).apply {
            setTitle("Permission")
            setMessage(rationaleStr)
            setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(
                    this@requestPermissionWithRationale,
                    arrayOf(permission),
                    requestCode
                )
            }
            create()
            show()
        }
    } else {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
}


fun ComponentActivity.launchWhenResumed(block: suspend CoroutineScope.() -> Unit): Job =
    lifecycle.coroutineScope.launchWhenResumed(block)

fun Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun String.encodeBase64ToString(): String = String(this.toByteArray().encodeBase64())
fun String.encodeBase64ToByteArray(): ByteArray = this.toByteArray().encodeBase64()
fun ByteArray.encodeBase64ToString(): String = String(this.encodeBase64())

fun String.decodeBase64(): String = String(this.toByteArray().decodeBase64())
fun String.decodeBase64ToByteArray(): ByteArray = this.toByteArray().decodeBase64()
fun ByteArray.decodeBase64ToString(): String = String(this.decodeBase64())

fun ByteArray.encodeBase64(): ByteArray {
    val table =
        (CharRange('A', 'Z') + CharRange('a', 'z') + CharRange('0', '9') + '+' + '/').toCharArray()
    val output = ByteArrayOutputStream()
    var padding = 0
    var position = 0
    while (position < this.size) {
        var b = this[position].toInt() and 0xFF shl 16 and 0xFFFFFF
        if (position + 1 < this.size) b =
            b or (this[position + 1].toInt() and 0xFF shl 8) else padding++
        if (position + 2 < this.size) b = b or (this[position + 2].toInt() and 0xFF) else padding++
        for (i in 0 until 4 - padding) {
            val c = b and 0xFC0000 shr 18
            output.write(table[c].toInt())
            b = b shl 6
        }
        position += 3
    }
    for (i in 0 until padding) {
        output.write('='.toInt())
    }
    return output.toByteArray()
}

fun ByteArray.decodeBase64(): ByteArray {
    val table = intArrayOf(
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        62,
        -1,
        -1,
        -1,
        63,
        52,
        53,
        54,
        55,
        56,
        57,
        58,
        59,
        60,
        61,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34,
        35,
        36,
        37,
        38,
        39,
        40,
        41,
        42,
        43,
        44,
        45,
        46,
        47,
        48,
        49,
        50,
        51,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1
    )

    val output = ByteArrayOutputStream()
    var position = 0
    while (position < this.size) {
        var b: Int
        if (table[this[position].toInt()] != -1) {
            b = table[this[position].toInt()] and 0xFF shl 18
        } else {
            position++
            continue
        }
        var count = 0
        if (position + 1 < this.size && table[this[position + 1].toInt()] != -1) {
            b = b or (table[this[position + 1].toInt()] and 0xFF shl 12)
            count++
        }
        if (position + 2 < this.size && table[this[position + 2].toInt()] != -1) {
            b = b or (table[this[position + 2].toInt()] and 0xFF shl 6)
            count++
        }
        if (position + 3 < this.size && table[this[position + 3].toInt()] != -1) {
            b = b or (table[this[position + 3].toInt()] and 0xFF)
            count++
        }
        while (count > 0) {
            val c = b and 0xFF0000 shr 16
            output.write(c.toChar().toInt())
            b = b shl 8
            count--
        }
        position += 4
    }
    return output.toByteArray()
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Context.copyToClipboard(text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
}

// extension function to set edit text maximum length
fun EditText.setMaxLength(maxLength: Int){
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String{
    var  name = ""
    val cursor = query(uri,null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}

fun Fragment.convertToBase64(attachment: File): String {
    return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
}

