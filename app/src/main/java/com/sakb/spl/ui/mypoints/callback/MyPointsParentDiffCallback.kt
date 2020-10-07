package com.sakb.spl.ui.mypoints.callback

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.PlayerMasterResponse

class MyPointsParentDiffCallback : DiffUtil.ItemCallback<List<PlayerMasterResponse.Data>>() {
    override fun areItemsTheSame(
        oldItem: List<PlayerMasterResponse.Data>,
        newItem: List<PlayerMasterResponse.Data>
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: List<PlayerMasterResponse.Data>,
        newItem: List<PlayerMasterResponse.Data>
    ): Boolean {

        return oldItem == newItem
    }


}