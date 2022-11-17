package com.intranet.paywallpanel.ui.main.drawer.paymentgateways.paymentgatewaydetail.adapters

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
import com.intranet.paywallpanel.databinding.ItemCommissionInformationBinding
import com.intranet.paywallpanel.databinding.ItemDashboardBinding
import com.intranet.paywallpanel.databinding.ItemProcessStepBinding
import com.intranet.paywallpanel.network.payment.model.PaymentActivityModel
import com.intranet.paywallpanel.network.paymentgatewayprovider.model.CommissionModel
import com.intranet.paywallpanel.util.Enums
import com.intranet.paywallpanel.util.GlobalData

class CommissionInformationAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val commissionModels: ArrayList<CommissionModel>
) : RecyclerView.Adapter<CommissionInformationAdapter.CommissionInformationViewHolder>() {

    class CommissionInformationViewHolder(val binding: ItemCommissionInformationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommissionInformationViewHolder {
        val binding = ItemCommissionInformationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommissionInformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommissionInformationViewHolder, position: Int) {

        holder.binding.tvInstallmentName.text = commissionModels[position].installementName ?: ""
        holder.binding.tvCommission.text = (commissionModels[position].commission ?: 0F).toString()

        holder.itemView.setOnClickListener {
            onClickListener.onClick(commissionModels[position])
        }
    }

    override fun getItemCount(): Int {
        return commissionModels.size
    }

    fun updateCommissionModels(newCommissionModels: List<CommissionModel>) {
        commissionModels.clear()
        commissionModels.addAll(newCommissionModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (commissionModel: CommissionModel) -> Unit) {
        fun onClick(commissionModel: CommissionModel) = clickListener(commissionModel)
    }
}