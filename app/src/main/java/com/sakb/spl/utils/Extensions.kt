package com.sakb.spl.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.sakb.spl.databinding.*
import java.io.ByteArrayOutputStream

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.centerToast(text: String) {
    val toast = Toast.makeText(this, text, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun Context.showSplDialog(
    positive: (dialog: AlertDialog?) -> Unit,
    negative: (dialog: AlertDialog?) -> Unit,
) {
    val binding = DialogSplViewBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(true)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.playerDetailsBtn.setOnClickListener { positive(dialog) }
    binding.deletePlayerBtn.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showSplDeleteDialog(
    playerProfileBtn: (dialog: AlertDialog?) -> Unit,
    restoreBtn: (dialog: AlertDialog?) -> Unit,
    replaceBtn: (dialog: AlertDialog?) -> Unit,

    ) {
    val binding = DialogSplDeleteViewBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(true)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.playerProfieBtn.setOnClickListener { playerProfileBtn(dialog) }
    binding.restorePlayerBtn.setOnClickListener { restoreBtn(dialog) }
    binding.replacePlayerBtn.setOnClickListener { replaceBtn(dialog) }
    dialog.show()
}

fun Context.showLeaguesSettingsDialog(
    link: String,
    admin: Boolean,
    standingBtn: (dialog: AlertDialog?, link: String) -> Unit,
    managementBtn: (dialog: AlertDialog?, link: String) -> Unit,
    leaveBtn: (dialog: AlertDialog?, link: String) -> Unit,
) {
    val binding = DialogSplLeagueViewBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(true)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.standingBtn.setOnClickListener { standingBtn(dialog, link) }
    binding.managementBtn.setOnClickListener { managementBtn(dialog, link) }
    binding.leaveBtn.setOnClickListener { leaveBtn(dialog, link) }
    if (admin) {
        binding.managementBtn.visibility = View.VISIBLE
        binding.leaveBtn.visibility = View.GONE
    } else {
        binding.managementBtn.visibility = View.GONE
        binding.leaveBtn.visibility = View.VISIBLE
    }
    dialog.show()
}

fun Context.showEnterRangeDialog(positive: (dialog: AlertDialog?, priceFrom: String, priceTo: String) -> Unit) {
    val binding = DialogEnterRangePriceBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.positiveEnterRangeBtn.setOnClickListener {
        positive(
            dialog, binding.enterPriceFrom.text.toString(),
            binding.enterPriceTo.text.toString()

        )
    }
    dialog.show()
}

fun Context.showDialogLogOut(
    resID: String,
    positive: (dialog: AlertDialog?) -> Unit,
    negative: (dialog: AlertDialog?) -> Unit,
) {
    val binding = DialogViewLogOutBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.dialogTextTitle.text = resID
    binding.positiveLogoutBtn.setOnClickListener { positive(dialog) }
    binding.negativeButton.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showEnterTeamNameDialog(positive: (dialog: AlertDialog?, name: String) -> Unit) {
    val binding = DialogEnterNameBinding.inflate(LayoutInflater.from(this), null, false)
    val alertDialog = AlertDialog.Builder(this).setView(binding.root)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.positiveEnterBtn.setOnClickListener {
        positive(dialog,
            binding.enterEt.text.toString())
    }
    dialog.show()
}

fun Bitmap.toStringBase(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}