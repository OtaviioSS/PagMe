package com.pagme.app.repository

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pagme.app.adapter.CardAdapter
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Card
import kotlinx.android.synthetic.main.activity_add_card.*
import java.util.ArrayList

val database = DatabaseRef().initializeDatabaseRefrence()

class CardRepository() {
    private  var cardArrayList = ArrayList<Card>()


    fun createCard(card: Card): Boolean {
        return try {
            database.child("userOtavio").child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {

                }
            true
        } catch (e: Exception) {
            false
        }

    }

    fun readCards(): ArrayList<Card> {
        database.child("userOtavio").child("cards")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
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

}