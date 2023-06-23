package com.pagme.app.domain.usecase

import com.pagme.app.data.model.Card
import com.pagme.app.data.repository.CardRepositoryInterface

class CardUseCase(private val cardRepositoryInterface: CardRepositoryInterface) {

    suspend fun createCard(card: Card,callback: (Boolean) -> Unit) {
        try {
            cardRepositoryInterface.insert(card)
            callback(true)
        }catch (e:Exception){
            callback(false)
        }

    }

    suspend fun updateCard(card: Card) {
        cardRepositoryInterface.update(card)
    }

    suspend fun deleteCard(card: Card) {
        cardRepositoryInterface.delete(card)
    }

    suspend fun getCardById(id: String): Card? {
        return cardRepositoryInterface.selectById(id)
    }

    suspend fun getAllCards(): MutableList<Card> {
        return cardRepositoryInterface.selectAll()
    }
}