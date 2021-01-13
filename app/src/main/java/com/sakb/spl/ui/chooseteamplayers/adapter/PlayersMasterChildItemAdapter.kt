package  com.sakb.spl.ui.chooseteamplayers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sakb.spl.R
import com.sakb.spl.constants.Constants
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.ui.addplayer.AddPlayerActivity
import com.sakb.spl.ui.addplayer.AddPlayerActivity.Companion.ACTIONTYPE
import com.sakb.spl.ui.addplayer.AddPlayerActivity.Companion.DELETEDPLAYER
import com.sakb.spl.ui.addplayer.AddPlayerActivity.Companion.ELDAWRYlINK
import com.sakb.spl.ui.addplayer.AddPlayerActivity.Companion.REPLACE
import com.sakb.spl.ui.addplayer.AddPlayerActivity.Companion.TYPELOCPLAYER
import com.sakb.spl.utils.showSplDeleteDialog
import com.sakb.spl.utils.showSplDialog

class PlayersMasterChildItemAdapter(
    // list of lineUp [1 - 4 - 4 - 2 - 4]
    private val children: List<PlayerMasterResponse.Data>,
    val parentPositions: Int = -1,
    var onItemDeleteClick: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? = null,
    var onOpenProfileClicked: ((pos: Int, PlayerMasterResponse.Data) -> Unit)? = null,
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? = null,
    ) : RecyclerView.Adapter<PlayersMasterChildItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item_team_master_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (children[position].found_player) {
            1 -> {
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
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.child_textView)
        val textViewCost: TextView = itemView.findViewById(R.id.child_cost_textView)
        val imageView: ImageView = itemView.findViewById(R.id.child_imageView)

        init {
            itemView.setOnClickListener {
                when (children[adapterPosition].found_player) {
                    1 -> {
                        if (children[adapterPosition].alPha == 0.5f) {
                            itemView.context.showSplDeleteDialog(
                                {
                                    it?.dismiss()
                                    onOpenProfileClicked?.invoke(
                                        adapterPosition,
                                        children[adapterPosition]
                                    )
                                },

                                {
                                    it?.dismiss()
                                    onRestorePlayerClicked?.invoke(
                                        adapterPosition,
                                        parentPositions,
                                        children[adapterPosition]
                                    )
                                },

                                {
                                    it?.dismiss()
                                    itemView.context.startActivity(
                                        Intent(itemView.context, AddPlayerActivity::class.java)
                                            .putExtra(ACTIONTYPE, REPLACE)
                                            .putExtra(
                                                DELETEDPLAYER,
                                                children[adapterPosition].link_player
                                            )
                                            .putExtra(
                                                ELDAWRYlINK,
                                                children[adapterPosition].eldwry_link
                                            )
                                            .putExtra(
                                                TYPELOCPLAYER,
                                                children[adapterPosition].type_loc_player
                                            )
                                    )
                                })
                        } else {
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