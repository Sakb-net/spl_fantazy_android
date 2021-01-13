package com.sakb.spl.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import com.sakb.spl.R
import com.sakb.spl.data.model.DataCreateLeague
import com.sakb.spl.databinding.DialogViewBinding
import com.sakb.spl.databinding.DialogViewConfirmationBinding
import com.sakb.spl.databinding.DialogViewSuccCreateLeagueBinding
import com.sakb.spl.databinding.DialogViewWarningBinding
import dmax.dialog.SpotsDialog


object Dialogs {
    fun getProgressDialog(
        context: Context,
        titleId: Int = R.string.loading_custom_title,
        isCancelable: Boolean = false,
    ): AlertDialog? {
        return SpotsDialog.Builder().setContext(context).setMessage(titleId)
            .setCancelable(isCancelable).build()
    }


    fun getSuccessDialog(
        context: Context,
        @DrawableRes drawableRes: Int = R.drawable.dialog_info,
        message: String?,
        isCancelable: Boolean = true,
    ): AlertDialog? {
        val binding = DialogViewBinding.inflate(LayoutInflater.from(context), null, false)
        val aDialog = AlertDialog.Builder(context).setView(binding.root).setCancelable(isCancelable)
        val dialog = aDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.dialogIcon.setImageResource(drawableRes)
        binding.dialogText.text = message
        binding.positiveBtn.setOnClickListener { dialog?.dismiss() }
        // dialog.show()
        return dialog
    }


    fun getErrorDialog(
        context: Context,
        @DrawableRes drawableRes: Int = R.drawable.dialog_info,
        message: String?,
        isCancelable: Boolean = true,
    ): AlertDialog? {
        val binding = DialogViewBinding.inflate(LayoutInflater.from(context), null, false)
        val aDialog = AlertDialog.Builder(context).setView(binding.root).setCancelable(isCancelable)
        val dialog = aDialog.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.dialogIcon.setImageResource(drawableRes)
        binding.dialogText.text = message
        binding.positiveBtn.setOnClickListener { dialog?.dismiss() }
        // dialog.show()
        return dialog
    }

}

fun Context.showWarningDialog(
    @DrawableRes drawableRes: Int,
    titleResID: Int,
    contentResID: Int,
    positive: (dialog: AlertDialog?) -> Unit,
    negative: (dialog: AlertDialog?) -> Unit,
) {
    val binding = DialogViewWarningBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.dialogImage.setImageResource(drawableRes)
    binding.dialogTextTitle.text = this.getString(titleResID)
    binding.dialogTextContent.text = this.getString(contentResID)
    binding.positiveButton.setOnClickListener { positive(dialog) }
    binding.negativeBtn.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showConfirmationDialog(
    @DrawableRes drawableRes: Int,
    title: String,
    contentResID: Int? = null,
    positive: (dialog: AlertDialog?) -> Unit,
) {
    val binding = DialogViewConfirmationBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.dialogImage.setImageResource(drawableRes)
    binding.dialogTextTitle.text = title
    binding.dialogTextContent.text = contentResID?.let { this.getString(it) }
    binding.positiveButton.setOnClickListener { positive(dialog) }
    dialog.show()
}

fun Context.showSuccessDialog(
    data: DataCreateLeague,
    copy: (dialog: AlertDialog?, code: String?) -> Unit,
    share: (dialog: AlertDialog?, url: String?) -> Unit,
    manage: (dialog: AlertDialog?, url: String?) -> Unit,
    myLeague: (dialog: AlertDialog?) -> Unit,
) {
    val binding = DialogViewSuccCreateLeagueBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    val code = data.groupEldwry?.code
    val url = data.groupEldwry?.link
    binding.codeText.text = code
    binding.copyBtn.setOnClickListener { copy(dialog, code) }
    binding.shareBtn.setOnClickListener { share(dialog, url) }
    binding.mangeDawery.setOnClickListener { manage(dialog, url) }
    binding.dawryaty.setOnClickListener { myLeague(dialog) }
    dialog.show()
}