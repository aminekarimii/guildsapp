package com.sqli.guildes.core

import androidx.annotation.Keep
import com.sqli.guildes.R

@Keep
object Constants {

    const val ENDPOINT = "http://192.168.1.107:8888/api/"
    const val USER = "userId"
    const val TOKEN = "token"
    val SUBMISSIONS_TYPES = mapOf(
            R.string.article to 50,
            R.string.atelier_workshop to 120,
            R.string.presentation to 80,
            R.string.breakfast_iscm to 150,
            R.string.contribution_project_OneISC to 200,
            R.string.english_participation to 0,
            R.string.english_session_organisation to 100,
            R.string.meetup_organisation to 300,
            R.string.creation_dt_asset to 300,
            R.string.participation_dt_asset to 200,
            R.string.participation_innersource to 250
    )
}