package com.sqli.guildes.utils

import android.content.Context
import com.sqli.guildes.R

object DateUtil {
    fun toReadableDateAndTime(context: Context,date: String): String {
        val onlyDate = date.substring(0, 10)
                .replace('-', '/')
        val time = date.substring(date.indexOf('T'), date.indexOf('T') + 6)
                .replace('T', ' ')
                .replace(':', 'h')
        return context.getString(R.string.date_template, onlyDate, time)
    }
}