package com.pagme.app.data.debt

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.pagme.app.domain.model.Card
import com.pagme.app.domain.model.Debt
import kotlinx.coroutines.internal.resumeCancellableWith
import java.util.ArrayList
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseDebtDataSource @Inject constructor(
    databaseRef: DatabaseReference,
    auth: FirebaseAuth
) : DebtDataSource {

    private val database = databaseRef
    private val user = auth.currentUser
    private val cards: MutableList<String?> = ArrayList()

    override suspend fun getDebt(): List<Debt> {
        return suspendCoroutine { continuation ->
            val debtArrayList = ArrayList<Debt>()
            database.child(user!!.uid.toString()).child("debts").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    debtArrayList.clear()
                    for (debtSnapshot in snapshot.children) {
                        val debt = debtSnapshot.getValue(Debt::class.java)
                        debtArrayList.add(debt!!)
                    }
                    continuation.resumeWith(Result.success(debtArrayList))

                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))
                }
            })
        }
    }

    override suspend fun createDebt(debt: Debt): Debt {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("debts").child(debt.idDebt.toString())
                .setValue(debt).addOnSuccessListener {
                    continuation.resumeWith(Result.success(debt))

                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }

    override suspend fun updateDebt(debt: Debt): Debt {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid).child("debts").child(debt.idDebt.toString()).setValue(debt)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(debt))
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }

        }
    }

    override suspend fun deleteDebt(idDebt: String): Boolean {
        return suspendCoroutine { continuation ->
            database.child(user!!.uid.toString()).child("debts").child(idDebt).setValue(null)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))
                }

        }
    }


}