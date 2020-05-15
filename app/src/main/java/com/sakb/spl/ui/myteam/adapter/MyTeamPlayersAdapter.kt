package com.sakb.spl.ui.myteam.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sakb.spl.R
import com.sakb.spl.data.model.MyteamPlayersResponse
import kotlinx.android.synthetic.main.parent_item_myteam_recycler.view.*


class MyTeamPlayersAdapter(
    private var parents: List<List<MyteamPlayersResponse.Player>?>
) : RecyclerView.Adapter<MyTeamPlayersAdapter.ViewHolder>() {

    var onItemClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null
    var onChangeClick: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null
    var onOpenProfileClicked: ((pos: Int, MyteamPlayersResponse.Player) -> Unit)? = null
    var onResetClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? = null
    var onRestorePlayerClicked: ((pos: Int, parentPosition: Int, MyteamPlayersResponse.Player) -> Unit)? =
        null


    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_item_myteam_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (parents.size>4) 4 else parents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents[position]
        holder.recyclerView.apply {
            // layoutManager = LinearLayoutManager(holder.recyclerView
            // .context, LinearLayout.HORIZONTAL, false)
            parents[position]?.let {
                adapter = MyTeamPlayersChildItemAdapter(
                    it,
                    position,
                    onItemClicked,
                    onChangeClick,
                    onOpenProfileClicked,
                    onResetClicked,
                    onRestorePlayerClicked
                )
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
    }
}