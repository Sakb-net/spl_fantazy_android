package com.sakb.spl.ui.help.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.ContentItemsItem
import com.sakb.spl.data.model.HowToPlayResponse

class AwardDiffCallback : DiffUtil.ItemCallback<ContentItemsItem>() {


    override fun areItemsTheSame(
        oldItem: ContentItemsItem,
        newItem: ContentItemsItem
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: ContentItemsItem,
        newItem: ContentItemsItem
    ): Boolean {
        return oldItem == newItem
    }
}