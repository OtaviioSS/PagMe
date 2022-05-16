package com.pagme.app.ui.card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.usecase.card.CreateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelNewCard @Inject constructor(private val createCardUseCase: CreateCardUseCase) : ViewModel() {

    fun createCard(cardName: String, closingDate: String, dueDate: String) = viewModelScope.launch {
        try {
            val card = createCardUseCase(cardName, closingDate, dueDate)

        } catch (e: Exception) {
            Log.d("Error: ", e.toString())

        }
    }
}