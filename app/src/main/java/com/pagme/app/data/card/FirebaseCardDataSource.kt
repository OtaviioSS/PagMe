package com.pagme.app.data.card

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pagme.app.domain.model.Card
import com.pagme.app.util.DatabaseRef
import java.util.ArrayList
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseCardDataSource @Inject constructor(
    databaseRef: DatabaseReference,
    auth: FirebaseAuth
) : CardDataSource {

    private val database = databaseRef
    private var cardArrayList = ArrayList<Card>()
    private val user = auth.currentUser


    override suspend fun createCard(card: Card): Card {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(card))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }

        }

    }

    override suspend fun getCard(): ArrayList<Card> {
        database.child(user!!.uid).child("cards")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cardArrayList.clear()
                    for (debtSnapshot in snapshot.children) {
                        val card = debtSnapshot.getValue(Card::class.java)
                        cardArrayList.add(card!!)
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }


            })
        return cardArrayList
    }

    override suspend fun updateCard(card: Card): Card {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(card))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }

        }
    }

    override suspend fun deleteCard(cardId: String): Boolean {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("cards").child(cardId).setValue(null)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }


    fun getDueDateCloseDate(idCard: String): Card {
        var carDates = Card()

        database.child(user!!.uid.toString()).child("cards").child(idCard)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(cardSnapshot: DataSnapshot) {
                    carDates.cardName = "Teste"
                    carDates.dueDate =
                        cardSnapshot.child("dueDate").getValue(String::class.java)
                    carDates.closingDate =
                        cardSnapshot.child("closingDate").getValue(String::class.java)


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        return carDates

    }

    fun getCardByName() {
        database.child(user!!.uid.toString()).child("cards")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(cardSnapshot: DataSnapshot) {


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

}