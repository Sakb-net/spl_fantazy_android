package com.sakb.spl.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import de.mateware.snacky.Snacky


object Snacky {

    fun getSuccessSnacky(
        activity: Activity,
        message: String
    ): Snackbar? {
        return Snacky.builder()
            .setActivity(activity)
            .setText(message)
            .setDuration(Snacky.LENGTH_SHORT)
            .success()
    }

    fun getErrorSnacky(
        activity: Activity,
        message: String
    ): Snackbar? {
        return Snacky.builder()
            .setActivity(activity)
            .setText(message)
            .setDuration(Snacky.LENGTH_SHORT)
            .error()
    }
}