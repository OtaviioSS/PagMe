package com.pagme.app.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.google.firebase.auth.FirebaseAuth
import com.pagme.app.data.datasource.DebtDataSource
import com.pagme.app.data.model.Card
import com.pagme.app.data.model.Contact
import com.pagme.app.data.model.Debt

class DebtRepositoryImplementation(private val dataSource: DebtDataSource) :
    DebtRepositoryInterface {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun insertDebt(debt: Debt) {
        try {
            debt.userId = auth.currentUser!!.uid
            dataSource.createDebt(debt)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o debito: ${e.message}")
        }
    }

    override suspend fun updateDebt(debt: Debt) {
        try {
            dataSource.alterDebt(debt)
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar o debito: ${e.message}")
        }
    }

    override suspend fun deleteDebt(debt: Debt) {
        dataSource.removeDebt(debt)
    }

    override suspend fun selectDebtById(id: String): Debt? {
        return dataSource.getDebtById(id)
    }

    override suspend fun selectAllDebts(): List<Debt> {
        return dataSource.getAllDebt()
    }



}