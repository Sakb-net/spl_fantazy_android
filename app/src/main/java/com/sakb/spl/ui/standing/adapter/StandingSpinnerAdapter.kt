package com.sakb.spl.ui.standing.adapter

import android.widget.ArrayAdapter
import com.sakb.spl.data.model.SubeldwrysItem
import com.sakb.spl.ui.standing.StandingFragment

class StandingSpinnerAdapter(
    theContext: StandingFragment,
    val objects: MutableList<SubeldwrysItem?>,
    theLayoutResId: Int,
) :
    ArrayAdapter<Any>(
        theContext.requireContext(),
        theLayoutResId,
        (objects as List<SubeldwrysItem>?)!!
    ) {

    override fun getItem(position: Int): String {
        return objects[position]?.langNumWeek ?: ""
    }

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}