package com.sakb.spl.ui.mypoints.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerMasterItemItem

class MyPointPlayersChildItemMenuAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<PlayerMasterItemItem>,
    val parentPositions: Int = -1,
) : RecyclerView.Adapter<MyPointPlayersChildItemMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item_myteamplayers_recycler_menu, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (children[position].foundPlayer) {
            1 -> {
                when (children[position].typeKeyCoatch) {
                    "captain" -> {
                        holder.imageViewCaptain.visibility = View.VISIBLE
                        holder.imageViewViceCaptain.visibility = View.GONE
                    }
                    "sub_captain" -> {
                        holder.imageViewCaptain.visibility = View.GONE
                        holder.imageViewViceCaptain.visibility = View.VISIBLE
                    }
                    else -> {
                        holder.imageViewCaptain.visibility = View.GONE
                        holder.imageViewViceCaptain.visibility = View.GONE
                    }
                }

                holder.textView.text = children[position].namePlayer
                holder.textViewCost.text = children[position].costPlayer.toString()

            }
            else -> {
                holder.textView.text = children[position].typeLocPlayer
            }
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.nameTv)
        val textViewCost: TextView = itemView.findViewById(R.id.priceTv)
        val textViewPoints: TextView = itemView.findViewById(R.id.pointTv)
        val textViewClub: TextView = itemView.findViewById(R.id.clubTv)
        val imageViewCaptain: ImageView = itemView.findViewById(R.id.captainIv)
        val imageViewViceCaptain: ImageView = itemView.findViewById(R.id.viceIv)
        //  val imageView: ImageView = itemView.ima


        init {
            itemView.setOnClickListener {
            }
        }


    }
}