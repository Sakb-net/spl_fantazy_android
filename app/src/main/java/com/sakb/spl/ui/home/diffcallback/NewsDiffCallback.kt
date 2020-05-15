package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse

class NewsDiffCallback : DiffUtil.ItemCallback<HomeResponse.New>() {
    override fun areItemsTheSame(
        oldItem: HomeResponse.New,
        newItem: HomeResponse.New
    ): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(
        oldItem: HomeResponse.New,
        newItem: HomeResponse.New
    ): Boolean {
        return oldItem == newItem
    }


}