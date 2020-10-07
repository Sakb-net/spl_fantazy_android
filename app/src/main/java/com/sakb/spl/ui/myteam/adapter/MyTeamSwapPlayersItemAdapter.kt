package com.sakb.spl.ui.myteam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.MyteamPlayersResponse
import kotlinx.android.synthetic.main.child_item_myteam_recycler.view.*
import timber.log.Timber

class MyTeamSwapPlayersItemAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<MyteamPlayersResponse.Player>,
    val parentPositions: Int = -1,
    var onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null

) : RecyclerView.Adapter<MyTeamSwapPlayersItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item_myteam_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (children[position].found_player) {
            1 -> {


                if (children[position].type_loc_player == "goalkeeper") {
                    holder.textViewType.text = "GK"
                } else {
                    holder.textViewType.text = position.toString()
                }

                if (children[position].isSelected)
                    holder.container.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.colorYellow
                        )
                    )
                else if (children[position].isActiveToSwap) {
                    holder.container.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.white
                        )
                    )
                } else {
                    holder.container.background = null

                }
                // change alpha color
                holder.imageView.alpha = children[position].alPha
                holder.textView.alpha = children[position].alPha
                holder.textViewCost.alpha = children[position].alPha

                Glide.with(holder.imageView.context)
                    .load(Constants.baseUrl + children[position].image_player)
                    .override(39)
                    .into(holder.imageView)

                holder.textView.text = children[position].name_player
                holder.textViewCost.text = children[position].cost_player.toString()

            }
            else -> {
                holder.textView.text = children[position].type_loc_player
            }
        }
        //  val child = playerMaster.
        /*   val child = children[position]
           holder.imageView.setImageResource(child.image)
           holder.textView.text = child.title*/
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: ConstraintLayout = itemView.container
        val textView: TextView = itemView.child_textView
        val textViewCost: TextView = itemView.child_cost_textView
        val imageView: ImageView = itemView.child_imageView
        val textViewType: TextView = itemView.type

        init {
            itemView.setOnClickListener {
                Timber.e("================${children[adapterPosition].isSelected}")
                onItemClicked?.invoke(adapterPosition, parentPositions, children[adapterPosition])
            }
        }


    }
}