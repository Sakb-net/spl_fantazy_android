package com.sakb.spl.ui.videos.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.VideosResponse

class VideosDiffCallback : DiffUtil.ItemCallback<VideosResponse.Data>() {
    override fun areItemsTheSame(
        oldItem: VideosResponse.Data,
        newItem: VideosResponse.Data
    ): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(
        oldItem: VideosResponse.Data,
        newItem: VideosResponse.Data
    ): Boolean {
        return oldItem == newItem
    }


}