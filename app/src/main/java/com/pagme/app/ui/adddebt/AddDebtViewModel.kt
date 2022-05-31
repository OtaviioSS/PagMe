package com.pagme.app.ui.adddebt

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.usecase.card.CreateCardUseCase
import com.pagme.app.domain.usecase.debt.CreateDebtUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDebtViewModel @Inject constructor(
    private val createDebtUseCase: CreateDebtUseCase,
    private val createCardUseCase: CreateCardUseCase

) :
    ViewModel() {


    private val _debtCreated = MutableLiveData<Debt>()
    val debtCreated: LiveData<Debt> = _debtCreated

    fun createDebt(
        nameCard: String,
        nameBuyer: String,
        valueBuy: Double,
        installments: Int,
        paidInstallments: Int,
        whatsapp: String,
        valueInstallments: Double,
        idCard: String
    ) = viewModelScope.launch {

        try {
            val deb = createDebtUseCase(
                nameCard,
                nameBuyer,
                valueBuy,
                installments,
                paidInstallments,
                whatsapp,
                valueInstallments,
                idCard
            )
            _debtCreated.value = deb
        } catch (e: Exception) {
            Log.d("CreateDebt", e.toString())
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