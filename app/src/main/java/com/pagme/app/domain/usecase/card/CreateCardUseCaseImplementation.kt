package com.pagme.app.domain.usecase.card

import com.pagme.app.data.card.CardRepository
import com.pagme.app.domain.model.Card
import java.util.*
import javax.inject.Inject

class CreateCardUseCaseImplementation @Inject constructor(private val cardRepository: CardRepository) :
    CreateCardUseCase {
    override suspend fun invoke(cardName: String, closingDate: String, dueDate: String): Card {
        return try {
            val card = Card(UUID.randomUUID().toString(), cardName, closingDate, dueDate)
            cardRepository.createCard(card)
            card
        } catch (e: Exception) {
            throw e
        }
    }

}