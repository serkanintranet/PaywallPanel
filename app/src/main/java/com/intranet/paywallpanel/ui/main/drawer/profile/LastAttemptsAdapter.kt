package com.intranet.paywallpanel.ui.main.drawer.profile

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
import com.intranet.paywallpanel.databinding.ItemLastAttemptsBinding
import com.intranet.paywallpanel.databinding.ItemLastTransactionsBinding
import com.intranet.paywallpanel.network.payment.model.TransferModel
import com.intranet.paywallpanel.network.user.model.AttemptsModel
import com.intranet.paywallpanel.util.GlobalData

class LastAttemptsAdapter(
    private val onClickListener: OnClickListener,
    private val context: Context,
    private val attemptModels: ArrayList<AttemptsStaticModel?>
) : RecyclerView.Adapter<LastAttemptsAdapter.LastAttemptsViewHolder>() {

    class LastAttemptsViewHolder(val binding: ItemLastAttemptsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastAttemptsViewHolder {
        val binding = ItemLastAttemptsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LastAttemptsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LastAttemptsViewHolder, position: Int) {

        holder.binding.tvDate.text = GlobalData.dateFormatWithTime((attemptModels[position]?.model?.date ?: "0001-01-01T00:00:00"))
        if (attemptModels[position]?.status == 1) {
            holder.binding.tvStatus.text = "Başarılı Giriş"
            val unwrappedDrawable1: Drawable = holder.binding.rlIndicator.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(wrappedDrawable1, Color.parseColor("#4CAF50"))
        } else {
            holder.binding.tvStatus.text = "Başarısız Giriş"
            val unwrappedDrawable1: Drawable = holder.binding.rlIndicator.background
            val wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1)
            DrawableCompat.setTint(
                wrappedDrawable1,
                Color.parseColor("#D30000")
            )
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(attemptModels[position])
        }
    }

    override fun getItemCount(): Int {
        return attemptModels.size
    }

    fun updateAttemptModels(newAttemptModels: List<AttemptsStaticModel?>) {
        attemptModels.clear()
        attemptModels.addAll(newAttemptModels)
        notifyDataSetChanged()

    }

    class OnClickListener(val clickListener: (attemptModel: AttemptsStaticModel?) -> Unit) {
        fun onClick(attemptModel: AttemptsStaticModel?) = clickListener(attemptModel)
    }
}