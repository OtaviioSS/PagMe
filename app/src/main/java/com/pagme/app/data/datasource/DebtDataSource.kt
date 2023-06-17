package com.pagme.app.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.Debt
import kotlinx.coroutines.tasks.await

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
                        "userId" to debt.userId,
                    )
                ).await()
        } catch (e: Exception) {
            throw Exception("Falha ao atualizar o débito: ${e.message}")
        }

    }

    suspend fun removeDebt(debt: Debt) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                .document(debt.idDebt).delete().await()
        } catch (e: Exception) {
            throw Exception("Falha ao remover o débito: ${e.message}")
        }

    }

    suspend fun getDebtById(id: String): Debt? {
        try {
            val snapshot =
                db.collection("users").document(auth.currentUser!!.uid).collection("debts")
                    .document(id).get().await()
            return snapshot.toObject(Debt::class.java)
        } catch (e: Exception) {
            throw Exception("Falha ao buscar o débito: ${e.message}")
        }

    }

    suspend fun getAllDebt(): List<Debt> {
        try {
            val snapshot =
                db.collection("users").document(auth.currentUser!!.uid)
                    .collection("debts")
                    .orderBy(
                        "nameBuyer",
                        Query.Direction.ASCENDING
                    ) // Ordena por campo "timestamp" em ordem decrescente
                    .get()
                    .await()
            return snapshot.toObjects(Debt::class.java)
        } catch (e: Exception) {
            throw Exception("Falha ao buscar o débito: ${e.message}")
        }

    }


}