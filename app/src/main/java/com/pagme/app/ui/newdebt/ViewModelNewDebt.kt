package com.pagme.app.ui.newdebt

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.usecase.card.CreateCardUseCase
import com.pagme.app.domain.usecase.debt.CreateDebtUseCase
import kotlinx.coroutines.launch

class ViewModelNewDebt(
    private val createDebtUseCase: CreateDebtUseCase, private val createCardUseCase: CreateCardUseCase
) : ViewModel() {
    private var isFormValid = false


    fun createDebt(
        nameCard: String,
        nameBuyer: String,
        valueBuy: Double,
        installments: Int,
        paidInstallments: Int,
        whatsapp: String,
        valueInstallments: Double,
        idCard: String
    ) =
        viewModelScope.launch {
            try {
                val debt = createDebtUseCase(
                    nameCard,
                    nameBuyer,
                    valueBuy,
                    installments,
                    paidInstallments,
                    whatsapp,
                    valueInstallments,
                    idCard
                )

            } catch (e: Exception) {
                Log.d("Error: ", e.toString())
            }


        }

    fun createCard(cardName: String, closingDate: String, dueDate: String) = viewModelScope.launch {
        try {
            val card = createCardUseCase(cardName, closingDate, dueDate)

        } catch (e: Exception) {
            Log.d("Error: ", e.toString())

        }
    }
}