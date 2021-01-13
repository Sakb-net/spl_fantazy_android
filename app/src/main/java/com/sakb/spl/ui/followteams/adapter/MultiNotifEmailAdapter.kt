package com.sakb.spl.ui.followteams.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.FollowTeamRequest

class MultiNotifEmailAdapter(
    private var teams: ArrayList<FollowTeamRequest>,
    var onCLick: (ArrayList<FollowTeamRequest>) -> Unit,
) :
    RecyclerView.Adapter<MultiNotifEmailAdapter.MultiViewHolder>() {

    var teamsResult: ArrayList<FollowTeamRequest> = teams

    @NonNull
    override fun onCreateViewHolder(
        @NonNull viewGroup: ViewGroup,
        i: Int,
    ): MultiNotifEmailAdapter.MultiViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_team_notification_option, viewGroup, false)
        return MultiViewHolder(view)
    }

    override fun onBindViewHolder(
        @NonNull multiViewHolder: MultiNotifEmailAdapter.MultiViewHolder,
        position: Int,
    ) {
        teams[position].let { multiViewHolder.bind(it, position) }
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    inner class MultiViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val email: ImageView = itemView.findViewById(R.id.imageView_email)
        private val notif: ImageView = itemView.findViewById(R.id.imageView_notification)

        fun bind(oneTeam: FollowTeamRequest, position: Int) {

            textView.text = oneTeam.name

            if (oneTeam.isEmail == 1) {
                email.setBackgroundResource(R.drawable.square_point)
                teamsResult[position].isEmail = 1
            } else {
                email.setBackgroundResource(R.drawable.square_dark)
                teamsResult[position].isEmail = 0
            }
            if (oneTeam.isNotif == 1) {
                notif.setBackgroundResource(R.drawable.square_point)
                teamsResult[position].isNotif = 1
            } else {
                notif.setBackgroundResource(R.drawable.square_dark)
                teamsResult[position].isNotif = 0
            }

            email.setOnClickListener {
                if (oneTeam.isEmail == 1) {
                    email.setBackgroundResource(R.drawable.square_dark)
                    teamsResult[position].isEmail = 0
                    onCLick(teamsResult)
                } else {
                    email.setBackgroundResource(R.drawable.square_point)
                    teamsResult[position].isEmail = 1
                    onCLick(teamsResult)
                }
            }
            notif.setOnClickListener {
                if (oneTeam.isNotif == 1) {
                    notif.setBackgroundResource(R.drawable.square_dark)
                    teamsResult[position].isNotif = 0
                    onCLick(teamsResult)
                } else {
                    notif.setBackgroundResource(R.drawable.square_point)
                    teamsResult[position].isNotif = 1
                    onCLick(teamsResult)
                }
            }
        }
    }
}