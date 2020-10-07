package com.sakb.spl.utils

import android.app.AlarmManager
import android.app.LauncherActivity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object AppUtil {
    fun restartApplication(context: Context) {
        val mStartActivity = Intent(context, LauncherActivity::class.java)
        val mPendingIntentId = 123456
        val mPendingIntent = PendingIntent.getActivity(
            context, mPendingIntentId, mStartActivity,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
    }
}