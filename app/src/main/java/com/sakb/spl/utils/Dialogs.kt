package com.sakb.spl.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.sakb.spl.R
import com.sakb.spl.databinding.DialogViewConfirmationBinding
import com.sakb.spl.databinding.DialogViewWarningBinding
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

fun Context.showWarningDialog(
    @DrawableRes drawableRes : Int,
    titleResID: Int,
    contentResID : Int,
    positive: (dialog: AlertDialog?) -> Unit,
    negative: (dialog: AlertDialog?) -> Unit
) {
    val binding = DialogViewWarningBinding.inflate(LayoutInflater.from(this), null,false)
    //   val view = LayoutInflater.from(this).inflate(R.layout.dialog_view_warning, null)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.dialogImage.setImageResource(drawableRes)
    binding.dialogTextTitle.text = this.getString(titleResID)
    binding.dialogTextContent.text = this.getString(contentResID)
    binding.positiveButton.setOnClickListener { positive(dialog) }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    binding.negativeBtn.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showConfirmationDialog(
    @DrawableRes drawableRes : Int,
    title: String,
    contentResID : Int?=null,
    positive: (dialog: AlertDialog?) -> Unit) {
    val binding = DialogViewConfirmationBinding.inflate(LayoutInflater.from(this), null,false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.dialogImage.setImageResource(drawableRes)
    binding.dialogTextTitle.text = title
    binding.dialogTextContent.text = contentResID?.let { this.getString(it) }
    binding.positiveButton.setOnClickListener { positive(dialog) }
    dialog.show()
}