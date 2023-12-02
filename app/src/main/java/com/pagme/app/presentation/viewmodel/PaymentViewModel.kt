package com.pagme.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.model.Payment
import com.pagme.app.domain.usecase.PaymentUseCase
import kotlinx.coroutines.launch

class PaymentViewModel(private val useCase: PaymentUseCase) : ViewModel() {

    private val _payments = MutableLiveData<MutableList<Payment>>()
    val payments: LiveData<MutableList<Payment>> get() = _payments

    fun create(payment: Payment, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            useCase.create(payment) { success ->
                callback(success)
            }
        }
    }

    fun update(payment: Payment) {
        viewModelScope.launch {
            useCase.update(payment)
        }
    }


    fun paidInstallment(payment: Payment,status: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            useCase.paidInstallment(payment,status) { success ->
                callback(success)
            }
        }
    }

    fun delete(payment: Payment) {
        viewModelScope.launch {
            useCase.delete(payment)
        }
    }


    fun getAllPayment(debtID: String) {
        viewModelScope.launch {
            _payments.value = useCase.getAll(debtID)
            Log.i("CardViewModel:", _payments.value.toString())
        }
    }

}