package com.pagme.app.repository

import android.widget.Toast
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Card
import kotlinx.android.synthetic.main.activity_add_card.*

 val database = DatabaseRef().initializeDatabaseRefrence()

class CardRepository ()  {


    fun createCard(card:Card): Boolean {
        try {
            database.child("userOtavio").child("cards").child(card.cardID.toString()).setValue(card)
                .addOnSuccessListener {

                }
            return true
        }catch (e:Exception){
            return false
        }

    }


}