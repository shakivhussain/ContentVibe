package com.shakiv.husain.instagramui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    private val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    fun getCurrentUTCTime(): String {
        val time = Calendar.getInstance().time
        time.time -= 19800000
        return isoDateFormat.format(time)
    }

    fun toDuration(utc: String): String {
        val milliseconds = toMilliSeconds(utcDate = utc, sdf = isoDateFormat)

        val millis = System.currentTimeMillis() - milliseconds
        val seconds = (millis / 1000).toInt()
        val minutes = (seconds / 60)
        val hours = (minutes / 60)
        val days = (hours / 24)
        val months = (days / 30)
        val years = (months / 12)
        return getShortestDate(seconds, minutes, hours, days, months, years)
    }

    private fun toMilliSeconds(utcDate: String, sdf: SimpleDateFormat): Long {
        return try {
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val localDate = sdf.parse(utcDate) ?: 0
            sdf.timeZone = TimeZone.getDefault()
            val dateFormateInUTC = sdf.format(localDate)
            sdf.parse(dateFormateInUTC)?.time ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private fun getShortestDate(
        seconds: Int,
        min: Int,
        hours: Int,
        days: Int,
        months: Int,
        years: Int
    ): String {
        return when {
            min < 1 -> "${seconds}s"
            min >= 1 && hours < 1 -> "${min}m"
            hours >= 1 && days < 1 -> "${hours}h"
            days >= 1 && months < 1 -> "${days}d"
            months >= 1 && years < 1 -> "${months}M"
            else -> "${years}Y"
        }
    }


}