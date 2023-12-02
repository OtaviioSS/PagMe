package com.pagme.app.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.pagme.app.data.datasource.DebtDataSource
import com.pagme.app.data.model.Debt
import com.pagme.app.data.model.Payment
import kotlinx.coroutines.flow.Flow

class DebtRepositoryImplementation(private val dataSource: DebtDataSource) :
    DebtRepositoryInterface {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun insertDebt(debt: Debt,paymentsList:ArrayList<Payment>) {
        try {
            debt.userId = auth.currentUser!!.uid
            dataSource.createDebt(debt, paymentsList)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o debito: ${e.message}")
        }
    }

    override suspend fun updateDebt(debt: Debt) {
        try {
            Log.i("DebtRepo:", "DebtRepo")
            dataSource.alterDebt(debt)
        } catch (e: Exception) {
            Log.i("DebtDataSource:", e.message.toString())
            throw Exception("Falha ao atualizar o debito: ${e.message}")
        }
    }

    override suspend fun pagouParcela(debt: Debt) {
        try {
            dataSource.pagouParcela(debt)
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar o debito: ${e.message}")
        }    }

    override suspend fun deleteDebt(debtId: String) {
        try {
            dataSource.removeDebt(debtId)
        } catch (e: Exception) {
            Log.e("DeleteDebt", "Erro ao deletar a dívida: ${e.message}")
            throw Exception("Erro ao deletar a dívida: ${e.message}")
        }
    }


    override suspend fun selectDebtById(id: String): Flow<Debt?> {
        return dataSource.getDebtById(id)
    }

    override suspend fun selectAllDebts(): List<Debt> {
        return dataSource.getAllDebt()
    }


}