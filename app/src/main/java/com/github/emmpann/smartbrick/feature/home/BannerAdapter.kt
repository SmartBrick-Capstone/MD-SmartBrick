package com.github.emmpann.smartbrick.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.emmpann.smartbrick.core.data.remote.response.Article
import com.github.emmpann.smartbrick.databinding.BannerItemBinding

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listArticle: ArrayList<Article> = arrayListOf()

    interface OnItemClickCallback {
        fun onItemClicked(article: Article)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun submitList(listArticle: List<Article>) {
        this.listArticle.addAll(listArticle)
        if (listArticle.size > 1) notifyItemRangeChanged(0, listArticle.lastIndex) else notifyItemInserted(0)
    }

    inner class ViewHolder(private val binding: BannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvBannerDate.text = article.date
                tvBannerTitle.text = article.title
                Glide.with(binding.root.context)
                    .load(article.image)
                    .into(ivArticle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = listArticle.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(article) }
    }
}