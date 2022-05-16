package com.pagme.app.domain.usecase.debt

import com.pagme.app.data.debt.DebtRepository
import com.pagme.app.domain.model.Debt
import javax.inject.Inject

class UpdateDebtUseCaseImplementation @Inject constructor(private val debtRepository: DebtRepository) {
    suspend operator fun invoke(debt: Debt): Debt
    {
        return debtRepository.updateDebt(debt)
    }
}