package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse

class VideosDiffCallback : DiffUtil.ItemCallback<HomeResponse.Video>() {
    override fun areItemsTheSame(
        oldItem: HomeResponse.Video,
        newItem: HomeResponse.Video
    ): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(
        oldItem: HomeResponse.Video,
        newItem: HomeResponse.Video
    ): Boolean {
        return oldItem == newItem
    }


}