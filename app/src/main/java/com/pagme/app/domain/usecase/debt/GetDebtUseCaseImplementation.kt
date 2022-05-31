package com.pagme.app.domain.usecase.debt

import com.pagme.app.data.debt.DebtRepository
import com.pagme.app.domain.model.Debt
import javax.inject.Inject

class GetDebtUseCaseImplementation @Inject constructor(
    private val debtRepository: DebtRepository
    ) : GetDebtUseCase {
    override suspend fun invoke(): List<Debt> {
        return debtRepository.getDebt()
    }
}