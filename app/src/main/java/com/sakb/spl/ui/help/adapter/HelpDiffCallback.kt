package com.sakb.spl.ui.help.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sakb.spl.data.model.HowToPlayResponse

class HelpDiffCallback : DiffUtil.ItemCallback<HowToPlayResponse.ContentHelp>() {



    override fun areItemsTheSame(
        oldItem: HowToPlayResponse.ContentHelp,
        newItem: HowToPlayResponse.ContentHelp
    ): Boolean {
       return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: HowToPlayResponse.ContentHelp,
        newItem: HowToPlayResponse.ContentHelp
    ): Boolean {
       return oldItem == newItem
    }
}