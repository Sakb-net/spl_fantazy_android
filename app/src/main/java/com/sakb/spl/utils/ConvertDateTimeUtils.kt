package com.sakb.spl.utils

import com.sakb.spl.utils.LanguageUtil.getLanguage
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConvertDateTimeUtils {
    var OLD_POA_FORMAT_OLD = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    const val TIME_FORMAT = "hh:mm aa"
    const val TIME_24_FORMAT = "HH:mm:ss"

    const val DATE_DEFAULT_FORMAT = "yyyy-MM-dd"
    const val DATE_CHAR_FORMAT_EN = "MMM dd"
    const val DATE_CHAR_FORMAT_AR = "dd MMM"
    var SECOND_MILLIS: Int = 1000;
    var MINUTE_MILLIS: Int = 60 * SECOND_MILLIS;
    var HOUR_MILLIS: Int = 60 * MINUTE_MILLIS;
    var DAY_MILLIS: Int = 24 * HOUR_MILLIS;

    fun getTimeAgo(time: Long): String? {
        var timeInside = time
        if (timeInside < 1000000000000L) {
            timeInside *= 1000
        }

        val now = System.currentTimeMillis()
        if (timeInside > now || timeInside <= 0) {
            return null
        }


        val diff = now - timeInside;
        return when {
            diff < MINUTE_MILLIS -> {
                "just now";
            }
            diff < 2 * MINUTE_MILLIS -> {
                "a minute ago";
            }
            diff < 50 * MINUTE_MILLIS -> {
                "${diff / MINUTE_MILLIS} minutes ago"
            }
            diff < 90 * MINUTE_MILLIS -> {
                "an hour ago";
            }
            diff < 24 * HOUR_MILLIS -> {
                "${diff / HOUR_MILLIS} hours ago"
            }
            diff < 48 * HOUR_MILLIS -> {
                "yesterday";
            }
            else -> {
                "${diff / DAY_MILLIS} days ago"
            }
        }
    }

    fun convertTimeMillis(time: String?, format: String): Long {
        if (time == null || time.isEmpty()) return 0
        var timeInMilliseconds = 0L
        // time format "4:56 am" or "8:01 pm"
        val sdf = SimpleDateFormat(format)
        try {
            val mDate = sdf.parse(time)
            timeInMilliseconds = mDate.time
            println("Date in milli :: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    fun getCurrentTimeInMillis(): Long {
        return System.currentTimeMillis()
    }

    fun getTimeAsDate(time: String?, oldFormat: String?, newFormat: String?): String? {
        var time = time
        if (time == null || time.isEmpty()) return null
        val date: Date
        try {
            val oldDateFormat = SimpleDateFormat(oldFormat, Locale.ENGLISH)
            val newDateFormat = SimpleDateFormat(newFormat, Locale(LanguageUtil.getLanguage()))
            date = oldDateFormat.parse(time)
            time = newDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    fun getTimeAsDateEnglish(time: String?, oldFormat: String?, newFormat: String?): String? {
        var time = time
        if (time == null || time.isEmpty()) return null
        val date: Date
        try {
            val oldDateFormat = SimpleDateFormat(oldFormat, Locale.ENGLISH)
            val newDateFormat = SimpleDateFormat(newFormat, Locale.ENGLISH)
            date = oldDateFormat.parse(time)
            time = newDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(OLD_POA_FORMAT_OLD)
        return format.format(date)
    }

    fun changeFormat(
        time: String?,
        oldFormat: String?,
        newFormat: String?
    ): String? {
        var time = time
        if (time == null || time.isEmpty()) return null
        val date: Date
        try {
            val oldDateFormat =
                SimpleDateFormat(oldFormat, Locale.ENGLISH)
            val newDateFormat =
                SimpleDateFormat(newFormat, Locale(getLanguage()))
            date = oldDateFormat.parse(time)
            time = newDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return time
    }

}