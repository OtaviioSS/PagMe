package com.pagme.app.ui.debt

import android.util.Log
import androidx.lifecycle.*
import com.pagme.app.data.debt.DebtRepository
import com.pagme.app.domain.model.Debt
import com.pagme.app.domain.usecase.debt.GetDebtUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
    private val repository: DebtRepository,
    private val getDebtUseCase: GetDebtUseCase
) : ViewModel() {
    val debtLiveData = MutableLiveData<MutableList<String?>>()

    private val _debtsData = MutableLiveData<List<Debt>>()
    val debtsData: LiveData<List<Debt>> = _debtsData
    fun getFilmeCorrotines() = viewModelScope.launch {
        try {
            val debts = getDebtUseCase()
            _debtsData.value = debts

        } catch (e: Exception) {
            Log.d("DebtViewModel", e.toString())

        }
    }


}