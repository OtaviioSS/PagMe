package com.pagme.app.business

import com.pagme.app.entity.Card
import com.pagme.app.repository.CardRepository
import java.util.ArrayList

private val cardRepository = CardRepository()

class CardBussines {
    fun createCard(card: Card): Boolean {
        if (card.cardName == "" || card.closingDate == "" || card.dueDate == "") {
            return false
        }
            cardRepository.createCard(card)
            return true



    }

    fun readCards(): ArrayList<Card> {
        return cardRepository.readCards()
    }
}