package com.sakb.spl.ui.myteam.adapter.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.MyteamPlayersResponse
import com.sakb.spl.utils.DividerItemDecorationNoLast
import kotlinx.android.synthetic.main.parent_item_myteamplayers_recycler_menu.view.*


class MyTeamPlayersMenuAdapter(
    private var parents: List<List<MyteamPlayersResponse.Player>?>
) : RecyclerView.Adapter<MyTeamPlayersMenuAdapter.ViewHolder>() {

    var onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null
    var onChangeClick: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null
    var onOpenProfileClicked: ((pos: Int, MyteamPlayersResponse.Player) -> Unit)? = null
    var onResetClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null


    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_item_myteamplayers_recycler_menu, parent, false)
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
                    holder.header.visibility = View.VISIBLE
                    holder.type.text = it[0].type_loc_player
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

                        4 -> {
                            holder.header.setBackgroundColor(
                                ContextCompat.getColor(
                                    holder.itemView.context,
                                    R.color.colorYellowDark
                                )
                            )
                            holder.type.text = "substitutes"
                        }
                    }




                    holder.recyclerView.apply {
                        // layoutManager = LinearLayoutManager(holder.recyclerView
                        // .context, LinearLayout.HORIZONTAL, false)
                        setRecycledViewPool(viewPool)
                    }
                    adapter = MyTeamPlayersChildItemMenuAdapter(
                        it,
                        position,
                        onItemClicked,
                        onChangeClick,
                        onOpenProfileClicked,
                        onResetClicked,
                        onRestorePlayerClicked
                    )

                } else {
                    holder.header.visibility = View.GONE
                }
            }
            setRecycledViewPool(viewPool)
        }
    }

    fun updateData(players: List<List<MyteamPlayersResponse.Player>?>) {
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