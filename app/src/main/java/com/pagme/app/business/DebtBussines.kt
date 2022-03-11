package com.pagme.app.business

import android.content.Context
import android.widget.Spinner
import com.google.firebase.database.DatabaseReference
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository

//CALSSE PARA IMPLEMENTAR REGRAS DE NEGOCIOS
class DebtBussines(private val database: DatabaseReference) {
    val debtRepository = DebtRepository(database)


    fun readCardsFromSppiner(context: Context, spinner: Spinner) {
        debtRepository.readCardsFromSppiner(context,spinner)
    }
    fun newDebit(debt: Debt) {
        debtRepository.newDebit(debt,database)
    }

}