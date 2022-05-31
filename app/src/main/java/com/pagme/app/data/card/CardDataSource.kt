package com.pagme.app.data.card

import com.pagme.app.domain.model.Card

interface CardDataSource {
    suspend fun getCard(): List<Card>

    suspend fun createCard(card: Card):Card

    suspend fun deleteCard(idCard: String):Boolean
}