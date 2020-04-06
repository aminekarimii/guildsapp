package com.sqli.guildes.core

import androidx.annotation.Keep

@Keep
object Constants {

    const val ENDPOINT = "http://192.168.1.107:8888/api/"
    const val USER = "userId"
    const val TOKEN = "token"
    val SUBMISSIONS_TYPES = mapOf(
            "Article" to 50,
            "Atelier/Workshop" to 120,
            "Présentation" to 80,
            "Petit déjeuner ISCM" to 150,
            "Contribution au projet de Transformation OneISC" to 200,
            "Participation Session Anglais" to 0,
            "Organiser Session Anglais" to 100,
            "Organiser un Meetup" to 300,
            "Création Asset DT" to 300,
            "Participation Asset DT" to 200,
            "Participation Innersource" to 250
    )
}