package com.intranet.paywallpanel.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.intranet.paywallpanel.R

fun Activity.displayDialogWithInteraction(
    message: String?,
    statusType: StatusType,
    alertCallback: AlertCallback
) {
    /*try {
        val dialog = Dialog(this, R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.item_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.tv_message.text = message
        val animation = dialog.lottie
        animation.playAnimation()
        when(statusType){
            StatusType.SUCCESS -> animation.setAnimation(getString(R.string.alert_type_done_icon))
            StatusType.ERROR -> animation.setAnimation(getString(R.string.alert_type_error_icon))
            StatusType.INFO -> animation.setAnimation(getString(R.string.alert_type_info_icon))
            StatusType.WARNING -> animation.setAnimation(getString(R.string.alert_type_warning_icon))
        }
        val positiveButton = dialog.findViewById<TextView>(R.id.button_confirm)
        val negativeButton = dialog.findViewById<TextView>(R.id.button_cancel)

        positiveButton.setOnClickListener {
            alertCallback.proceed()
            animation.cancelAnimation()
            dialog.dismiss()
        }
        negativeButton.setOnClickListener {
            alertCallback.cancel()
            dialog.dismiss()
        }
        dialog.show()
    } catch (e: Exception){

    }*/
}

fun Activity.displayAlert(
    message: String?,
    statusType: StatusType,
    isCancelButtonVisible: Boolean,
    alertCallback: AlertCallback
) {
    try {
        val dialog = Dialog(this, R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)

        val tvMessage = view.findViewById<TextView>(R.id.tv_message)
        val tvHeader = view.findViewById<TextView>(R.id.tv_header_alert)
        val ivStatus = view.findViewById<ImageView>(R.id.iv_status)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnConfirm = view.findViewById<Button>(R.id.btn_ok)

        if (isCancelButtonVisible) {
            btnCancel.visibility = View.VISIBLE
        } else {
            btnCancel.visibility = View.GONE
        }

        if (statusType == StatusType.SUCCESS) {
            tvHeader.text = resources.getString(R.string.success)
            ivStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.status_success))
        } else if (statusType == StatusType.ERROR) {
            tvHeader.text = resources.getString(R.string.unsuccess)
            ivStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.status_error))
        } else if (statusType == StatusType.INFO) {
            tvHeader.text = resources.getString(R.string.info)
            ivStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.status_info))
        } else if (statusType == StatusType.QUESTION) {
            tvHeader.text = resources.getString(R.string.question)
            ivStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.status_question))
        } else {
            tvHeader.text = resources.getString(R.string.warning)
            ivStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.status_warning))
        }

        btnCancel.setOnClickListener {
            alertCallback.cancel()
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            alertCallback.proceed()
            dialog.dismiss()
        }

        tvMessage.text = message

        dialog.setCancelable(true)

        dialog.setContentView(view)

        dialog.show()
    } catch (e: Exception) {
        Log.e("Dialog Error", e.message.toString())
    }
}

interface AlertCallback {
    fun proceed()
    fun cancel()
}

interface AlertCallbackEditText {
    fun proceed(text: String): String {
        return text
    }

    fun cancel()
}

enum class StatusType(val code: Int) {
    SUCCESS(0),
    ERROR(1),
    INFO(2),
    QUESTION(3),
    WARNING(4)
}