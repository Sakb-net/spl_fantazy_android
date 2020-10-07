package com.sakb.spl.ui.howtoplay.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HowToPlayResponse

class InstructionsDiffCallback : DiffUtil.ItemCallback<HowToPlayResponse.ContentRole>() {


    override fun areItemsTheSame(
        oldItem: HowToPlayResponse.ContentRole,
        newItem: HowToPlayResponse.ContentRole
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: HowToPlayResponse.ContentRole,
        newItem: HowToPlayResponse.ContentRole
    ): Boolean {
        return oldItem == newItem
    }
}