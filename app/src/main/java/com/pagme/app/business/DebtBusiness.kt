package com.pagme.app.business

import android.os.Build
import androidx.annotation.RequiresApi
import com.pagme.app.domain.model.Debt
import com.pagme.app.data.card.CardRepository
import com.pagme.app.data.debt.DebtRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    fun enterOnePayment(value:Int,idDebt:String) {
        return debtRepository.insertPayment(value,idDebt)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun verifyIfCurrentDateIsGreaterThanCloseDate(idCard:String): String {

        val cardRepository = CardRepository().getDueDateCloseDate(idCard)

        val status:String
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd"))
        val closeDate = Integer.parseInt(cardRepository.closingDate.toString())
        val dueDate = Integer.parseInt(cardRepository.dueDate.toString())
        status = when {
            currentDate.toInt() < 20-> {
                "Aberto"
            }
            currentDate.toInt() > 15 -> {
                "Atrasado"
            }
            else -> {
                "Fechado"
            }
        }

        return status
    }


}