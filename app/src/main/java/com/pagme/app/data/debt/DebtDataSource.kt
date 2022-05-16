package com.pagme.app.data.debt

import com.pagme.app.domain.model.Debt

interface DebtDataSource {
    suspend fun getDebt(): List<Debt>

    suspend fun createDebt(debt: Debt): Debt

    suspend fun updateDebt(debt: Debt): Debt

    suspend fun deleteDebt(idDebt: String):Boolean
}