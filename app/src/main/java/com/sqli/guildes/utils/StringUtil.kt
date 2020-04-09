package com.sqli.guildes.utils

import java.text.Normalizer

object StringUtil {
    val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

    fun CharSequence.formatName(): String {
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return REGEX_UNACCENT.replace(temp, "").replace(" ", "")
    }
}