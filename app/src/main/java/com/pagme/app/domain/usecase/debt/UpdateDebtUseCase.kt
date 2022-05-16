package com.pagme.app.domain.usecase.debt

import android.net.Uri
import com.pagme.app.domain.model.Debt

interface UpdateDebtUseCase  {
    suspend operator fun invoke(
        nameCard: String,
        nameBuyer: String,
        valueBuy: Double,
        installments: Int,
        paidInstallments: Int,
        whatsapp: String,
        valueInstallments: Double,
        idCard: String
    ): Debt
}