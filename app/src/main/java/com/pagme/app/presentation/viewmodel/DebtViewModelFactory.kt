package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.domain.usecase.DebtUseCase
import javax.inject.Inject

class DebtViewModelFactory @Inject constructor(private val useCase: DebtUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtViewModel::class.java)) {
            return DebtViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}