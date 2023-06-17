package com.pagme.app.data.model

import java.util.*

data class Contact(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var phone: String
) {
    fun r(): String {
        return name
    }
}

