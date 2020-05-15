package com.sakb.spl.utils

import android.content.Context
import android.widget.ArrayAdapter

class SpinnerHelperAdapter(theContext: Context, objects: List<Any>, theLayoutResId: Int) :
    ArrayAdapter<Any>(theContext, theLayoutResId, objects) {

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}