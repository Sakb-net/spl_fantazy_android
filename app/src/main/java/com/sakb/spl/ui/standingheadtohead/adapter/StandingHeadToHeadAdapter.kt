package com.sakb.spl.ui.standingheadtohead.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.UsersGroupItem

class StandingHeadToHeadAdapter(
    private var parents: List<UsersGroupItem?>?,
) : RecyclerView.Adapter<StandingHeadToHeadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_standing, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents?.get(position)
        holder.bind(parent)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val team_name: AppCompatTextView = itemView.findViewById(R.id.team_name)
        val name: AppCompatTextView = itemView.findViewById(R.id.name)
        val order: AppCompatTextView = itemView.findViewById(R.id.order)
        val point: AppCompatTextView = itemView.findViewById(R.id.point)


        fun bind(parent: UsersGroupItem?) {
            team_name.text = parent?.nameTeam
            name.text = parent?.displayName
            order.text = parent?.sort
            point.text = parent?.gwPoints
        }
    }
}