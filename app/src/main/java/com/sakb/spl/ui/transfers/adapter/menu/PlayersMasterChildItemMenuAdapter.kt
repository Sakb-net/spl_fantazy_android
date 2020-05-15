package com.sakb.spl.ui.transfers.adapter.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.ui.addplayer.AddPlayerActivity
import com.sakb.spl.utils.showSplDeleteDialog
import com.sakb.spl.utils.showSplDialog
import kotlinx.android.synthetic.main.transfers_child_item_team_master_recycler_menu.view.*

class PlayersMasterChildItemMenuAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<PlayerMasterResponse.Data>,
    val parentPositions: Int = -1,
    var onItemDeleteClick: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? = null,
    var onOpenProfileClicked: ((pos: Int, PlayerMasterResponse.Data) -> Unit)? = null,
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? = null


) : RecyclerView.Adapter<PlayersMasterChildItemMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.transfers_child_item_team_master_recycler_menu, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (children[position].found_player) {
            1 -> {

                // change alpha color
                holder.itemView.alpha = children[position].alPha
                holder.textView.alpha = children[position].alPha
                holder.textViewCost.alpha = children[position].alPha

                /*    Glide.with(holder.imageView.context)
                        .load(Constants.baseUrl + children[position].image_player)
                        .override(39)
                        .into(holder.imageView)*/

                holder.textView.text = children[position].name_player
                holder.textViewPoints.text = children[position].point_player.toString()
                holder.textViewCost.text = children[position].cost_player.toString()
                holder.textViewClub.text =
                    children[position].team?.plus(" - ")?.plus(children[position].type_player)

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
        //  val imageView: ImageView = itemView.ima


        init {
            itemView.setOnClickListener {
                when (children[adapterPosition].found_player) {
                    1 -> {
                        // if is transparent
                        if (children[adapterPosition].alPha == 0.5f)
                            itemView.context.showSplDeleteDialog({
                                it?.dismiss()
                                onOpenProfileClicked?.invoke(
                                    adapterPosition,
                                    children[adapterPosition]
                                )
                            }, {
                                it?.dismiss()
                                onRestorePlayerClicked?.invoke(
                                    adapterPosition,
                                    parentPositions,
                                    children[adapterPosition]
                                )
                            },
                                {
                                    it?.dismiss()
//                                    itemView.context.startActivity(
//                                        Intent(itemView.context, AddPlayerActivity::class.java)
//                                            .putExtra("replace", true)
//                                            .putExtra(
//                                                "old_link",
//                                                children[adapterPosition].link_player
//                                            )
//                                            .putExtra(
//                                                "type_loc_player",
//                                                children[adapterPosition].type_loc_player
//                                            )
//                                    )
                                    itemView.context.startActivity(
                                        Intent(itemView.context, AddPlayerActivity::class.java)
                                            .putExtra(
                                                AddPlayerActivity.ACTIONTYPE,
                                                AddPlayerActivity.REPLACE
                                            )
                                            .putExtra(AddPlayerActivity.DELETEDPLAYER,children[adapterPosition].link_player)
                                            .putExtra(AddPlayerActivity.ELDAWRYlINK,children[adapterPosition].eldwry_link)
                                            .putExtra(
                                                "type_loc_player",
                                                children[adapterPosition].type_loc_player
                                            )
                                    )
                                })
                        else
                        // if is not transparent
                            itemView.context.showSplDialog({
                                it?.dismiss()
                                onOpenProfileClicked?.invoke(
                                    adapterPosition,
                                    children[adapterPosition]
                                )
                            }, {
                                it?.dismiss()
                                onItemDeleteClick?.invoke(
                                    adapterPosition,
                                    parentPositions,
                                    children[adapterPosition]
                                )
                            })
                    }
                    else -> {
                        itemView.context.startActivity(
                            Intent(itemView.context, AddPlayerActivity::class.java)
                                .putExtra(
                                    "type_loc_player",
                                    children[adapterPosition].type_loc_player
                                )
                        )
                    }
                }
            }
        }


    }
}