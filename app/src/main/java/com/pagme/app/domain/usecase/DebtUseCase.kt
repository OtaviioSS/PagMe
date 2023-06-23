package com.pagme.app.domain.usecase

import android.content.Context
import android.provider.ContactsContract
import com.pagme.app.data.model.Contact
import com.pagme.app.data.model.Debt
import com.pagme.app.data.repository.DebtRepositoryInterface
import kotlinx.coroutines.flow.Flow

class DebtUseCase(private val debtRepositoryInterface: DebtRepositoryInterface) {

    suspend fun createDebt(debt: Debt, callback: (Boolean) -> Unit) {
        try {
            debtRepositoryInterface.insertDebt(debt)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    suspend fun updateDebt(debt: Debt, callback: (Boolean) -> Unit) {
        try {
            debtRepositoryInterface.updateDebt(debt)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    suspend fun pagouParcela(debt: Debt, callback: (Boolean) -> Unit) {
        try {
            debtRepositoryInterface.pagouParcela(debt)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }
    }

    suspend fun deleteDebt(debtID: String) {
        debtRepositoryInterface.deleteDebt(debtID)
    }

    suspend fun getDebtById(id: String): Flow<Debt?> {
        return debtRepositoryInterface.selectDebtById(id)
    }

    suspend fun getAllDebts(): List<Debt> {
        return debtRepositoryInterface.selectAllDebts()
    }


}