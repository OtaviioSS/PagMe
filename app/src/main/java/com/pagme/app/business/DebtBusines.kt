package com.pagme.app.business

import com.google.firebase.database.DatabaseReference
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository

//CALSSE PARA IMPLEMENTAR REGRAS DE NEGOCIOS
class DebtBusines() {
    private val debtRepository = DebtRepository()


    fun readCardsFromSpinner(): MutableList<String?> {
        return debtRepository.readCardsFromSppiner()
    }


    fun newDebt(debt: Debt) {
        debtRepository.createDebit(debt)
    }

    fun editDebt(debt: Debt): Boolean {
        if (debtRepository.updateDebit(debt)){
            return true
        }
        return false

    }

    fun readOneDebt(debtID: String): Debt{
        return debtRepository.getOneDebt(debtID)
    }

    fun removeDebt(debtID: String){
        return debtRepository.deleteDebt(debtID)
    }

}