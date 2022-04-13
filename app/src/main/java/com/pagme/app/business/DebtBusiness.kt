package com.pagme.app.business

import android.widget.ProgressBar
import com.google.firebase.database.DataSnapshot
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository
import java.util.ArrayList

//CALSSE PARA IMPLEMENTAR REGRAS DE NEGOCIOS
class DebtBusiness() {
    private val debtRepository = DebtRepository()


    fun readCardsFromSpinner(): MutableList<String?> {
        return debtRepository.readCardsFromSpinner()
    }

    fun newDebt(debt: Debt) {
        debtRepository.createDebit(debt)
    }

    fun editDebt(debt: Debt): Boolean {
        if (debtRepository.updateDebit(debt)) {
            return true
        }
        return false

    }

    fun readOneDebt(debtID: String): Debt {
        return debtRepository.getOneDebt(debtID)
    }

    fun readDebts(): ArrayList<Debt> {
       return debtRepository.readAllDebts()
    }

    fun removeDebt(debtID: String) {
        return debtRepository.deleteDebt(debtID)
    }

}