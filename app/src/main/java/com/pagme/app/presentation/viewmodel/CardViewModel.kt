package com.pagme.app.presentation.viewmodel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.data.model.Card
import com.pagme.app.domain.usecase.CardUseCase
import io.grpc.Context
import kotlinx.coroutines.launch

class CardViewModel(private val useCase: CardUseCase) : ViewModel() {
    private val _cards = MutableLiveData<MutableList<Card>>()
    val cards: LiveData<MutableList<Card>> = _cards

    fun createCard(card: Card, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            useCase.createCard(card) { success ->
                callback(success)

            }
        }
    }

    fun update(card: Card) {
        viewModelScope.launch {
            useCase.updateCard(card)
        }
    }

    fun delete(card: Card) {
        viewModelScope.launch {
            useCase.deleteCard(card)
        }
    }

    fun getCardById(id: String): LiveData<Card?> {
        val cardLiveData = MutableLiveData<Card?>()
        viewModelScope.launch {
            val card = useCase.getCardById(id)
            cardLiveData.postValue(card)
        }
        return cardLiveData
    }

    fun getAllCards() {
        viewModelScope.launch {
            _cards.value = useCase.getAllCards()
            Log.i("CardViewModel:", _cards.value.toString())
        }
    }

}