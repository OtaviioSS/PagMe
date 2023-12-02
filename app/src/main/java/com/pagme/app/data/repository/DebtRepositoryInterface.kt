package com.pagme.app.data.repository

import android.content.Context
import com.pagme.app.data.model.Contact
import com.pagme.app.data.model.Debt
import com.pagme.app.data.model.Payment
import kotlinx.coroutines.flow.Flow

interface DebtRepositoryInterface {

    suspend fun insertDebt(debt: Debt,paymentsList:ArrayList<Payment>)

    suspend fun updateDebt(debt: Debt)
    suspend fun pagouParcela(debt: Debt)
    suspend fun deleteDebt(debtId:String)
    suspend fun selectDebtById(id: String): Flow<Debt?>
    suspend fun selectAllDebts(): List<Debt>
}