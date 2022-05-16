package com.pagme.app.domain.usecase.debt

import com.pagme.app.data.debt.DebtRepository
import com.pagme.app.domain.model.Debt
import java.util.*
import javax.inject.Inject


class CreateDebtUseCaseImplementation @Inject constructor(
    private val debtRepository: DebtRepository
) : CreateDebtUseCase {

    override suspend fun invoke(
        nameCard: String,
        nameBuyer: String,
        valueBuy: Double,
        installments: Int,
        paidInstallments: Int,
        whatsapp: String,
        valueInstallments: Double,
        idCard: String
    ): Debt {
        return try {
            val debt = Debt(
                UUID.randomUUID().toString(),
                nameCard,
                nameBuyer,
                valueBuy,
                installments,
                paidInstallments,
                whatsapp,
                valueInstallments,
                idCard
            )
            debtRepository.createDebt(debt)
            debt
        } catch (e: Exception) {
            throw e
        }
    }
}