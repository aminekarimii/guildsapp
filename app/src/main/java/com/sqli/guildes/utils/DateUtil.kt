package com.sqli.guildes.utils

import log

object DateUtil {
    fun toReadableDateAndTime(date: String): String {
        log(date)
        val onlyDate = date.substring(0, 10)
                .replace('-', '/')
        val time = date.substring(date.indexOf('T'), date.indexOf('T') + 6)
                .replace('T', ' ')
                .replace(':', 'h')
        return "le $onlyDate à$time"
    }
}