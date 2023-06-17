package com.pagme.app.data.repository

import android.content.Context
import com.pagme.app.data.model.Contact
import com.pagme.app.data.model.Debt

interface DebtRepositoryInterface {

    suspend fun insertDebt(debt: Debt)

    suspend fun updateDebt(debt: Debt)
    suspend fun deleteDebt(debt: Debt)
    suspend fun selectDebtById(id: String): Debt?
    suspend fun selectAllDebts(): List<Debt>
}