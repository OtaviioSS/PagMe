package com.pagme.app.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity
@Parcelize
data class Debt(
    @PrimaryKey
    var idDebt: Long = 0L,
    var nameCard: String,
    var nameBuyer: String,
    var valueBuy: Double = 0.0,
    var installments: Int = 0,
    var paidInstallments: Int = 0,
    var whatsapp: String? = null,
    var valueInstallments: Double = 0.0,
    var userId: String? = null
) : Parcelable






