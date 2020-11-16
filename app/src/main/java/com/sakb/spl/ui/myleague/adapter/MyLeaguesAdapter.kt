package com.sakb.spl.ui.myleague.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.GroupEldwryItem

class MyLeaguesAdapter(
    private var parents: List<GroupEldwryItem?>?,
    private var user_id: Int?,
    val settingClick: (link: String, admin: Boolean) -> Unit,
) : RecyclerView.Adapter<MyLeaguesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_league, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents?.get(position)
        holder.bind(parent)
        holder.leagueSettings.setOnClickListener {
            if (user_id == parent?.userId)
                settingClick(parent?.link ?: "", true)
            else
                settingClick(parent?.link ?: "", false)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val leagueName: AppCompatTextView = itemView.findViewById(R.id.league_name)
        val leagueOrder: AppCompatTextView = itemView.findViewById(R.id.league_value)
        val leagueSettings: AppCompatImageView = itemView.findViewById(R.id.league_setting)

        fun bind(parent: GroupEldwryItem?) {
            leagueName.text = parent?.name
            leagueOrder.text = parent?.currentSort
        }
    }
}