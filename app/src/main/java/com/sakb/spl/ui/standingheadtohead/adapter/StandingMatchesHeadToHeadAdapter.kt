package com.sakb.spl.ui.standingheadtohead.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.internal.t
import com.sakb.spl.R
import com.sakb.spl.data.model.MatchGroupGroupItem
import com.sakb.spl.data.model.UsersGroupItem

class StandingMatchesHeadToHeadAdapter(
    private var parents: List<MatchGroupGroupItem?>?,
) : RecyclerView.Adapter<StandingMatchesHeadToHeadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_head_match_item, parent, false)
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
        val team1:AppCompatTextView = itemView.findViewById(R.id.team1_tv)
        val team1_name:AppCompatTextView = itemView.findViewById(R.id.team1_name_tv)
        val team1_result:AppCompatTextView = itemView.findViewById(R.id.date_result_team_one)
        val team2:AppCompatTextView = itemView.findViewById(R.id.team2_tv)
        val team2_name:AppCompatTextView = itemView.findViewById(R.id.team2_name_tv)
        val team2_result:AppCompatTextView = itemView.findViewById(R.id.date_result_team_two)

        fun bind(parent: MatchGroupGroupItem?) {
            team1.text = parent?.firstTeamName
            team1_name.text = parent?.firstUserName
            team1_result.text = parent?.firstTeamPoints

            team2.text = parent?.secondTeamName
            team2_name.text = parent?.secondUserName
            team2_result.text = parent?.secondTeamPoints
        }
    }
}