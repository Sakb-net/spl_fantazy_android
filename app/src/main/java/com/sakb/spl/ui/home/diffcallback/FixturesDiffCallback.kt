package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.data.model.MatchGroupItem

class FixturesDiffCallback : DiffUtil.ItemCallback<MatchGroupItem>() {
    override fun areItemsTheSame(
        oldItem: MatchGroupItem,
        newItem: MatchGroupItem
    ): Boolean {
        return oldItem.linkFirst == newItem.linkFirst && oldItem.linkSecond == newItem.linkSecond
    }

    override fun areContentsTheSame(
        oldItem: MatchGroupItem,
        newItem: MatchGroupItem
    ): Boolean {
        return oldItem == newItem
    }


}