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

class DashboardAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val dashboardModels: ArrayList<DashboardStaticModel>
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    class DashboardViewHolder(val binding: ItemDashboardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val binding = ItemDashboardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DashboardAdapter.DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {

        holder.binding.tvHeader.text = dashboardModels[position].title
        holder.binding.tvBody.text = dashboardModels[position].amount

        if (dashboardModels[position].title == "Transfer Tutarı") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.coins))
        } else if (dashboardModels[position].title == "Transfer Adedi") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.counter))
        } else if (dashboardModels[position].title == "Başarılı Transfer Tutarı") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.success_transfer_amount))
        } else if (dashboardModels[position].title == "Başarılı Transfer Adedi") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.success_transfer_count))
        } else if (dashboardModels[position].title == "Başarısız Transfer Tutarı") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unsuccess_transfer_amount))
        } else if (dashboardModels[position].title == "Başarısız Transfer Adedi") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.unsuccess_transfer_count))
        } else if (dashboardModels[position].title == "İade Adedi") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.return_count))
        } else if (dashboardModels[position].title == "İptal Adedi") {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cancel_count))
        } else {
            holder.binding.ivIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.coins))
        }

        val unwrappedDrawable1: Drawable = holder.binding.clRoot.background
        val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
        DrawableCompat.setTint(
            wrappedDrawable1,
            Color.parseColor(dashboardModels[position].color)
        )

        holder.itemView.setOnClickListener {
            onClickListener.onClick(dashboardModels[position])
        }
    }

    override fun getItemCount(): Int {
        return dashboardModels.size
    }

    fun updateDashboardModels(newDashboardModels: List<DashboardStaticModel>) {
        dashboardModels.clear()
        dashboardModels.addAll(newDashboardModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (dashboardModels: DashboardStaticModel) -> Unit) {
        fun onClick(dashboardModels: DashboardStaticModel) = clickListener(dashboardModels)
    }
}