package com.sakb.spl.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.sakb.spl.R
import kotlinx.android.synthetic.main.dialog_enter_name.view.*
import kotlinx.android.synthetic.main.dialog_enter_range_price.view.*
import kotlinx.android.synthetic.main.dialog_spl_delete_view.view.*
import kotlinx.android.synthetic.main.dialog_spl_view.view.*
import kotlinx.android.synthetic.main.dialog_view_log_out.view.*
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
    negative: (dialog: AlertDialog?) -> Unit
) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_spl_view, null)
    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    // view.dialogIcon.setImageResource(drawableRes)
    //  view.dialogText.text = resID
    view.playerDetailsBtn.setOnClickListener { positive(dialog) }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    view.deletePlayerBtn.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showSplDeleteDialog(
    playerProfileBtn: (dialog: AlertDialog?) -> Unit,
    restoreBtn: (dialog: AlertDialog?) -> Unit,
    replaceBtn: (dialog: AlertDialog?) -> Unit

) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_spl_delete_view, null)
    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(true)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    // view.dialogIcon.setImageResource(drawableRes)
    //  view.dialogText.text = resID
    view.playerProfieBtn.setOnClickListener { playerProfileBtn(dialog) }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    view.restorePlayerBtn.setOnClickListener { restoreBtn(dialog) }
    view.replacePlayerBtn.setOnClickListener { replaceBtn(dialog) }
    dialog.show()
}


fun Context.showEnterRangeDialog(positive: (dialog: AlertDialog?, priceFrom: String, priceTo: String) -> Unit) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_enter_range_price, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    Util.changeViewTypeFace(this,
//        Constants.FONT_REGULAR,
//        view.positiveBtn)
    //  view.dialogIcon.setImageResource(drawableRes)
    // view.dialogText.text = resID
    // view.total_price.text = total
    view.positiveEnterRangeBtn.setOnClickListener {
        positive(
            dialog, view.enter_price_from.text.toString(),
            view.enter_price_to.text.toString()

        )
    }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}

fun Context.showDialogLogOut(
    resID: String,
    positive: (dialog: AlertDialog?) -> Unit,
    negative: (dialog: AlertDialog?) -> Unit
) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_view_log_out, null)
    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    // view.dialogIcon.setImageResource(drawableRes)
    view.dialogTextTitle.text = resID
    view.positiveLogoutBtn.setOnClickListener { positive(dialog) }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    view.negativeButton.setOnClickListener { negative(dialog) }
    dialog.show()
}

fun Context.showEnterTeamNameDialog(positive: (dialog: AlertDialog?, name: String) -> Unit) {
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_enter_name, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    Util.changeViewTypeFace(this,
//        Constants.FONT_REGULAR,
//        view.positiveBtn)
    //  view.dialogIcon.setImageResource(drawableRes)
    // view.dialogText.text = resID
    // view.total_price.text = total
    view.positiveEnterBtn.setOnClickListener { positive(dialog, view.enter_et.text.toString()) }
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}

fun Bitmap.toStringBase(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}