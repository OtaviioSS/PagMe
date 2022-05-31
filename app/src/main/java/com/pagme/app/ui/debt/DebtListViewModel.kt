package com.pagme.app.ui.debt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.usecase.debt.GetDebtUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtListViewModel @Inject constructor(private val getDebtUseCase: GetDebtUseCase) : ViewModel() {
    private val _debtData = MutableLiveData<List<Debt>>()
    val debtsData: LiveData<List<Debt>> = _debtData



    fun getDebts() = viewModelScope.launch {
        try {
            val Debts = getDebtUseCase()
            _debtData.value = Debts
        } catch (e: Exception) {
            Log.d("DebtsViewModel", e.toString())
        }
    }
}