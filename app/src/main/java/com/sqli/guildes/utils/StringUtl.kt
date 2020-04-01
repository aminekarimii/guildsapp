package com.sqli.guildes.utils

import java.util.*

object StringUtl {
    fun formatGuild(guildName: String): String {
        return guildName.replace("é", "e")
                .replace("è", "e")
                .replace(" ", "")
                .toLowerCase(Locale.getDefault())
    }
}