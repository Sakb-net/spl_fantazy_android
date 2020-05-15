package com.sakb.spl.ui.mypoints.callback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.PlayerMasterResponse

class MyPointsDiffCallback : DiffUtil.ItemCallback<PlayerMasterResponse.Data>() {
    override fun areItemsTheSame(
        oldItem: PlayerMasterResponse.Data,
        newItem: PlayerMasterResponse.Data
    ): Boolean {
        return oldItem.link_player == newItem.link_player
    }
    override fun areContentsTheSame(
        oldItem: PlayerMasterResponse.Data,
        newItem: PlayerMasterResponse.Data
    ): Boolean {

        return oldItem == newItem
    }


}