package com.intranet.paywallpanel.ui.main.drawer.paymentgateways

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
import com.intranet.paywallpanel.databinding.ItemGatewaysBinding
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.PaymentGatewayProviderBodyModel

class GatewaysAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val gatewayProviderModels: ArrayList<PaymentGatewayProviderBodyModel?>
) : RecyclerView.Adapter<GatewaysAdapter.GatewaysViewHolder>() {

    class GatewaysViewHolder(val binding: ItemGatewaysBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GatewaysViewHolder {
        val binding = ItemGatewaysBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GatewaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GatewaysViewHolder, position: Int) {

        if (gatewayProviderModels[position]?.isConnected == true) {
            holder.binding.ivArrow1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check))
            holder.binding.tvStatus.text = context.getString(R.string.connected)
        } else {
            holder.binding.ivArrow1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.right_arrow_white))
            holder.binding.tvStatus.text = context.getString(R.string.connect)
        }

        Glide.with(context).load(gatewayProviderModels[position]?.logoUrl)
            .into(holder.binding.ivCompany)

        if (gatewayProviderModels[position]?.name == "EsnekPos") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#322AC7")
            )
        } else if (gatewayProviderModels[position]?.name == "AkBank") {
            val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#DF392E")
            )
        } else if (gatewayProviderModels[position]?.name == "Kuveyt Türk") {
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

        holder.itemView.setOnClickListener {
            onClickListener.onClick(gatewayProviderModels[position])
        }
    }

    override fun getItemCount(): Int {
        return gatewayProviderModels.size
    }

    fun updateGatewayProviderModels(newGatewayProviderModels: List<PaymentGatewayProviderBodyModel?>) {
        gatewayProviderModels.clear()
        gatewayProviderModels.addAll(newGatewayProviderModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (gatewayProviderModels: PaymentGatewayProviderBodyModel?) -> Unit) {
        fun onClick(gatewayProviderModels: PaymentGatewayProviderBodyModel?) = clickListener(gatewayProviderModels)
    }
}