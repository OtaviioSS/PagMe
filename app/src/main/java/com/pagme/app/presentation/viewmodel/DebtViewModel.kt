package com.pagme.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.model.Debt
import com.pagme.app.domain.usecase.DebtUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DebtViewModel(private val debtUseCase: DebtUseCase) : ViewModel() {
    private val _debts = MutableLiveData<List<Debt>>()
    val debts: LiveData<List<Debt>> get() = _debts

    private var originalDebtsList: List<Debt> = emptyList()
    private var filteredDebtsList: List<Debt> = emptyList()
    fun filterDebts(query: String) {
        filteredDebtsList = originalDebtsList.filter { debt ->
            debt.nameBuyer.contains(query, ignoreCase = true)
        }
        _debts.value = filteredDebtsList
    }

    fun createDebt(debt: Debt, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            debtUseCase.createDebt(debt) { success ->
                callback(success)
            }
        }
    }

    fun update(debt: Debt, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            debtUseCase.updateDebt(debt) { success ->
                callback(success)
            }
        }
    }

    fun delete(debtID: String) {
        viewModelScope.launch {
            debtUseCase.deleteDebt(debtID)
        }
    }

    fun getDebtById(id: String): LiveData<Flow<Debt?>> {
        val debtLiveData = MutableLiveData<Flow<Debt?>>()
        viewModelScope.launch {
            val debt = debtUseCase.getDebtById(id)
            debtLiveData.postValue(debt)
        }
        return debtLiveData
    }

    fun getAllDebts() {
        viewModelScope.launch {
            originalDebtsList = debtUseCase.getAllDebts()
            filteredDebtsList = originalDebtsList
            _debts.value = filteredDebtsList
        }
    }

    fun pagouParcela(debt: Debt, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            debtUseCase.pagouParcela(debt) { success ->
                callback(success)
            }
        }
    }

}
