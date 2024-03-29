package com.sakb.spl.ui.transfers.adapter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.PlayerMasterResponse
import com.sakb.spl.utils.DividerItemDecorationNoLast
import kotlinx.android.synthetic.main.transfers_parent_item_team_master_recycler_menu.view.*


class MyTeamPlayersMasterMenuAdapter(
    private var parents: List<List<PlayerMasterResponse.Data>?>
) : RecyclerView.Adapter<MyTeamPlayersMasterMenuAdapter.ViewHolder>() {

    var onItemDeleteClick: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? =
        null
    var onOpenProfileClicked: ((pos: Int, PlayerMasterResponse.Data) -> Unit)? = null
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, PlayerMasterResponse.Data) -> Unit)? =
        null


    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.transfers_parent_item_team_master_recycler_menu, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents[position]

        holder.recyclerView.apply {
            // layoutManager = LinearLayoutManager(holder.recyclerView
            // .context, LinearLayout.HORIZONTAL, false)
            parents[position]?.let {
                if (it.size > 0) {
                    when (position) {
                        0 -> holder.header.setBackgroundColor(
                                ContextCompat.getColor(
                                    holder.itemView.context,
                                    R.color.colorSky
                                )
                            )

                        1 -> holder.header.setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.colorOrange
                            )
                        )

                        2 -> holder.header.setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.colorRed
                            )
                        )
                        3 -> holder.header.setBackgroundColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.colorLemon
                            )
                        )
                    }

                    holder.header.visibility = View.VISIBLE
                    holder.type.text = it[0].type_loc_player
                    adapter = PlayersMasterChildItemMenuAdapter(
                        it,
                        position,
                        onItemDeleteClick,
                        onOpenProfileClicked,
                        onRestorePlayerClicked
                    )
                } else {
                    holder.header.visibility = View.GONE
                }
            }
            setRecycledViewPool(viewPool)
        }
    }

    fun updateData(players: List<List<PlayerMasterResponse.Data>?>) {
        this.parents = players
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.rv_child
        val type: TextView = itemView.nameTv
        val header: LinearLayout = itemView.header
        init {
            recyclerView.addItemDecoration(
                DividerItemDecorationNoLast(
                    itemView.context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }
}