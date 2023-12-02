package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.domain.usecase.PaymentUseCase
import javax.inject.Inject

class PaymentViewModelFactory @Inject constructor(private val useCase: PaymentUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
