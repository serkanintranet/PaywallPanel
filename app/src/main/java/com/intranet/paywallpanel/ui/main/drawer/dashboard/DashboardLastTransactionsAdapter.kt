package com.intranet.paywallpanel.ui.main.drawer.dashboard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.intranet.paywallpanel.R
import com.intranet.paywallpanel.databinding.ItemDashboardBinding
import com.intranet.paywallpanel.databinding.ItemLastTransactionsBinding
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.util.Enums
import com.intranet.paywallpanel.util.GlobalData

class DashboardLastTransactionsAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val dashboardLastTransactionModels: ArrayList<TransferModel?>
) : RecyclerView.Adapter<DashboardLastTransactionsAdapter.DashboardLastTransactionsViewHolder>() {

    class DashboardLastTransactionsViewHolder(val binding: ItemLastTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardLastTransactionsViewHolder {
        val binding =
            ItemLastTransactionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardLastTransactionsAdapter.DashboardLastTransactionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardLastTransactionsViewHolder, position: Int) {

        holder.binding.tvDate.text = GlobalData.dateFormatWithTime(
            (dashboardLastTransactionModels[position]?.dateTime ?: "0001-01-01T00:00:00")
        )
        holder.binding.tvAmount.text = GlobalData.moneyFormatToShow(
            (dashboardLastTransactionModels[position]?.amount ?: 0F).toString(), 1
        )
        holder.binding.tvName.text = dashboardLastTransactionModels[position]?.cardOwnerName ?: ""
        holder.binding.tvType.text = dashboardLastTransactionModels[position]?.status ?: ""

        if (dashboardLastTransactionModels[position]?.status == "Finansallaştırma Bekliyor") {
            val unwrappedDrawable1: Drawable = holder.binding.rlIndicator.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)

            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#4D908E")
            )

        } else {
            val paymentStatusColor: Enums.PaymentStatusColor? =
                dashboardLastTransactionModels[position]?.status?.let {
                    Enums.PaymentStatusColor.valueOf(
                        it
                    )
                }
            val unwrappedDrawable1: Drawable = holder.binding.rlIndicator.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            if (paymentStatusColor != null) {
                DrawableCompat.setTint(
                    wrappedDrawable1,
                    Color.parseColor(paymentStatusColor.paymentStatusColor)
                )
            }
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(dashboardLastTransactionModels[position])
        }
    }

    override fun getItemCount(): Int {
        return dashboardLastTransactionModels.size
    }

    fun updateDashboardLastTransactionModels(
        newDashboardLastTransactionModels: List<TransferModel?>,
        clear: Boolean
    ) {
        if (clear) {
            dashboardLastTransactionModels.clear()
        }
        dashboardLastTransactionModels.addAll(newDashboardLastTransactionModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (dashboardLastTransactionModels: TransferModel?) -> Unit) {
        fun onClick(dashboardLastTransactionModels: TransferModel?) =
            clickListener(dashboardLastTransactionModels)
    }
}