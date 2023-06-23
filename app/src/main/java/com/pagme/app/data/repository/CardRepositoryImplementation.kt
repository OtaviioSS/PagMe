package com.pagme.app.data.repository

import com.pagme.app.data.datasource.CardDataSource
import com.pagme.app.data.model.Card

class CardRepositoryImplementation(private val cardDataSource: CardDataSource) :
    CardRepositoryInterface {
    override suspend fun insert(card: Card) {
        try {
            cardDataSource.create(card)

        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }

    }

    override suspend fun update(card: Card) {
        cardDataSource.alter(card)
    }

    override suspend fun delete(card: Card) {
        cardDataSource.remove(card)
    }


    override suspend fun selectById(id: String): Card? {
        return cardDataSource.getCardById(id)
    }

    override suspend fun selectAll(): MutableList<Card> {
        return cardDataSource.getAll()
    }


}
