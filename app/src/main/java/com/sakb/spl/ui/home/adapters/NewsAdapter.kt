package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.ItemHomeNewsBinding
import com.sakb.spl.ui.home.diffcallback.NewsDiffCallback

class NewsAdapter :
    ListAdapter<HomeResponse.New, NewsAdapter.FixturesViewHolder>(NewsDiffCallback()) {

    var onClickListener: ((HomeResponse.New) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onClickListener
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class FixturesViewHolder(
        private val binding: ItemHomeNewsBinding,
        private val onNewsListener: ((HomeResponse.New) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: HomeResponse.New) = data.run {
            itemView.setOnClickListener { onNewsListener?.invoke(this) }
            binding.textViewTitle.text = name
            binding.textViewSubtitle.text = createdAt
            Glide.with(binding.imageViewThumbNews).load(Constants.baseUrl + image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageViewThumbNews)
        }
    }

    fun onClear() {
        onClickListener = null
    }
}