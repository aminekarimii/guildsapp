package com.sqli.guildes.data.remote.requests

data class LoginRequest @JvmOverloads constructor (var user: LoginUser = LoginUser()) {
    data class LoginUser @JvmOverloads constructor (
            var username: String = "akherbouch",
            var password: String = "123456789"
    )
}