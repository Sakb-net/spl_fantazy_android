package com.sakb.spl.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sakb.spl.R
import kotlinx.android.synthetic.main.dialog_no_internet_connection.*

class NoInternetConnectionDialog(
    context: Context,
    private val callback: NoInternetDialogCallback?
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_no_internet_connection)
        btnRetry.setOnClickListener {
            callback?.retry()
            dismiss()
        }
        if (callback == null) btnRetry.visibility = View.INVISIBLE
        else btnRetry.visibility = View.VISIBLE
    }

    interface NoInternetDialogCallback {
        fun retry()
    }
}