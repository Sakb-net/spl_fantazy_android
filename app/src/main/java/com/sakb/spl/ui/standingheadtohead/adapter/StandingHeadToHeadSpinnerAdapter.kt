package com.sakb.spl.ui.standingheadtohead.adapter

import android.widget.ArrayAdapter
import com.sakb.spl.data.model.DataItemSubGroup
import com.sakb.spl.ui.standingheadtohead.StandingHeadToHeadFragment

class StandingHeadToHeadSpinnerAdapter(
    theContext: StandingHeadToHeadFragment,
    val objects: MutableList<DataItemSubGroup?>,
    theLayoutResId: Int,
) :
    ArrayAdapter<Any>(
        theContext.requireContext(),
        theLayoutResId,
        (objects as List<DataItemSubGroup?>)
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