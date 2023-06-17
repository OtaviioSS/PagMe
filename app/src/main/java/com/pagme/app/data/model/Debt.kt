package com.pagme.app.data.model


data class Debt(
    var idDebt: String = "",
    var nameCard: String = "",
    var nameBuyer: String = "",
    var valueBuy: Double = 0.0,
    var installments: Int = 0,
    var paidInstallments: Int = 0,
    var whatsapp: String? = null,
    val disabled: Boolean = false,
    var valueInstallments: Double = 0.0,
    var userId: String = ""
)
