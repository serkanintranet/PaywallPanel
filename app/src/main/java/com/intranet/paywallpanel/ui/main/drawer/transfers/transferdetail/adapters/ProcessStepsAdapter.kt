package com.intranet.paywallpanel.ui.main.drawer.transfers.transferdetail.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.ItemDashboardBinding
import com.intranet.paywallpanel.databinding.ItemProcessStepBinding
import com.intranet.paywallpanel.network.payment.model.PaymentActivityModel
import com.intranet.paywallpanel.util.Enums
import com.intranet.paywallpanel.util.GlobalData

class ProcessStepsAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val paymentActivityModels: ArrayList<PaymentActivityModel>
) : RecyclerView.Adapter<ProcessStepsAdapter.ProcessStepsViewHolder>() {

    class ProcessStepsViewHolder(val binding: ItemProcessStepBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessStepsViewHolder {
        val binding = ItemProcessStepBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProcessStepsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProcessStepsViewHolder, position: Int) {

        holder.binding.tvHeader.text = paymentActivityModels[position].paymentStatusName ?: ""
        holder.binding.tvDate.text = GlobalData.dateFormatWithTime(paymentActivityModels[position].dateTime ?: "")

        Glide.with(context).load(paymentActivityModels[position].paymentGatewayProviderLogo)
            .into(holder.binding.ivIcon)

        if (paymentActivityModels[position].paymentStatusName == "Finansallaştırma Bekliyor") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)

            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#4D908E")
            )

        } else {
            val paymentStatusColor: Enums.PaymentStatusColor? =
                paymentActivityModels[position].paymentStatusName?.let {
                    Enums.PaymentStatusColor.valueOf(
                        it
                    )
                }
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            if (paymentStatusColor != null) {
                DrawableCompat.setTint(
                    wrappedDrawable1,
                    Color.parseColor(paymentStatusColor.paymentStatusColor)
                )
            }
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(paymentActivityModels[position])
        }
    }

    override fun getItemCount(): Int {
        return paymentActivityModels.size
    }

    fun updatePaymentActivityModels(newPaymentActivityModels: List<PaymentActivityModel>) {
        paymentActivityModels.clear()
        paymentActivityModels.addAll(newPaymentActivityModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (paymentActivityModel: PaymentActivityModel) -> Unit) {
        fun onClick(paymentActivityModel: PaymentActivityModel) = clickListener(paymentActivityModel)
    }
}