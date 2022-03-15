package com.pagme.app.repository

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.pagme.app.entity.Debt
import com.pagme.app.ui.cards
import java.util.ArrayList

class DebtRepository(private val database: DatabaseReference) {
    val mListDebt: MutableList<Debt> = ArrayList()
    val user:String = "userOtavio"

    fun getList(): List<Debt> {
        return mListDebt
    }

    fun readCardsFromSppiner(): MutableList<String?> {
        database.child(user).child("cards").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cards.clear()
                for (cardSnapshot in dataSnapshot.children) {
                    val cardName = cardSnapshot.child("cardName").getValue(String::class.java)
                    cards.add(cardName)


                }

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        return cards
    }
    fun createDebit(debt: Debt, database: DatabaseReference) {
        //        ALTERAR O USEROTAVIO PARA O EMAIL DO USUARIO LOGADO
        database.child("userOtavio").child("debts").child(debt.id.toString()).setValue(debt)


    }
}