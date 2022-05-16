package com.pagme.app.data.card

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.pagme.app.util.DatabaseRef
import com.pagme.app.domain.model.Card
import java.util.ArrayList
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


class CardRepository @Inject constructor(private val cardDataSource: CardDataSource) {

    suspend fun getCard():List<@JvmSuppressWildcards Card> = cardDataSource.getCard()

    suspend fun createCard(card: Card): Card = cardDataSource.createCard(card)

    suspend fun updateCard(card: Card):Card = cardDataSource.updateCard(card)

    suspend fun deleteCard(idCard:String): Boolean = cardDataSource.deleteCard(idCard)






















/*
    private var cardArrayList = ArrayList<Card>()
    private val database = DatabaseRef().initializeDatabaseRefrence()
    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser


    suspend fun createCard(card: Card): Boolean {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }.addOnFailureListener{
                    continuation.resumeWith(Result.success(false))
                }

        }

    }

    fun readCards(): ArrayList<Card> {
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

    fun updateCard(card: Card): Boolean {
        return try {
            database.child(user!!.uid).child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {
                }
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteCard(cardId: String) {
        database.child(user!!.uid).child("cards").child(cardId).setValue(null)
            .addOnSuccessListener {
            }
    }



    fun getDueDateCloseDate(idCard:String): Card {
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

    fun getCardByName(){
        database.child(user!!.uid.toString()).child("cards")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(cardSnapshot: DataSnapshot) {


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
*/

}