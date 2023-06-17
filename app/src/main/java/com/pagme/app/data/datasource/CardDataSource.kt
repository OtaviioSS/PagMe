package com.pagme.app.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.pagme.app.data.model.Card
import kotlinx.coroutines.tasks.await

class CardDataSource {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth


    suspend fun create(card: Card) {
        try {
            db.collection("users").document(auth.currentUser!!.uid).collection("cards")
                .document(card.idCard)
                .set(card)
                .await()
        }catch (e:Exception){
            throw Exception("Falha ao criar o cartão: ${e.message}")
        }
    }


    suspend fun alter(card: Card) {
        db.collection("users").document(auth.currentUser!!.uid).collection("cards")
            .document(card.idCard).update(
                mapOf(
                    "idCard" to card.idCard,
                    "nameCard" to card.nameCard,

                    )
            ).await()
    }

    suspend fun remove(card: Card) {
        db.collection("users").document(auth.currentUser!!.uid).collection("cards")
            .document(card.idCard).delete().await()
    }

    suspend fun getCardById(id: String): Card? {
        val snapshot = db.collection("users").document(auth.currentUser!!.uid).collection("cards")
            .document(id).get().await()
        return snapshot.toObject(Card::class.java)
    }

    suspend fun getAll(): List<Card> {
        val snapshot =
            db.collection("users").document(auth.currentUser!!.uid).collection("cards").get()
                .await()
        return snapshot.toObjects(Card::class.java)
    }
}