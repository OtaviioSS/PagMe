package com.pagme.app.data.model

import java.io.Serializable
import java.util.Date


data class Payment(
    var paymentID: String = "",
    var paymentDate: Date = Date(),
    var paymentDueDate: Date = Date(),
    var paymentValue: Double = 0.0,
    var paymentStatus: String ="",
    var debtID: String = "",
    var cardID: String = "",
    var userID: String = "",

): Serializable
