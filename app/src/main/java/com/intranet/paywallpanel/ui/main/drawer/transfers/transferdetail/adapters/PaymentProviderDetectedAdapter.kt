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
import com.intranet.paywallpanel.databinding.ItemChosenProvidersBinding
import com.intranet.paywallpanel.databinding.ItemDashboardBinding
import com.intranet.paywallpanel.databinding.ItemProcessStepBinding
import com.intranet.paywallpanel.network.payment.model.PaymentActivityModel
import com.intranet.paywallpanel.network.payment.model.PaymentProviderDetectedModel
import com.intranet.paywallpanel.util.Enums

class PaymentProviderDetectedAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val paymentProviderDetectedModels: ArrayList<PaymentProviderDetectedModel>
) : RecyclerView.Adapter<PaymentProviderDetectedAdapter.PaymentProviderDetectedViewHolder>() {

    class PaymentProviderDetectedViewHolder(val binding: ItemChosenProvidersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentProviderDetectedViewHolder {
        val binding = ItemChosenProvidersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentProviderDetectedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentProviderDetectedViewHolder, position: Int) {

        holder.binding.tvCommission.text = "K: " + paymentProviderDetectedModels[position].commission ?: ""

        if (paymentProviderDetectedModels[position].paymentGatewayProviderName == "EsnekPos") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#322AC7")
            )
        } else if (paymentProviderDetectedModels[position].paymentGatewayProviderName == "AkBank") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#DF392E")
            )
        } else if (paymentProviderDetectedModels[position].paymentGatewayProviderName == "Kuveyt Türk") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#046648")
            )
        } else {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#005c82")
            )
        }

        Glide.with(context).load(paymentProviderDetectedModels[position].paymentGatewayProviderLogo)
            .into(holder.binding.ivIcon)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(paymentProviderDetectedModels[position])
        }
    }

    override fun getItemCount(): Int {
        return paymentProviderDetectedModels.size
    }

    fun updatePaymentProviderDetectedModels(newPaymentProviderDetectedModels: List<PaymentProviderDetectedModel>) {
        paymentProviderDetectedModels.clear()
        paymentProviderDetectedModels.addAll(newPaymentProviderDetectedModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (paymentProviderDetectedModel: PaymentProviderDetectedModel) -> Unit) {
        fun onClick(paymentProviderDetectedModel: PaymentProviderDetectedModel) = clickListener(paymentProviderDetectedModel)
    }
}