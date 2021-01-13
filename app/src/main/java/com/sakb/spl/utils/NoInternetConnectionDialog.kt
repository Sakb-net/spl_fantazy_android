package com.sakb.spl.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sakb.spl.databinding.DialogNoInternetConnectionBinding

class NoInternetConnectionDialog(
    context: Context,
    private val callback: NoInternetDialogCallback?,
) : AlertDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DialogNoInternetConnectionBinding.inflate(LayoutInflater.from(context), null, false)

        setContentView(binding.root)
        binding.btnRetry.setOnClickListener {
            callback?.retry()
            dismiss()
        }
        if (callback == null) binding.btnRetry.visibility = View.INVISIBLE
        else binding.btnRetry.visibility = View.VISIBLE
    }

    interface NoInternetDialogCallback {
        fun retry()
    }
}