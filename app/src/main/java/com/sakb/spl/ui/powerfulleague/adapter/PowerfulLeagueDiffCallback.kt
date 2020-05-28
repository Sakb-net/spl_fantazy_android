package com.sakb.spl.ui.powerfulleague.adapter

import androidx.recyclerview.widget.DiffUtil

class PowerfulLeagueDiffCallback : DiffUtil.ItemCallback<PowerfulLeagueUIModel>() {



    override fun areItemsTheSame(
        oldItem: PowerfulLeagueUIModel,
        newItem: PowerfulLeagueUIModel
    ): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PowerfulLeagueUIModel,
        newItem: PowerfulLeagueUIModel
    ): Boolean {
       return oldItem == newItem
    }
}