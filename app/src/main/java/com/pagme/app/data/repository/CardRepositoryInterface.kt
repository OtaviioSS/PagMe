package com.pagme.app.data.repository

import com.pagme.app.data.model.Card


interface CardRepositoryInterface {
    suspend fun insert(card: Card)
    suspend fun update(card: Card)
    suspend fun delete(card: Card)
    suspend fun selectById(id: String): Card?
    suspend fun selectAll(): MutableList<Card>
}