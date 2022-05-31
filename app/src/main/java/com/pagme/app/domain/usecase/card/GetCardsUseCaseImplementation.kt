package com.pagme.app.domain.usecase.card

import com.pagme.app.data.card.CardRepository
import com.pagme.app.domain.model.Card
import javax.inject.Inject

class GetCardsUseCaseImplementation @Inject constructor(
    private val cardRepository: CardRepository
) : GetCardsUseCase {

    override suspend fun invoke(): List<Card> {
        return cardRepository.getCard()
    }

}