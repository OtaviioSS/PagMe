package com.pagme.app.domain.usecase.card

import com.pagme.app.domain.model.Card

interface GetCardsUseCase {
    suspend operator fun invoke():List<Card>
}