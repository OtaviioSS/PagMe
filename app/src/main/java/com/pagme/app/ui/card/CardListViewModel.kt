package com.pagme.app.ui.card

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pagme.app.domain.model.Card
import com.pagme.app.domain.usecase.card.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(private val getCardsUseCase: GetCardsUseCase) : ViewModel() {
    private val _cardData = MutableLiveData<List<Card>>()
    val cardsData: LiveData<List<Card>> = _cardData



    fun getCards() = viewModelScope.launch {
        try {
            val Cards = getCardsUseCase()
            _cardData.value = Cards
        } catch (e: Exception) {
            Log.d("CardsViewModel", e.toString())
        }
    }
}