package com.pagme.app.domain.usecase.debt

import com.pagme.app.data.debt.DebtRepository
import javax.inject.Inject

class DeleteDebtUseCaseImplementation @Inject constructor(private val debtRepository: DebtRepository){
    suspend operator fun invoke(idDebt: String):Boolean{
        return debtRepository.deleteDebt(idDebt)
    }

}