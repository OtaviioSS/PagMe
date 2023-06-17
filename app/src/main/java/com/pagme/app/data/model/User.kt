package com.pagme.app.data.model


data class User(
    var userId: String? = null,
    var email: String = "",
    var userName: String? = null,
    var password: String = "",
    var termsUse: Boolean? = false,
    var privacyPolicy:Boolean? = false



)

