package com.pagme.app.business

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.pagme.app.entity.Card
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository

//CALSSE PARA IMPLEMENTAR REGRAS DE NEGOCIOS
class DebtBussines(private val database: DatabaseReference) {
    private val debtRepository = DebtRepository(database)


    fun readCardsFromSppiner(): MutableList<String?> {
       return debtRepository.readCardsFromSppiner()
  }


    fun newDebit(debt: Debt) {
        debtRepository.createDebit(debt,database)
    }

}