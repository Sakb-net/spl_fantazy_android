package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.DataItem
import com.sakb.spl.data.model.HomeResponse

class FixturesHeaderDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(
        oldItem: DataItem,
        newItem: DataItem
    ): Boolean {
        return oldItem.startDate == newItem.startDate
    }

    override fun areContentsTheSame(
        oldItem: DataItem,
        newItem: DataItem
    ): Boolean {
        return oldItem == newItem
    }


}