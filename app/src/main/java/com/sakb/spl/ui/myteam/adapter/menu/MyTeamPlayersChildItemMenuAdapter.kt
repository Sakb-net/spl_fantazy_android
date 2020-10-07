package com.sakb.spl.ui.myteam.adapter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.MyteamPlayersResponse
import kotlinx.android.synthetic.main.child_item_myteamplayers_recycler_menu.view.*

class MyTeamPlayersChildItemMenuAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<MyteamPlayersResponse.Player>,
    val parentPositions: Int = -1,
    var onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onChangeClick: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onOpenProfileClicked: ((pos: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onResetClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)?,
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null
) : RecyclerView.Adapter<MyTeamPlayersChildItemMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item_myteamplayers_recycler_menu, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (children[position].found_player) {
            1 -> {

//                if (children[position].isActiveToSwap){
//                    holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.colorYellow))
//                }
//                else{
//                    holder.itemView.setBackground(null)
//
//                }

                if (children[position].type_key_coatch == "captain") {
                    holder.imageViewCaptain.visibility = View.VISIBLE
                    holder.imageViewViceCaptain.visibility = View.GONE
                } else if (children[position].type_key_coatch == "sub_captain") {
                    holder.imageViewCaptain.visibility = View.GONE
                    holder.imageViewViceCaptain.visibility = View.VISIBLE
                } else {
                    holder.imageViewCaptain.visibility = View.GONE
                    holder.imageViewViceCaptain.visibility = View.GONE
                }
                // change alpha color
                holder.itemView.alpha = children[position].alPha
                holder.textView.alpha = children[position].alPha
                holder.textViewCost.alpha = children[position].alPha

//                Glide.with(holder.imageView.context)
//                    .load(Constants.baseUrl + children[position].image_player)
//                    .override(39)
//                    .into(holder.imageView)

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
        val textView: TextView = itemView.nameTv
        val textViewCost: TextView = itemView.priceTv
        val textViewPoints: TextView = itemView.pointTv
        val textViewClub: TextView = itemView.clubTv
        val imageViewCaptain: ImageView = itemView.captainIv
        val imageViewViceCaptain: ImageView = itemView.viceIv
        //  val imageView: ImageView = itemView.ima


        init {
            itemView.setOnClickListener {
                onItemClicked?.invoke(adapterPosition, parentPositions, children[adapterPosition])
            }
        }


    }
}