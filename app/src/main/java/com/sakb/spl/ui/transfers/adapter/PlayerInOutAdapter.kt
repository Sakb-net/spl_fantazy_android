package com.sakb.spl.ui.transfers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.PlayerResponse

class PlayerInOutAdapter(
    var context: Context,
    var playerInResponseList: ArrayList<PlayerResponse>,
    var playerOutResponseList: ArrayList<PlayerResponse>,
) : RecyclerView.Adapter<PlayerInOutAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PlayerInOutAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_in_out, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlayerInOutAdapter.ViewHolder, position: Int) {
        if (playerInResponseList.size > position && playerOutResponseList.size > position) {
            val playerInResponse = playerInResponseList[position]
            val playerOutResponse = playerOutResponseList[position]
            holder.bind(playerInResponse, playerOutResponse)
        }
    }

    override fun getItemCount(): Int {
        return if (playerInResponseList.size >= playerOutResponseList.size) {
            playerInResponseList.size
        } else {
            playerOutResponseList.size
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoIn: AppCompatImageView = itemView.findViewById(R.id.logoIn)
        val logoOut: AppCompatImageView = itemView.findViewById(R.id.logoOut)
        val nameIn: AppCompatTextView = itemView.findViewById(R.id.nameIn)
        val nameOut: AppCompatTextView = itemView.findViewById(R.id.nameOut)
        val teamIn: AppCompatTextView = itemView.findViewById(R.id.teamIn)
        val teamOut: AppCompatTextView = itemView.findViewById(R.id.teamOut)
        fun bind(playerInResponse: PlayerResponse, playerOutResponse: PlayerResponse) {
            Glide.with(context).load(Constants.baseUrl + playerInResponse.data?.playerData?.image)
                .into(logoIn)
            Glide.with(context).load(Constants.baseUrl + playerOutResponse.data?.playerData?.image)
                .into(logoOut)
            nameIn.text = playerInResponse.data?.playerData?.name
            nameOut.text = playerOutResponse.data?.playerData?.name
            teamIn.text = playerInResponse.data?.playerData?.team
            teamOut.text = playerOutResponse.data?.playerData?.team
        }
    }

    fun updateIn(player: PlayerResponse) {
        if (!playerInResponseList.contains(player)) {
            playerInResponseList.add(player)
        }
        notifyDataSetChanged()
    }

    fun updateOut(player: PlayerResponse) {
        if (!playerOutResponseList.contains(player)) {
            playerOutResponseList.add(player)
        }
        notifyDataSetChanged()
    }
}
