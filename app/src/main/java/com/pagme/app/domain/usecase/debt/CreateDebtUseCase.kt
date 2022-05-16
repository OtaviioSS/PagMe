package com.pagme.app.domain.usecase.debt

import com.pagme.app.domain.model.Debt

interface CreateDebtUseCase {
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