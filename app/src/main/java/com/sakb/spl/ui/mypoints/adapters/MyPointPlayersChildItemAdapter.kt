package com.sakb.spl.ui.mypoints.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.PlayerMasterItemItem

class MyPointPlayersChildItemAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<PlayerMasterItemItem>,
    val parentPositions: Int = -1,
) : RecyclerView.Adapter<MyPointPlayersChildItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item_mypoint_recycler, parent, false)
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

                Glide.with(holder.imageView.context)
                    .load(Constants.baseUrl + children[position].imagePlayer)
                    .override(42)
                    .into(holder.imageView)

                holder.textView.text = children[position].namePlayer
                holder.textViewCost.text = children[position].costPlayer.toString()

            }
            else -> {
                holder.textView.text = children[position].typeLocPlayer
            }
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val textView: TextView = itemView.findViewById(R.id.child_textView)
        val textViewCost: TextView = itemView.findViewById(R.id.child_cost_textView)
        val imageView: ImageView = itemView.findViewById(R.id.child_imageView)
        val imageViewCaptain: ImageView = itemView.findViewById(R.id.captainIv)
        val imageViewViceCaptain: ImageView = itemView.findViewById(R.id.viceIv)


        init {
            itemView.setOnClickListener {

            }
        }
    }
}