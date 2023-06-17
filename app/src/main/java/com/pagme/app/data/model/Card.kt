package com.pagme.app.data.model



data class Card(
    var idCard: String = "",
    var nameCard: String = "",
    var dueDate: Int = 0,
    val synchronize: Boolean = false,
    val disabled: Boolean = false,
    var userId: String? = null

)

