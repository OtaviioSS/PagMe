package com.pagme.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pagme.app.domain.usecase.CardUseCase
import javax.inject.Inject

class CardViewModelFactory @Inject constructor(private val useCase: CardUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            return CardViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}