package com.sakb.spl.ui.matches.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.DataItemSubFix
import com.sakb.spl.data.model.HomeResponse

class FixturesDiffCallback : DiffUtil.ItemCallback<DataItemSubFix>() {
    override fun areItemsTheSame(
        oldItem: DataItemSubFix,
        newItem: DataItemSubFix
    ): Boolean {
        return oldItem.linkFirst == newItem.linkFirst && oldItem.linkSecond == newItem.linkSecond
    }

    override fun areContentsTheSame(
        oldItem: DataItemSubFix,
        newItem: DataItemSubFix
    ): Boolean {
        return oldItem == newItem
    }


}