package com.pagme.app.domain.usecase

import android.util.Log
import com.pagme.app.data.model.Payment
import com.pagme.app.data.repository.PaymentRepositoryInterface

class PaymentUseCase(private val paymentRepositoryInterface: PaymentRepositoryInterface) {

    suspend fun create(payment: Payment, callback: (Boolean) -> Unit) {
        try {
            paymentRepositoryInterface.insert(payment)
            callback(true)
            Log.e("PaymentUseCaseSuccess", callback.toString())
        } catch (e: Exception) {
            callback(false)
            Log.e("PaymentUseCaseFalha", callback.toString())
        }

    }

    suspend fun update(payment: Payment) {
        paymentRepositoryInterface.update(payment)
    }

    suspend fun paidInstallment(payment: Payment,status: String, callback: (Boolean) -> Unit) {
        try {
            paymentRepositoryInterface.paidInstallment(payment,status)
            callback(true)
            Log.e("PaymentUseCaseSuccess", callback.toString())
        } catch (e: Exception) {
            callback(false)
            Log.e("PaymentUseCaseFalha", e.message.toString())
        }
    }

    suspend fun delete(payment: Payment) {
        paymentRepositoryInterface.delete(payment)
    }


    suspend fun getAll(debtID: String): MutableList<Payment> {
        return paymentRepositoryInterface.selectAll(debtID)
    }
}