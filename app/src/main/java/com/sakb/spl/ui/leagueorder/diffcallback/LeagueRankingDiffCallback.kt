package com.sakb.spl.ui.leagueorder.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.DataItemSubFix
import com.sakb.spl.data.model.RankingEldwryItem

class LeagueRankingDiffCallback : DiffUtil.ItemCallback<RankingEldwryItem>() {
    override fun areItemsTheSame(
        oldItem: RankingEldwryItem,
        newItem: RankingEldwryItem
    ): Boolean {
        return oldItem.teamLink == newItem.teamLink
    }

    override fun areContentsTheSame(
        oldItem: RankingEldwryItem,
        newItem: RankingEldwryItem
    ): Boolean {
        return oldItem == newItem
    }
}