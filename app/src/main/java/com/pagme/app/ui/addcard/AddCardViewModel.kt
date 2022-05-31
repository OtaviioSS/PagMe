package com.pagme.app.ui.addcard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.model.Card
import com.pagme.app.domain.usecase.card.CreateCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(private val createCardUseCase: CreateCardUseCase) : ViewModel() {



    private val _cardCreated = MutableLiveData<Card>()
    val cardCreated: LiveData<Card> = _cardCreated

    fun createCard(cardName: String, closingDate: String, dueDate: String) = viewModelScope.launch {
        try {
            val card = createCardUseCase(cardName, closingDate, dueDate)

        } catch (e: Exception) {
            Log.d("Error: ", e.toString())

        }
    }
}