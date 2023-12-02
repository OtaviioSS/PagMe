package com.pagme.app.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.Debt
import com.pagme.app.data.model.Payment
import com.pagme.app.util.STATUS_PAGO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class PaymentDataSource {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth


    suspend fun create(payment: Payment) {
        try {
            payment.userID = auth.currentUser!!.uid
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(payment.debtID)
                .collection("payments")
                .document(payment.paymentID)
                .set(payment)
                .await()
            Log.i("PaymentDataSource", "Criou")

        } catch (e: Exception) {
            Log.e("PaymentDataSource", e.message.toString())
            throw Exception("Falha ao criar o pagamento: ${e.message}")
        }

    }

    suspend fun alter(payment: Payment) {
        try {
            payment.userID = auth.currentUser!!.uid
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(payment.debtID)
                .collection("payments")
                .document(payment.paymentID).update(
                    mapOf(
                        "paymentID" to payment.paymentID,
                        "paymentDate" to payment.paymentDate,
                        "paymentValue" to payment.paymentValue,
                        "paymentStatus" to payment.paymentStatus,
                        "debtID" to payment.debtID,
                        "cardID" to payment.cardID,
                        "userId" to auth.currentUser!!.uid,
                    )
                ).await()
        } catch (e: Exception) {
            Log.i("DebtDataSource:", e.message.toString())
            throw Exception("Falha ao atualizar o débito: ${e.message}")
        }

    }

    suspend fun remove(payment: Payment) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(payment.debtID)
                .collection("payments")
                .document(payment.paymentID).delete().await()
        } catch (e: Exception) {
            throw Exception("Falha ao remover o pagamento: ${e.message}")
        }

    }


    suspend fun getAllDebt(debtID:String): MutableList<Payment> {
        try {
            val snapshot =
                db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                    .document(debtID)
                    .collection("payments")
                    .orderBy("paymentDueDate", Query.Direction.ASCENDING)
                    .get()
                    .await()
            return snapshot.toObjects(Payment::class.java)
        } catch (e: Exception) {
            throw Exception("Falha ao buscar o débito: ${e.message}")
        }

    }

    suspend fun paidInstallment(payment: Payment, status: String) {
        try {
            payment.userID = auth.currentUser!!.uid
            payment.paymentStatus = STATUS_PAGO
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(payment.debtID)
                .collection("payments")
                .document(payment.paymentID).update("paymentStatus", status).await()
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar o débito: ${e.message}")
        }
    }


}