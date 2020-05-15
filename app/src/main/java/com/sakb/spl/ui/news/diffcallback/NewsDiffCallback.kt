package com.sakb.spl.ui.news.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.NewsResponse

class NewsDiffCallback : DiffUtil.ItemCallback<NewsResponse.Data>() {
    override fun areItemsTheSame(
        oldItem: NewsResponse.Data,
        newItem: NewsResponse.Data
    ): Boolean {
        return oldItem.link == newItem.link
    }

    override fun areContentsTheSame(
        oldItem: NewsResponse.Data,
        newItem: NewsResponse.Data
    ): Boolean {
        return oldItem == newItem
    }


}