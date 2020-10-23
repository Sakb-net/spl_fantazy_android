package com.sakb.spl.ui.playerprofile.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse
import com.sakb.spl.data.model.MatchGroupItem
import com.sakb.spl.data.model.StatisticsDataItem

class MatchesDiffCallback : DiffUtil.ItemCallback<StatisticsDataItem>() {
    override fun areItemsTheSame(
        oldItem: StatisticsDataItem,
        newItem: StatisticsDataItem
    ): Boolean {
        return oldItem.againestTeam == newItem.againestTeam && oldItem.ownTeam == newItem.ownTeam
    }

    override fun areContentsTheSame(
        oldItem: StatisticsDataItem,
        newItem: StatisticsDataItem
    ): Boolean {
        return oldItem == newItem
    }


}