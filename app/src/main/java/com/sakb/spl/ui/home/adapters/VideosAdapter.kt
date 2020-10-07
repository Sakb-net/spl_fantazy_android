package com.sakb.spl.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.databinding.ItemHomeVideoBinding
import com.sakb.spl.ui.home.diffcallback.VideosDiffCallback


class VideosAdapter :
    ListAdapter<HomeResponse.Video, VideosAdapter.FixturesViewHolder>(VideosDiffCallback()) {

    var onClickListener: ((HomeResponse.Video) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FixturesViewHolder(
            ItemHomeVideoBinding.inflate(
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
        private val binding: ItemHomeVideoBinding,
        private val onNewsListener: ((HomeResponse.Video) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: HomeResponse.Video) = data.run {
            itemView.setOnClickListener { onNewsListener?.invoke(this) }

            binding.textViewTitleVideo.text = name
            binding.textViewSubtitleVideo.text = createdAt
            Glide.with(binding.imageViewThumbVideo).load(Constants.baseUrl + image)
                .override(150).diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imageViewThumbVideo)
        }
    }

    fun onClear() {
        onClickListener = null
    }
}