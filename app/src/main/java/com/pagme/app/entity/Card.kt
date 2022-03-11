package com.pagme.app.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Card(
    val cardID: String? = null,
    val cardName: String? = null,
    val closingDate: String? = null,
    val dueDate: String? = null
) {


}
