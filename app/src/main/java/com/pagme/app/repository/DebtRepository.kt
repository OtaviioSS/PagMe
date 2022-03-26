package com.pagme.app.repository

import com.google.firebase.database.*
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Debt
import com.pagme.app.ui.cards
import kotlinx.android.synthetic.main.activity_edit_debt.*
import java.util.ArrayList

class DebtRepository() {
    val mListDebt: MutableList<Debt> = ArrayList()
    val user: String = "userOtavio"
    private val database = DatabaseRef().initializeDatabaseRefrence()


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

    fun createDebit(debt: Debt) {
        //        ALTERAR O USEROTAVIO PARA O EMAIL DO USUARIO LOGADO
        database.child("userOtavio").child("debts").child(debt.idDebt.toString()).setValue(debt)


    }

    fun updateDebit(debt: Debt): Boolean {
        try {
            database.child("userOtavio").child("cards").child(debt.idDebt.toString()).setValue(debt)
                .addOnSuccessListener {
                }
        }catch (exception:Exception){
            return false
        }
        return false

    }

    fun getOneDebt(debtID:String){
        val debt = Debt()
        database.child(user).child("debts").child(debtID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val debtSnep = snapshot.getValue(Debt::class.java)!!
                debt.valueBuy = debtSnep.valueBuy.toDouble()


            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}