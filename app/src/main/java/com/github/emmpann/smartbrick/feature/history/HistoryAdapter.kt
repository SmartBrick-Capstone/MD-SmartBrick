package com.github.emmpann.smartbrick.feature.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.emmpann.smartbrick.core.data.remote.response.History
import com.github.emmpann.smartbrick.databinding.HistoryItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listHistory: ArrayList<History> = arrayListOf()

    interface OnItemClickCallback {
        fun onItemClicked(article: History)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun submitList(listHistory: List<History>) {
        this.listHistory.addAll(listHistory)
        if (listHistory.size > 1) notifyItemRangeChanged(
            0,
            listHistory.lastIndex
        ) else notifyItemInserted(0)
    }

    inner class ViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            with(binding) {
                tvHistoryTitle.text = history.title
                tvDate.text = history.date
                when (history.status) {
                    "Sukses" -> {
                        cvStatus.setCardBackgroundColor(Color.parseColor("#46C31A"))
                        tvStatus.text = history.status
                    }

                    "Gagal" -> {
                        cvStatus.setCardBackgroundColor(Color.parseColor("#FF0000"))
                        tvStatus.text = history.status
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int = listHistory.size
}