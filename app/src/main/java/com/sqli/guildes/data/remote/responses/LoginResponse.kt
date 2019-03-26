package com.sqli.guildes.data.remote.responses

data class LoginResponse @JvmOverloads constructor (
        var _id: String = "",
        var username: String = "",
        var token: String = ""
)