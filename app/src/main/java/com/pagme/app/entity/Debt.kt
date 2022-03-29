package com.pagme.app.entity


data class Debt(
    var idDebt: String?=null,
    var nameCard: String?=null,
    var nameBuyer: String?=null,
    var valueBuy: Double=0.0,
    var installments: Int=0,
    var paidInstallments: Int=0,
    var whatsapp: String?=null,
    var valueInstallments: Double=0.0
)




