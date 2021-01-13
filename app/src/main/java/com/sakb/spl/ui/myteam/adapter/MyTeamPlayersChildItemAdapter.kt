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

class MyTeamPlayersChildItemAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<MyteamPlayersResponse.Player>,
    val parentPositions: Int = -1,
    var onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onChangeClick: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onOpenProfileClicked: ((pos: Int, MyteamPlayersResponse.Player) -> Unit)? = null,
    var onResetClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)?,
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null,


    ) : RecyclerView.Adapter<MyTeamPlayersChildItemAdapter.ViewHolder>() {

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

                if (children[position].isActiveToSwap) {
                    holder.container.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.colorYellow
                        )
                    )
                } else {
                    holder.container.background = null

                }

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
                holder.imageView.alpha = children[position].alPha
                holder.textView.alpha = children[position].alPha
                holder.textViewCost.alpha = children[position].alPha

                Glide.with(holder.imageView.context)
                    .load(Constants.baseUrl + children[position].image_player)
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
        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val textView: TextView = itemView.findViewById(R.id.child_textView)
        val textViewCost: TextView = itemView.findViewById(R.id.child_cost_textView)
        val imageView: ImageView = itemView.findViewById(R.id.child_imageView)
        val imageViewCaptain: ImageView = itemView.findViewById(R.id.captainIv)
        val imageViewViceCaptain: ImageView = itemView.findViewById(R.id.viceIv)


        init {
            itemView.setOnClickListener {
                onItemClicked?.invoke(adapterPosition, parentPositions, children[adapterPosition])
            }


        }


    }
}