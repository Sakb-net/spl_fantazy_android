package com.sakb.spl.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.sakb.spl.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.dialog_view.view.*


object Dialogs {
    fun getProgressDialog(
        context: Context,
        titleId : Int  = R.string.loading_custom_title,
        isCancelable: Boolean = false
    ): AlertDialog? {
        return SpotsDialog.Builder().setContext(context).setMessage(titleId).setCancelable(isCancelable).build()
    }

    fun getSuccessDialog(
         context: Context,
         @DrawableRes drawableRes: Int = R.drawable.dialog_info,
         message: String?,
         isCancelable: Boolean = true
    ): AlertDialog? {
         val view = LayoutInflater.from(context).inflate(R.layout.dialog_view, null)
    val aDialog = AlertDialog.Builder(context).setView(view).setCancelable(isCancelable)
         val dialog = aDialog.create()
         dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
         view.dialogIcon.setImageResource(drawableRes)
         view.dialogText.text = message
         view.positiveBtn.setOnClickListener{dialog?.dismiss()}
        // dialog.show()
    return dialog
    }


    fun getErrorDialog(
        context: Context,
        @DrawableRes drawableRes: Int = R.drawable.dialog_info,
        message: String?,
        isCancelable: Boolean = true
    ): AlertDialog? {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_view, null)
        val aDialog = AlertDialog.Builder(context).setView(view).setCancelable(isCancelable)
        val dialog = aDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.dialogIcon.setImageResource(drawableRes)
        view.dialogText.text = message
        view.positiveBtn.setOnClickListener{dialog?.dismiss()}
       // dialog.show()
        return dialog
    }

}