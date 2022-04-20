package com.pagme.app.business

import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.pagme.app.entity.Debt
import com.pagme.app.repository.DebtRepository
import java.text.SimpleDateFormat
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
    fun verifyIfCurrentDateIsGreaterThanCloseDate(closeDate:Int,dueDate:Int): String {
        val status:String
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd"))
        status = when {
            currentDate.toInt() < closeDate -> {
                "Aberto"
            }
            currentDate.toInt() > dueDate -> {
                "Atrasado"
            }
            else -> {
                "Fechado"
            }
        }

        return status
    }
}