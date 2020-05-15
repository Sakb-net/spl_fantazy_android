package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse

class FixturesDiffCallback : DiffUtil.ItemCallback< HomeResponse.MatchGroup>() {
    override fun areItemsTheSame(
        oldItem: HomeResponse.MatchGroup,
        newItem: HomeResponse.MatchGroup
    ): Boolean {
        return oldItem.linkFirst == newItem.linkFirst && oldItem.linkSecond == newItem.linkSecond
    }

    override fun areContentsTheSame(
        oldItem: HomeResponse.MatchGroup,
        newItem: HomeResponse.MatchGroup
    ): Boolean {
       return oldItem == newItem
    }


}