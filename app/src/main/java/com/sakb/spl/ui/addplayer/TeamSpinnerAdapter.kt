package com.sakb.spl.ui.addplayer

import android.content.Context
import android.widget.ArrayAdapter
import com.sakb.spl.data.model.PlayerByTypeResponse

class TeamSpinnerAdapter(
    theContext: Context,
    private val objects: MutableList<PlayerByTypeResponse.Team?>?,
    theLayoutResId: Int,
) : ArrayAdapter<Any>(theContext, theLayoutResId, objects as List<PlayerByTypeResponse.Team?>) {

    override fun getItem(position: Int): String {
        return objects?.get(position)?.team ?: ""
    }

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}