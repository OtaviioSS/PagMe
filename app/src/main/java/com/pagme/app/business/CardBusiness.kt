package com.pagme.app.business

import com.pagme.app.domain.model.Card
import com.pagme.app.data.card.CardRepository
import java.util.ArrayList


class CardBusiness() {
    private val cardRepository = CardRepository()

    suspend fun createCard(card: Card): Boolean {
        if (card.cardName == "" || card.closingDate == "" || card.dueDate == "") {
            return false
        }
        cardRepository.createCard(card)
        return true


    }

    fun updateCard(card: Card) {
        cardRepository.updateCard(card)
    }

    fun readCards(): ArrayList<Card> {
        return cardRepository.readCards()
    }

    fun removeCard(cardId: String) {
        return cardRepository.deleteCard(cardId)

    }
}