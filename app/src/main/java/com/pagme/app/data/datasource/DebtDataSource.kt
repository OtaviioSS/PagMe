package com.pagme.app.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.Debt
import kotlinx.coroutines.tasks.await


import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DebtDataSource {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth


    suspend fun createDebt(debt: Debt) {
        try {
            debt.userId = auth.currentUser!!.uid
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(debt.idDebt)
                .set(debt)
                .await()
        } catch (e: Exception) {
            throw Exception("Falha ao criar o débito: ${e.message}")
        }

    }

    suspend fun alterDebt(debt: Debt) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(debt.idDebt).update(
                    mapOf(
                        "idDebt" to debt.idDebt,
                        "nameCard" to debt.nameCard,
                        "nameBuyer" to debt.nameBuyer,
                        "valueBuy" to debt.valueBuy,
                        "installments" to debt.installments,
                        "paidInstallments" to debt.paidInstallments,
                        "whatsapp" to debt.whatsapp,
                        "disabled" to debt.disabled,
                        "valueInstallments" to debt.valueInstallments,
                        "userId" to auth.currentUser!!.uid,
                    )
                ).await()
        } catch (e: Exception) {
            Log.i("DebtDataSource:", e.message.toString())
            throw Exception("Falha ao atualizar o débito: ${e.message}")
        }

    }

    suspend fun removeDebt(debtID: String) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(debtID).delete().await()
        } catch (e: Exception) {
            throw Exception("Falha ao remover o débito: ${e.message}")
        }

    }

    suspend fun getDebtById(id: String): Flow<Debt?> = callbackFlow {
        val docRef = db.collection("users").document(auth.currentUser!!.uid)
            .collection("debts").document(id)
        val registration = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error) // Feche o fluxo e retorne o erro
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val debt = snapshot.toObject(Debt::class.java)
                trySend(debt).isSuccess // Ofereça o objeto Debt atualizado ao fluxo
            } else {
                trySend(null).isSuccess // Ofereça null ao fluxo se o documento não existir
            }
        }

        awaitClose { registration.remove() } // Remova o listener quando o fluxo for fechado
    }

    suspend fun getAllDebt(): List<Debt> {
        try {
            val snapshot =
                db.collection("users").document(auth.currentUser!!.uid)
                    .collection("debts")
                    .orderBy("nameBuyer", Query.Direction.ASCENDING)
                    .get()
                    .await()
            return snapshot.toObjects(Debt::class.java)
        } catch (e: Exception) {
            throw Exception("Falha ao buscar o débito: ${e.message}")
        }

    }

    suspend fun pagouParcela(debt: Debt) {
        try {
            debt.paidInstallments = debt.paidInstallments.toInt() + 1
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(debt.idDebt).update("paidInstallments", debt.paidInstallments).await()
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar o débito: ${e.message}")
        }
    }
}