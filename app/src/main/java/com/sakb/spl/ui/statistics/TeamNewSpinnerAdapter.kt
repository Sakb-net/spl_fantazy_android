package com.sakb.spl.ui.statistics

import android.widget.ArrayAdapter
import com.sakb.spl.data.model.GetTeamResponse

class TeamNewSpinnerAdapter(
    theContext: StatisticsFragment,
    val objects: MutableList<GetTeamResponse.Data?>,
    theLayoutResId: Int
) :
    ArrayAdapter<Any>(theContext?.requireContext(), theLayoutResId, objects as List<GetTeamResponse.Data?>) {

    override fun getItem(position: Int): String {
        return objects[position]?.name?:""
    }

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}