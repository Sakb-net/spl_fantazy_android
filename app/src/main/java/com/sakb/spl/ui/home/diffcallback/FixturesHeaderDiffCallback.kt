package com.sakb.spl.ui.home.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HomeResponse

class FixturesHeaderDiffCallback : DiffUtil.ItemCallback<HomeResponse.Fixture>() {
    override fun areItemsTheSame(
        oldItem: HomeResponse.Fixture,
        newItem: HomeResponse.Fixture
    ): Boolean {
        return oldItem.startDate == newItem.startDate
    }

    override fun areContentsTheSame(
        oldItem: HomeResponse.Fixture,
        newItem: HomeResponse.Fixture
    ): Boolean {
        return oldItem == newItem
    }


}