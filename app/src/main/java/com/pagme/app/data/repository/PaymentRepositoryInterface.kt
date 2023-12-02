package com.pagme.app.data.repository

import com.pagme.app.data.model.Payment


interface PaymentRepositoryInterface {
    suspend fun insert(payment: Payment)
    suspend fun update(payment: Payment)
    suspend fun paidInstallment(payment: Payment,status: String)
    suspend fun delete(payment: Payment)
    suspend fun selectAll(debtID:String): MutableList<Payment>
}