package com.shakiv.husain.instagramui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtils {

    private val isoDateFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    fun getCurrentUTCTime(): String {
        val time = Calendar.getInstance().time
        time.time -= 19800000
        return isoDateFormat.format(time)
    }


}