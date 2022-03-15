package com.pagme.app.business

import com.pagme.app.entity.Card
import com.pagme.app.repository.CardRepository

private val cardRepository = CardRepository()

class CardBussines {
    fun createCard(card: Card): Boolean {
        val resultRepository = cardRepository.createCard(card)
        if (resultRepository) {
            return true
        }
        return false

    }
}