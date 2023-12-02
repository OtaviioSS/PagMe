package com.pagme.app.data.repository

import android.util.Log
import com.pagme.app.data.datasource.PaymentDataSource
import com.pagme.app.data.model.Payment

class PaymentRepositoryImplementation(private val paymentDataSource: PaymentDataSource) :
    PaymentRepositoryInterface {
    override suspend fun insert(payment: Payment) {
        try {
            paymentDataSource.create(payment)
        } catch (e: Exception) {
            Log.e("PaymentDataSource", e.message.toString())
            throw Exception("Falha ao criar o pagamento: ${e.message}")

        }
    }

    override suspend fun update(payment: Payment) {
        try {
            paymentDataSource.alter(payment)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }

    override suspend fun paidInstallment(payment: Payment,status: String) {
        try {
            paymentDataSource.paidInstallment(payment,status)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }

    override suspend fun delete(payment: Payment) {
        try {
            paymentDataSource.remove(payment)
        } catch (e: Exception) {
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }

    override suspend fun selectAll(debtID: String): MutableList<Payment> {
        return paymentDataSource.getAllDebt(debtID)
    }
}