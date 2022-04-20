package com.pagme.app.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.pagme.app.database.DatabaseRef
import com.pagme.app.entity.Debt
import kotlinx.android.synthetic.main.activity_edit_debt.*
import java.util.ArrayList

class DebtRepository() {
    private val database = DatabaseRef().initializeDatabaseRefrence()
    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser
    private val cards: MutableList<String?> = ArrayList()


    fun readAllDebts(): ArrayList<Debt> {
        val debtArrayList = ArrayList<Debt>()
        database.child(user!!.uid.toString()).child("debts").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                debtArrayList.clear()
                for (debtSnapshot in snapshot.children) {
                    val debt = debtSnapshot.getValue(Debt::class.java)
                    debtArrayList.add(debt!!)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return debtArrayList
    }

    fun readCardsFromSpinner(): MutableList<String?> {
        database.child(user!!.uid).child("cards").addValueEventListener(object :
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
        database.child(user!!.uid).child("debts").child(debt.idDebt.toString())
            .setValue(debt)


    }

    fun updateDebit(debt: Debt): Boolean {
        try {
            database.child(user!!.uid).child("debts").child(debt.idDebt.toString()).setValue(debt)
                .addOnSuccessListener {
                }
        } catch (exception: Exception) {
            return false
        }
        return false

    }

    fun getOneDebt(debtID: String): Debt {
        var debt = Debt()
        database.child(user!!.uid.toString()).child("debts").child(debtID)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    debt = snapshot.getValue(Debt::class.java)!!


                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        return debt
    }

    fun deleteDebt(debtID: String) {
        database.child(user!!.uid.toString()).child("debts").child(debtID).setValue(null)
    }

    fun insertPayment(value:Int,idDebt:String) {
        database.child(user!!.uid).child("debts").child(idDebt).child("paidInstallments").setValue(value)
    }
}